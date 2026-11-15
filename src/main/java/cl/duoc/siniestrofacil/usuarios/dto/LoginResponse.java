package cl.duoc.siniestrofacil.usuarios.dto;

public record LoginResponse(
        Long id,
        String nombre,
        String email,
        String rol,
        String mensaje,
        String token
) {
}
