package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.CaracteristicaDTO;
import Barberia_API.Barberia_API.dto.ServicioDTO;
import Barberia_API.Barberia_API.entity.Categoria;
import Barberia_API.Barberia_API.entity.Servicio;
import Barberia_API.Barberia_API.repository.CaracteristicaRepository;
import Barberia_API.Barberia_API.repository.CategoriaRepository;
import Barberia_API.Barberia_API.repository.ServicioRepository;
import Barberia_API.Barberia_API.service.ServicioService;
import jakarta.persistence.EntityNotFoundException;
import Barberia_API.Barberia_API.entity.Caracteristica;
import Barberia_API.Barberia_API.repository.CaracteristicaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final CategoriaRepository categoriaRepository;
    private final CaracteristicaRepository caracteristicaRepository;


    public ServicioServiceImpl(ServicioRepository servicioRepository, CategoriaRepository categoriaRepository, CaracteristicaRepository caracteristicaRepository) {
        this.servicioRepository = servicioRepository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
    }



    @Override
    public ServicioDTO save(ServicioDTO servicioDTO) {
        Categoria categoria = categoriaRepository
                .findById(servicioDTO.getCategoriaId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Categoría no encontrada con id "
                                        + servicioDTO.getCategoriaId()
                        )
                );

        if (servicioRepository.existsByNombre(
                servicioDTO.getNombre()
        )) {
            throw new IllegalArgumentException(
                    "El nombre del servicio ya está en uso"
            );
        }


        Servicio servicio = toEntity(servicioDTO);
        servicio.setCategoria(categoria);
        servicio.setCaracteristicas(
                obtenerCaracteristicas(
                        servicioDTO.getCaracteristicaIds()
                )
        );

        Servicio servicioGuardado =
                servicioRepository.save(servicio);

        return toDTO(servicioGuardado);
    }
    @Override
    public List<ServicioDTO> findAll() {
        return servicioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioDTO findById(Long id) {
        return servicioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado con id " + id));
    }

    @Override
    public ServicioDTO findByNombre(String nombre) {
        Servicio servicio = servicioRepository.findByNombre(nombre);
        if (servicio == null) {
            throw new EntityNotFoundException("Servicio no encontrado con nombre " + nombre);
        }
        return toDTO(servicio);
    }

    @Override
    public ServicioDTO update(
            Long id,
            ServicioDTO servicioDTO
    ) {
        Servicio existente = servicioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Servicio no encontrado con id " + id
                        )
                );

        Categoria categoria = categoriaRepository
                .findById(servicioDTO.getCategoriaId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Categoría no encontrada con id "
                                        + servicioDTO.getCategoriaId()
                        )
                );

        existente.setNombre(servicioDTO.getNombre().trim());
        existente.setPrecio(servicioDTO.getPrecio());
        existente.setDescripcion(
                servicioDTO.getDescripcion().trim()
        );
        existente.setImagenes(servicioDTO.getImagenes());
        existente.setCategoria(categoria);
        existente.setCaracteristicas(
                obtenerCaracteristicas(servicioDTO.getCaracteristicaIds())
        );

        Servicio servicioActualizado =
                servicioRepository.save(existente);

        return toDTO(servicioActualizado);
    }
    @Override
    public void delete(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicio no encontrado con id " + id);
        }
        servicioRepository.deleteById(id);
    }

    private ServicioDTO toDTO(Servicio servicio) {
        if (servicio == null) return null;
        ServicioDTO dto = new ServicioDTO();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setPrecio(servicio.getPrecio());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setImagenes(servicio.getImagenes());
        if (servicio.getCategoria() != null) {
            dto.setCategoriaId(servicio.getCategoria().getId());
            dto.setCategoriaNombre(servicio.getCategoria().getNombre());
        }
        dto.setCaracteristicas(
                servicio.getCaracteristicas()
                        .stream()
                        .map(caracteristica -> {
                            CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
                            caracteristicaDTO.setId(caracteristica.getId());
                            caracteristicaDTO.setNombre(caracteristica.getNombre());
                            return caracteristicaDTO;
                        })
                        .toList()
        );
        return dto;
    }

    private Servicio toEntity(ServicioDTO dto) {
        if (dto == null) return null;
        Servicio servicio = new Servicio();
        servicio.setId(dto.getId());
        servicio.setNombre(dto.getNombre());
        servicio.setPrecio(dto.getPrecio());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setImagenes(dto.getImagenes());
        return servicio;
    }
    private Set<Caracteristica> obtenerCaracteristicas(
            List<Long> caracteristicaIds
    ) {
        if (caracteristicaIds == null || caracteristicaIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<Caracteristica> caracteristicas = new HashSet<>(
                caracteristicaRepository.findAllById(caracteristicaIds)
        );

        if (caracteristicas.size() != new HashSet<>(caracteristicaIds).size()) {
            throw new EntityNotFoundException(
                    "Una o más características no existen"
            );
        }

        return caracteristicas;
    }
}
