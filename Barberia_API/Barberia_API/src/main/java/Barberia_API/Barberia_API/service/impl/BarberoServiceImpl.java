package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.entity.Barbero;
import Barberia_API.Barberia_API.repository.BarberoRepository;
import Barberia_API.Barberia_API.service.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BarberoServiceImpl implements  BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    @Override
    public List<Barbero> findAll() {
        return barberoRepository.findAll();
    }

    @Override
    public Barbero findById(Long id) {
        return barberoRepository.findById(id).orElse(null);
    }

    @Override
    public Barbero findByName(String nombre) {
        return (Barbero) barberoRepository;
    }

    @Override
    public Barbero save(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    @Override
    public void delete(Long id) {
        barberoRepository.deleteById(id);

    }

    @Override
    public Barbero update(Long id, Barbero barbero) {
        Barbero existente = findById(id);
        existente.setNombre(barbero.getNombre());
        existente.setEspecialidad(barbero.getEspecialidad());
        existente.setImagenes(barbero.getImagenes());
        return barberoRepository.save(existente);
    }
}
