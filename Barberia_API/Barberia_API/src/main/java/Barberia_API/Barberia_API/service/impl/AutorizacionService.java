package Barberia_API.Barberia_API.service.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AutorizacionService {

    public void verificarAdministrador(HttpSession session) {
        String rol = (String) session.getAttribute("usuarioRol");

        if (!"ADMIN".equals(rol)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Acceso denegado: se requiere rol de administrador."
            );
        }
    }
}