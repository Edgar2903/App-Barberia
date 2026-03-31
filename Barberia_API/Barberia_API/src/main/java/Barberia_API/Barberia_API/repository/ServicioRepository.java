package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Servicio findByNombre(String nombre);


    boolean existsByNombre(String nombre);
}
