package cl.duoc.siniestrofacil.usuarios.service;

import cl.duoc.siniestrofacil.usuarios.dto.LoginRequest;
import cl.duoc.siniestrofacil.usuarios.dto.LoginResponse;
import cl.duoc.siniestrofacil.usuarios.exception.CredencialesInvalidasException;
import cl.duoc.siniestrofacil.usuarios.model.Usuario;
import cl.duoc.siniestrofacil.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Valida email y contraseña. Si son correctos y el usuario está activo,
     * devuelve sus datos básicos y un token JWT; si no, responde 401.
     */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!usuario.isActivo() || !passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }
        String rol = usuario.getRol().getNombre();
        String token = jwtService.generarToken(usuario.getEmail(), rol);
        return new LoginResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                rol, "Autenticación exitosa", token);
    }
}
