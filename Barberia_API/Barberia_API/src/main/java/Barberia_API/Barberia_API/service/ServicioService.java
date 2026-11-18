package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.ServicioDTO;

import java.util.List;

public interface ServicioService {
    List<ServicioDTO> findAll();
    ServicioDTO findById(Long id);
    ServicioDTO findByNombre(String nombre);
    ServicioDTO save(ServicioDTO servicio);
    ServicioDTO update(Long id, ServicioDTO servicio);
    void delete(Long id);
}

