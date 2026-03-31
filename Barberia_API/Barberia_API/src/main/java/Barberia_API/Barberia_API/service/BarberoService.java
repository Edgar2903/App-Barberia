package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.entity.Barbero;

import java.util.List;

public interface BarberoService{

    List<Barbero> findAll();
    Barbero findById(Long id);
    Barbero findByName(String nombre);
    Barbero save(Barbero barbero);
    void delete(Long id);
    Barbero update(Long id, Barbero barbero);
}

