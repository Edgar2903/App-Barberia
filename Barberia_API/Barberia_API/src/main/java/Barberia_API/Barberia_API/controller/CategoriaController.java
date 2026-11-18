package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.dto.CategoriaDTO;
import Barberia_API.Barberia_API.service.impl.AutorizacionService;
import Barberia_API.Barberia_API.service.CategoriaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final AutorizacionService autorizacionService;

    public CategoriaController(
            CategoriaService categoriaService,
            AutorizacionService autorizacionService
    ) {
        this.categoriaService = categoriaService;
        this.autorizacionService = autorizacionService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> categorias =
                categoriaService.findAll();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(
            @PathVariable Long id
    ) {
        CategoriaDTO categoria =
                categoriaService.findById(id);

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> save(
            @Valid @RequestBody CategoriaDTO categoriaDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        CategoriaDTO categoriaGuardada =
                categoriaService.save(categoriaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoriaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO categoriaDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        CategoriaDTO categoriaActualizada =
                categoriaService.update(id, categoriaDTO);

        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        categoriaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}