package cl.duoc.siniestrofacil.usuarios.service;

import cl.duoc.siniestrofacil.usuarios.dto.UsuarioRequest;
import cl.duoc.siniestrofacil.usuarios.dto.UsuarioResponse;
import cl.duoc.siniestrofacil.usuarios.exception.ConflictoException;
import cl.duoc.siniestrofacil.usuarios.exception.RecursoNoEncontradoException;
import cl.duoc.siniestrofacil.usuarios.model.Rol;
import cl.duoc.siniestrofacil.usuarios.model.Usuario;
import cl.duoc.siniestrofacil.usuarios.repository.RolRepository;
import cl.duoc.siniestrofacil.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(UsuarioResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarPorRol(Long rolId) {
        return usuarioRepository.findByRolId(rolId).stream().map(UsuarioResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        return UsuarioResponse.desde(buscar(id));
    }

    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new ConflictoException("La contraseña es obligatoria al crear un usuario");
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ConflictoException("Ya existe un usuario con el email " + request.email());
        }
        Usuario usuario = new Usuario();
        aplicarDatos(usuario, request);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Usuario usuario = buscar(id);
        if (!usuario.getEmail().equals(request.email()) && usuarioRepository.existsByEmail(request.email())) {
            throw new ConflictoException("Ya existe un usuario con el email " + request.email());
        }
        aplicarDatos(usuario, request);
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }
        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.delete(buscar(id));
    }

    private Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id " + id));
    }

    private void aplicarDatos(Usuario usuario, UsuarioRequest request) {
        Rol rol = rolRepository.findById(request.rolId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un rol con id " + request.rolId()));
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());
        usuario.setRol(rol);
        if (request.activo() != null) {
            usuario.setActivo(request.activo());
        }
    }
}
