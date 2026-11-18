package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.dto.BarberoDTO;
import Barberia_API.Barberia_API.service.BarberoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Barberia_API.Barberia_API.service.impl.AutorizacionService;

import java.util.List;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    private final BarberoService barberoService;
    private final AutorizacionService autorizacionService;


    public BarberoController(BarberoService barberoService, AutorizacionService autorizacionService) {
        this.barberoService = barberoService;
        this.autorizacionService = autorizacionService;
    }

    @GetMapping
    public ResponseEntity<List<BarberoDTO>> getAll() {
        return ResponseEntity.ok(barberoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(barberoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BarberoDTO> save(@Valid @RequestBody BarberoDTO dto, HttpSession session) {
        autorizacionService.verificarAdministrador(session);
        return ResponseEntity.ok(barberoService.save(dto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberoDTO> update(@PathVariable Long id, @Valid @RequestBody BarberoDTO dto, HttpSession session) {
        autorizacionService.verificarAdministrador(session);
        return ResponseEntity.ok(barberoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        autorizacionService.verificarAdministrador(session);
        barberoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}