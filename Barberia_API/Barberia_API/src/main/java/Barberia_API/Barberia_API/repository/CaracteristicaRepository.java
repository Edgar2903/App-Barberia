package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    boolean existsByNombreIgnoreCase(String nombre);


}
