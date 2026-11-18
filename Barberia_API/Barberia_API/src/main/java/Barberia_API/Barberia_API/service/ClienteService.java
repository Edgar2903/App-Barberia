package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAll();

    ClienteDTO findById(Long id);

    ClienteDTO findByName(String nombre);

    ClienteDTO save(ClienteDTO cliente);

    ClienteDTO update(Long id, ClienteDTO cliente);

    void delete(Long id);
}
