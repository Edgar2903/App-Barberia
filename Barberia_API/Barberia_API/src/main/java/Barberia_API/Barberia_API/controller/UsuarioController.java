package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.dto.LoginDTO;
import Barberia_API.Barberia_API.dto.RegistroUsuarioDTO;
import Barberia_API.Barberia_API.dto.RolUsuarioDTO;
import Barberia_API.Barberia_API.dto.UsuarioDTO;
import Barberia_API.Barberia_API.service.impl.AutorizacionService;
import Barberia_API.Barberia_API.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AutorizacionService autorizacionService;

    public UsuarioController(UsuarioService usuarioService, AutorizacionService autorizacionService) {
        this.usuarioService = usuarioService;
        this.autorizacionService = autorizacionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody RegistroUsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioRegistrado = usuarioService.save(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> iniciarSesion(
            @Valid @RequestBody LoginDTO loginDTO,
            HttpSession session
    ) {
        UsuarioDTO usuario =
                usuarioService.login(loginDTO);

        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioRol", usuario.getRol());

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> cerrarSesion(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/rol")
    public ResponseEntity<UsuarioDTO> cambiarRol(
            @PathVariable Long id,
            @Valid @RequestBody RolUsuarioDTO rolDTO,
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        Object usuarioId = session.getAttribute("usuarioId");

        if (id.equals(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No podés modificar el rol de tu propia cuenta"
            );
        }

        UsuarioDTO usuarioActualizado =
                usuarioService.changeRole(id, rolDTO);

        return ResponseEntity.ok(usuarioActualizado);
    }

    @GetMapping
    public ResponseEntity<java.util.List<UsuarioDTO>> listarUsuarios(
            HttpSession session
    ) {
        autorizacionService.verificarAdministrador(session);

        java.util.List<UsuarioDTO> usuarios =
                usuarioService.findAll();

        return ResponseEntity.ok(usuarios);
    }

    private void verificarAdministrador(HttpSession session) {
        Object rol = session.getAttribute("usuarioRol");

        if (!"ADMIN".equals(rol)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Acceso permitido únicamente a administradores"
            );
        }
    }
}
