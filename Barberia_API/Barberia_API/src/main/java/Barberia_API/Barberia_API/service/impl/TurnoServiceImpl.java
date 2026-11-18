package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.TurnoDTO;
import Barberia_API.Barberia_API.entity.Barbero;
import Barberia_API.Barberia_API.entity.Cliente;
import Barberia_API.Barberia_API.entity.Servicio;
import Barberia_API.Barberia_API.entity.Turno;
import Barberia_API.Barberia_API.repository.BarberoRepository;
import Barberia_API.Barberia_API.repository.ClienteRepository;
import Barberia_API.Barberia_API.repository.ServicioRepository;
import Barberia_API.Barberia_API.repository.TurnoRepository;
import Barberia_API.Barberia_API.service.TurnoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final ClienteRepository clienteRepo;
    private final BarberoRepository barberoRepo;
    private final ServicioRepository servicioRepo;
    private final TurnoRepository turnoRepository;

    public TurnoServiceImpl(
            ClienteRepository clienteRepo,
            BarberoRepository barberoRepo,
            ServicioRepository servicioRepo,
            TurnoRepository turnoRepository) {

        this.clienteRepo = clienteRepo;
        this.barberoRepo = barberoRepo;
        this.servicioRepo = servicioRepo;
        this.turnoRepository = turnoRepository;
    }

    @Override
    public List<TurnoDTO> findAll() {
        return turnoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TurnoDTO findById(Long id) {
        return turnoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() ->
                        new RuntimeException("Turno no encontrado"));
    }

    @Override
    public TurnoDTO save(TurnoDTO dto) {

        if (dto.getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "No podés reservar una fecha pasada");
        }

        Turno turno = toEntity(dto);

        boolean existe = turnoRepository.existsByFechaAndHora(
                turno.getFecha(),
                turno.getHora()
        );

        if (existe) {
            throw new RuntimeException(
                    "Ese horario ya está ocupado");
        }

        return toDTO(turnoRepository.save(turno));
    }

    @Override
    public void delete(Long id) {

        if (!turnoRepository.existsById(id)) {
            throw new RuntimeException("Turno no encontrado");
        }

        turnoRepository.deleteById(id);
    }

    @Override
    public TurnoDTO update(Long id, TurnoDTO turnoDTO) {

        Turno existente = turnoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Turno no encontrado"));

        Cliente cliente = clienteRepo
                .findById(turnoDTO.getClienteId())
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        Barbero barbero = barberoRepo
                .findById(turnoDTO.getBarberoId())
                .orElseThrow(() ->
                        new RuntimeException("Barbero no encontrado"));

        Servicio servicio = servicioRepo
                .findById(turnoDTO.getServicioId())
                .orElseThrow(() ->
                        new RuntimeException("Servicio no encontrado"));

        if (!existente.getFecha().equals(turnoDTO.getFecha())
                || !existente.getHora().equals(turnoDTO.getHora())) {

            boolean ocupado =
                    turnoRepository.existsByFechaAndHora(
                            turnoDTO.getFecha(),
                            turnoDTO.getHora());

            if (ocupado) {
                throw new RuntimeException(
                        "Ese horario ya está ocupado");
            }
        }

        if (turnoDTO.getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "No podés modificar a una fecha pasada");
        }

        existente.setFecha(turnoDTO.getFecha());
        existente.setHora(turnoDTO.getHora());
        existente.setCliente(cliente);
        existente.setBarbero(barbero);
        existente.setServicio(servicio);

        return toDTO(turnoRepository.save(existente));
    }

    private TurnoDTO toDTO(Turno turno) {

        if (turno == null) {
            return null;
        }

        TurnoDTO dto = new TurnoDTO();

        dto.setId(turno.getId());
        dto.setFecha(turno.getFecha());
        dto.setHora(turno.getHora());

        dto.setClienteId(
                turno.getCliente() != null
                        ? turno.getCliente().getId()
                        : null);

        dto.setBarberoId(
                turno.getBarbero() != null
                        ? turno.getBarbero().getId()
                        : null);

        dto.setServicioId(
                turno.getServicio() != null
                        ? turno.getServicio().getId()
                        : null);

        return dto;
    }

    private Turno toEntity(TurnoDTO dto) {

        if (dto == null) {
            return null;
        }

        Cliente cliente = clienteRepo
                .findById(dto.getClienteId())
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        Barbero barbero = barberoRepo
                .findById(dto.getBarberoId())
                .orElseThrow(() ->
                        new RuntimeException("Barbero no encontrado"));

        Servicio servicio = servicioRepo
                .findById(dto.getServicioId())
                .orElseThrow(() ->
                        new RuntimeException("Servicio no encontrado"));

        Turno turno = new Turno();

        turno.setId(dto.getId());
        turno.setFecha(dto.getFecha());
        turno.setHora(dto.getHora());
        turno.setCliente(cliente);
        turno.setBarbero(barbero);
        turno.setServicio(servicio);

        return turno;
    }
}