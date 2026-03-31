package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.entity.Servicio;
import Barberia_API.Barberia_API.entity.Cliente;

import java.util.List;

public interface ServicioService {
    List<Servicio> findAll();
    Servicio findById(Long id);
    Servicio findByNombre(String nombre);
    Servicio save(Servicio servicio);
    Servicio update(Long id, Servicio servicio);
    void delete(Long id);
}

