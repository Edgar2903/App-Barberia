package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.ClienteDTO;
import Barberia_API.Barberia_API.entity.Cliente;
import Barberia_API.Barberia_API.repository.ClienteRepository;
import Barberia_API.Barberia_API.service.ClienteService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

private final ClienteRepository clienteRepository;

public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
}

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
public ClienteDTO findById(Long id) {
    Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    return toDTO(cliente);
}

    @Override
    public ClienteDTO findByName(String nombre) {

        Cliente cliente = clienteRepository.findByNombre(nombre);

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        return toDTO(cliente);
    }
    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        return toDTO(clienteRepository.save(toEntity(clienteDTO)));
    }

@Override
public ClienteDTO update(Long id, ClienteDTO clienteDTO) {

    Cliente existente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    existente.setNombre(clienteDTO.getNombre());
    existente.setApellido(clienteDTO.getApellido());
    existente.setTelefono(clienteDTO.getTelefono());

    return toDTO(clienteRepository.save(existente));
}

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}
