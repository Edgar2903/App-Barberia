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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class TurnoServiceImpl implements TurnoService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private BarberoRepository barberoRepo;

    @Autowired
    private ServicioRepository servicioRepo;



    @Autowired
    private TurnoRepository turnoRepository;

    @Override
    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    @Override
    public Turno findById(Long id) {
        return turnoRepository.findById(id).get();
    }




    @Override
    public Turno save(TurnoDTO dto) {

        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Barbero barbero = barberoRepo.findById(dto.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));

        Servicio servicio = servicioRepo.findById(dto.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // 🔥 Validación duplicado
        boolean existe = turnoRepository.existsByFechaAndHora(
                dto.getFecha(),
                dto.getHora()

        );

        if (existe) {
            throw new RuntimeException("Ese horario ya está ocupado");
        }

        Turno turno = new Turno();
        turno.setFecha(dto.getFecha());
        turno.setHora(dto.getHora());
        turno.setCliente(cliente);
        turno.setBarbero(barbero);
        turno.setServicio(servicio);

        return turnoRepository.save(turno);
    }

    @Override
    public void delete(Long id) {
        turnoRepository.deleteById(id);
    }

    @Override
    public Turno update(Long id, Turno turno) {

        Turno existente = findById(id);
        boolean existe = turnoRepository.existsByFechaAndHora(
                turno.getFecha(),
                turno.getHora()

        );
        if (existe &&
                !(existente.getFecha().equals(turno.getFecha()) &&
                        existente.getHora().equals(turno.getHora()) &&
                        existente.getBarbero().getId().equals(turno.getBarbero().getId()))) {

            throw new RuntimeException("Ese horario ya está ocupado");
        }

        if (turno.getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException("No podés modificar a una fecha pasada");
        }

        existente.setFecha(turno.getFecha());
        existente.setHora(turno.getHora());
        existente.setCliente(turno.getCliente());
        existente.setBarbero(turno.getBarbero());
        existente.setServicio(turno.getServicio());

        return turnoRepository.save(existente);
    }
}
