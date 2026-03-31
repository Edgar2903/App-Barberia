package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.TurnoDTO;
import Barberia_API.Barberia_API.entity.Turno;

import java.util.List;

public interface TurnoService {
    List<Turno> findAll();
    Turno findById(Long id);
    Turno save(TurnoDTO dto);
    void delete(Long id);
    Turno update(Long id, Turno turno);
}
