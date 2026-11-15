package cl.duoc.siniestrofacil.usuarios.dto;

import cl.duoc.siniestrofacil.usuarios.model.Usuario;

import java.time.LocalDateTime;

/**
 * Datos de un usuario que se devuelven al cliente (nunca incluye la contraseña).
 */
public record UsuarioResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        boolean activo,
        LocalDateTime fechaRegistro,
        Long rolId,
        String rol
) {
    public static UsuarioResponse desde(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.isActivo(),
                u.getFechaRegistro(),
                u.getRol().getId(),
                u.getRol().getNombre()
        );
    }
}
