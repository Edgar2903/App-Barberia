package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.dto.TurnoDTO;
import Barberia_API.Barberia_API.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> findAll() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> save(
            @Valid @RequestBody TurnoDTO dto) {

        return ResponseEntity.ok(turnoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TurnoDTO dto) {

        return ResponseEntity.ok(turnoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}