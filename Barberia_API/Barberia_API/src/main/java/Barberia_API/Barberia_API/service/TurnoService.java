package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.TurnoDTO;

import java.util.List;

public interface TurnoService {
    List<TurnoDTO> findAll();
    TurnoDTO findById(Long id);
    TurnoDTO save(TurnoDTO dto);
    void delete(Long id);
    TurnoDTO update(Long id, TurnoDTO turno);
}
