package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.LoginDTO;
import Barberia_API.Barberia_API.dto.RegistroUsuarioDTO;
import Barberia_API.Barberia_API.dto.RolUsuarioDTO;
import Barberia_API.Barberia_API.dto.UsuarioDTO;
import Barberia_API.Barberia_API.entity.Usuario;
import Barberia_API.Barberia_API.repository.UsuarioRepository;
import Barberia_API.Barberia_API.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    public UsuarioDTO save(RegistroUsuarioDTO usuarioDTO) {

        String emailNormalizado = usuarioDTO.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmail(emailNormalizado)) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre().trim());
        usuario.setApellido(usuarioDTO.getApellido().trim());
        usuario.setEmail(emailNormalizado);
        usuario.setPassword(encoder.encode(usuarioDTO.getPassword()));
        usuario.setRol("USER");
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        UsuarioDTO usuarioRespuesta = new UsuarioDTO();
        usuarioRespuesta.setId(usuarioGuardado.getId());
        usuarioRespuesta.setNombre(usuarioGuardado.getNombre());
        usuarioRespuesta.setApellido(usuarioGuardado.getApellido());
        usuarioRespuesta.setEmail(usuarioGuardado.getEmail());
        usuarioRespuesta.setRol(usuarioGuardado.getRol());
        return usuarioRespuesta;
    }

    @Override
    public UsuarioDTO login(LoginDTO loginDTO) {

        String emailNormalizado =
                loginDTO.getEmail().trim().toLowerCase();

        Usuario usuario = usuarioRepository
                .findByEmail(emailNormalizado)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Correo o contraseña incorrectos"
                        )
                );

        if (!encoder.matches(
                loginDTO.getPassword(),
                usuario.getPassword()
        )) {
            throw new IllegalArgumentException(
                    "Correo o contraseña incorrectos"
            );
        }

        UsuarioDTO usuarioRespuesta = new UsuarioDTO();

        usuarioRespuesta.setId(usuario.getId());
        usuarioRespuesta.setNombre(usuario.getNombre());
        usuarioRespuesta.setApellido(usuario.getApellido());
        usuarioRespuesta.setEmail(usuario.getEmail());
        usuarioRespuesta.setRol(usuario.getRol());

        return usuarioRespuesta;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setId(usuario.getId());
                    usuarioDTO.setNombre(usuario.getNombre());
                    usuarioDTO.setApellido(usuario.getApellido());
                    usuarioDTO.setEmail(usuario.getEmail());
                    usuarioDTO.setRol(usuario.getRol());
                    return usuarioDTO;
                })
                .toList();
    }

    @Override
    public UsuarioDTO changeRole(Long id, RolUsuarioDTO rolDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setRol(rolDTO.getRol().trim().toUpperCase());
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        UsuarioDTO usuarioRespuesta = new UsuarioDTO();
        usuarioRespuesta.setId(usuarioActualizado.getId());
        usuarioRespuesta.setNombre(usuarioActualizado.getNombre());
        usuarioRespuesta.setApellido(usuarioActualizado.getApellido());
        usuarioRespuesta.setEmail(usuarioActualizado.getEmail());
        usuarioRespuesta.setRol(usuarioActualizado.getRol());

        return usuarioRespuesta;
    }
}
