package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.CaracteristicaDTO;

import java.util.List;

public interface CaracteristicaService {
    List<CaracteristicaDTO> findAll();

    CaracteristicaDTO findById(Long id);

    CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO);

    CaracteristicaDTO update(
            Long id,
            CaracteristicaDTO caracteristicaDTO
    );

    void delete(Long id);
}
