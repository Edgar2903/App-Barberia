package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.BarberoDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface BarberoService{

    List<BarberoDTO> findAll();
    BarberoDTO findById(Long id);
    BarberoDTO findByName(String nombre);
    BarberoDTO save(BarberoDTO barbero);
    void delete(Long id);
    BarberoDTO update(Long id, BarberoDTO barbero);



}

