package cl.duoc.siniestrofacil.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos para crear o actualizar un usuario.
 * En la actualización la contraseña es opcional (si viene vacía se mantiene la actual).
 */
public record UsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 60, message = "El nombre admite hasta 60 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 60, message = "El apellido admite hasta 60 caracteres")
        String apellido,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        @Size(max = 120, message = "El email admite hasta 120 caracteres")
        String email,

        @Size(min = 6, max = 60, message = "La contraseña debe tener entre 6 y 60 caracteres")
        String password,

        @NotNull(message = "El rolId es obligatorio")
        Long rolId,

        Boolean activo
) {
}
