package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.BarberoDTO;
import Barberia_API.Barberia_API.entity.Barbero;
import Barberia_API.Barberia_API.repository.BarberoRepository;
import Barberia_API.Barberia_API.service.BarberoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarberoServiceImpl implements BarberoService {


private final BarberoRepository barberoRepository;

public BarberoServiceImpl(BarberoRepository barberoRepository) {
    this.barberoRepository = barberoRepository;
}

    @Override
    public List<BarberoDTO> findAll() {
        return barberoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
public BarberoDTO findById(Long id) {
    Barbero barbero = barberoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));

    return toDTO(barbero);
}
    @Override
    public BarberoDTO findByName(String nombre) {

        Barbero barbero = barberoRepository.findByNombre(nombre);

        if (barbero == null) {
            throw new RuntimeException("Barbero no encontrado");
        }

        return toDTO(barbero);
    }
    @Override
    public BarberoDTO save(BarberoDTO barberoDTO) {
        Barbero barbero = toEntity(barberoDTO);
        return toDTO(barberoRepository.save(barbero));
    }

    @Override
    public void delete(Long id) {
        barberoRepository.deleteById(id);
    }
@Override
public BarberoDTO update(Long id, BarberoDTO barberoDTO) {

    Barbero existente = barberoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));

    existente.setNombre(barberoDTO.getNombre());
    existente.setApellido(barberoDTO.getApellido());
    existente.setEspecialidad(barberoDTO.getEspecialidad());
    existente.setValoracion(barberoDTO.getValoracion());
    existente.setImagenes(barberoDTO.getImagenes());

    return toDTO(barberoRepository.save(existente));
}



    private BarberoDTO toDTO(Barbero barbero) {
        if (barbero == null) {
            return null;
        }
        BarberoDTO dto = new BarberoDTO();
        dto.setId(barbero.getId());
        dto.setNombre(barbero.getNombre());
        dto.setApellido(barbero.getApellido());
        dto.setEspecialidad(barbero.getEspecialidad());
        dto.setValoracion(barbero.getValoracion());
        dto.setImagenes(barbero.getImagenes());
        return dto;
    }

    private Barbero toEntity(BarberoDTO dto) {
        if (dto == null) {
            return null;
        }
        Barbero barbero = new Barbero();
        barbero.setId(dto.getId());
        barbero.setNombre(dto.getNombre());
        barbero.setApellido(dto.getApellido());
        barbero.setEspecialidad(dto.getEspecialidad());
        barbero.setValoracion(dto.getValoracion());
        barbero.setImagenes(dto.getImagenes());
        return barbero;
    }
}
