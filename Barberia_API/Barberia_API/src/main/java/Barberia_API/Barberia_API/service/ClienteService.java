package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente findByName(String nombre);

    Cliente save(Cliente cliente);

    Cliente update(Long id, Cliente cliente);

    void delete(Long id);
}
