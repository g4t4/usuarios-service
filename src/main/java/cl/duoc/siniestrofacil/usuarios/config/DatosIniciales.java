package cl.duoc.siniestrofacil.usuarios.config;

import cl.duoc.siniestrofacil.usuarios.model.Rol;
import cl.duoc.siniestrofacil.usuarios.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga los roles base la primera vez que se inicia el servicio.
 */
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol("ADMIN", "Administrador del sistema"));
                rolRepository.save(new Rol("ASEGURADO", "Cliente asegurado que denuncia siniestros"));
                rolRepository.save(new Rol("TALLER", "Taller que atiende los vehículos siniestrados"));
            }
        };
    }
}
