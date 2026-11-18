package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByNombre(String nombre);
}