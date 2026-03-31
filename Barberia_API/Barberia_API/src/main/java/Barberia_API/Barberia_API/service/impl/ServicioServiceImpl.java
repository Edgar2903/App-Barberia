package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.entity.Servicio;
import Barberia_API.Barberia_API.repository.ServicioRepository;
import Barberia_API.Barberia_API.repository.TurnoRepository;
import Barberia_API.Barberia_API.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final TurnoRepository turnoRepository;

    @Autowired
    public ServicioServiceImpl(ServicioRepository servicioRepository, TurnoRepository turnoRepository) {
        this.servicioRepository = servicioRepository;
        this.turnoRepository = turnoRepository;
    }

    @Override
    public Servicio save(Servicio servicio) {
        if (servicioRepository.existsByNombre(servicio.getNombre())) {
            throw new IllegalArgumentException("El nombre del servicio ya está en uso");
        }
        return servicioRepository.save(servicio);
    }





    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio findById(Long id) {
        return servicioRepository.findById(id).get();
    }

    @Override
    public Servicio findByNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }


    @Override
    public Servicio update(Long id, Servicio servicio) {
        Servicio existente = findById(id);
        existente.setNombre(servicio.getNombre());
        existente.setPrecio(servicio.getPrecio());
        existente.setImagenes(servicio.getImagenes());
        return servicioRepository.save(existente);    }

    @Override
    public void delete(Long id) {
        turnoRepository.deleteById(id);
        servicioRepository.deleteById(id);;
    }


}
