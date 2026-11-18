package Barberia_API.Barberia_API.service.impl;


import Barberia_API.Barberia_API.dto.CaracteristicaDTO;
import Barberia_API.Barberia_API.entity.Caracteristica;
import Barberia_API.Barberia_API.repository.CaracteristicaRepository;
import Barberia_API.Barberia_API.service.CaracteristicaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaracteristicaServiceImpl implements CaracteristicaService {
    private final CaracteristicaRepository caracteristicaRepository;

    public CaracteristicaServiceImpl(CaracteristicaRepository caracteristicaRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
    }
    private CaracteristicaDTO toDTO(Caracteristica caracteristica)
    {
        CaracteristicaDTO dto = new CaracteristicaDTO();
        dto.setId(caracteristica.getId());
        dto.setNombre(caracteristica.getNombre());
        return dto;
    }

    @Override
    public List<CaracteristicaDTO> findAll() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        return caracteristicas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CaracteristicaDTO findById(Long id) {
        Caracteristica caracteristica = caracteristicaRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Característica no encontrada"
                        )
                );

        return toDTO(caracteristica);
    }

    @Override
    public CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO) {
        Caracteristica caracteristica = new Caracteristica();
        String nombreNormalizado =
                caracteristicaDTO.getNombre().trim();

        if (caracteristicaRepository
                .existsByNombreIgnoreCase(nombreNormalizado)) {
            throw new IllegalArgumentException(
                    "La característica ya existe"
            );
        }
        caracteristica.setNombre(nombreNormalizado);

        Caracteristica caracteristicaGuardada =
                caracteristicaRepository.save(caracteristica);

        return toDTO(caracteristicaGuardada);

    }

    @Override
    public CaracteristicaDTO update(Long id, CaracteristicaDTO caracteristicaDTO) {
        Caracteristica caracteristica = caracteristicaRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Característica no encontrada"
                        )
                );
        caracteristica.setNombre(caracteristicaDTO.getNombre());
        Caracteristica caracteristicaActualizada = caracteristicaRepository.save(caracteristica);

        return   toDTO(caracteristicaActualizada);
    }

    @Override
    public void delete(Long id) {
        caracteristicaRepository.existsById(id);
        new IllegalArgumentException("No se ha encontrado la característica con id " + id);
        caracteristicaRepository.deleteById(id);

    }
}

