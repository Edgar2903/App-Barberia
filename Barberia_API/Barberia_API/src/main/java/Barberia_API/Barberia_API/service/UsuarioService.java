package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.LoginDTO;
import Barberia_API.Barberia_API.dto.RegistroUsuarioDTO;
import Barberia_API.Barberia_API.dto.RolUsuarioDTO;
import Barberia_API.Barberia_API.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO save(RegistroUsuarioDTO usuarioDTO);
    UsuarioDTO login(LoginDTO loginDTO);
    List<UsuarioDTO> findAll();

    UsuarioDTO changeRole(Long id, RolUsuarioDTO rolDTO);

}
