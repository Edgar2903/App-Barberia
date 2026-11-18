package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.dto.ServicioDTO;
import Barberia_API.Barberia_API.service.impl.AutorizacionService;
import Barberia_API.Barberia_API.service.ServicioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;
    private final AutorizacionService autorizacionService;

    public ServicioController(ServicioService servicioService, AutorizacionService autorizacionService) {
        this.servicioService = servicioService;
        this.autorizacionService = autorizacionService;
    }

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> findAll() {
        return ResponseEntity.ok(servicioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ServicioDTO> findByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(servicioService.findByNombre(nombre));
    }
    @PostMapping
    public ResponseEntity<ServicioDTO> save(
            @Valid @RequestBody ServicioDTO servicioDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicioService.save(servicioDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> update(@PathVariable Long id, @Valid @RequestBody ServicioDTO servicio, HttpSession session) {
        return ResponseEntity.ok(servicioService.update(id, servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
