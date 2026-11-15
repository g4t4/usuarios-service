package cl.duoc.siniestrofacil.usuarios.repository;

import cl.duoc.siniestrofacil.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    boolean existsByNombre(String nombre);
}
