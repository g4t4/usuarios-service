package cl.duoc.siniestrofacil.usuarios.service;

import cl.duoc.siniestrofacil.usuarios.exception.ConflictoException;
import cl.duoc.siniestrofacil.usuarios.exception.RecursoNoEncontradoException;
import cl.duoc.siniestrofacil.usuarios.model.Rol;
import cl.duoc.siniestrofacil.usuarios.repository.RolRepository;
import cl.duoc.siniestrofacil.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolService {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public RolService(RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rol obtener(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un rol con id " + id));
    }

    @Transactional
    public Rol crear(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new ConflictoException("Ya existe un rol con el nombre " + rol.getNombre());
        }
        rol.setId(null);
        return rolRepository.save(rol);
    }

    @Transactional
    public Rol actualizar(Long id, Rol datos) {
        Rol rol = obtener(id);
        if (!rol.getNombre().equals(datos.getNombre()) && rolRepository.existsByNombre(datos.getNombre())) {
            throw new ConflictoException("Ya existe un rol con el nombre " + datos.getNombre());
        }
        rol.setNombre(datos.getNombre());
        rol.setDescripcion(datos.getDescripcion());
        return rolRepository.save(rol);
    }

    @Transactional
    public void eliminar(Long id) {
        Rol rol = obtener(id);
        if (usuarioRepository.existsByRolId(id)) {
            throw new ConflictoException("No se puede eliminar el rol porque tiene usuarios asociados");
        }
        rolRepository.delete(rol);
    }
}
