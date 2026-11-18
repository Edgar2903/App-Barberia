package Barberia_API.Barberia_API.controller;


import Barberia_API.Barberia_API.dto.CaracteristicaDTO;

import Barberia_API.Barberia_API.service.CaracteristicaService;

import Barberia_API.Barberia_API.service.impl.AutorizacionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caracteristicas")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;
    private final AutorizacionService autorizacionService;


    public CaracteristicaController(CaracteristicaService caracteristicaService, AutorizacionService autorizacionService) {
        this.caracteristicaService = caracteristicaService;
        this.autorizacionService = autorizacionService;
    }

    @GetMapping
    public ResponseEntity<List<CaracteristicaDTO>> findAll() {
        List<CaracteristicaDTO> caracteristicas = caracteristicaService.findAll();
        return ResponseEntity.ok(caracteristicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaDTO> findById(@PathVariable Long id) {
        CaracteristicaDTO caracteristica = caracteristicaService.findById(id);
        return ResponseEntity.ok(caracteristica);
    }

    @PostMapping
    public ResponseEntity<CaracteristicaDTO> save(
            @Valid @RequestBody CaracteristicaDTO caracteristicaDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        CaracteristicaDTO caracteristicaGuardada =
                caracteristicaService.save(caracteristicaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(caracteristicaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CaracteristicaDTO caracteristicaDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        CaracteristicaDTO caracteristicaActualizada =
                caracteristicaService.update(id, caracteristicaDTO);

        return ResponseEntity.ok(caracteristicaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);
        caracteristicaService.delete(id);
        return ResponseEntity.noContent().build();
        }

}
