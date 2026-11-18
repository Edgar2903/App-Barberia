package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {
    Barbero findByNombre(String nombre);
}
