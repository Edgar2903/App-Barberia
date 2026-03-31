package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    boolean existsByFechaAndHora(LocalDate fecha, LocalTime hora);
}

