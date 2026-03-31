package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.entity.Cliente;
import Barberia_API.Barberia_API.repository.ClienteRepository;
import Barberia_API.Barberia_API.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
       return clienteRepository.findById(id).get();
    }

    @Override
    public Cliente findByName(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }


    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        Cliente existente = findById(id);
        existente.setNombre(cliente.getNombre());
        existente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.delete(findById(id));

    }
}
