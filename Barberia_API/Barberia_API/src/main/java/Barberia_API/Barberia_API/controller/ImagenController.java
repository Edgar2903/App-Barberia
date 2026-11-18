package Barberia_API.Barberia_API.controller;

import Barberia_API.Barberia_API.service.impl.AutorizacionService;
import Barberia_API.Barberia_API.service.impl.ImagenServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final ImagenServiceImpl imagenServiceImpl;
    private final AutorizacionService autorizacionService;

    public ImagenController(ImagenServiceImpl imagenServiceImpl, AutorizacionService autorizacionService) {
        this.imagenServiceImpl = imagenServiceImpl;
        this.autorizacionService = autorizacionService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, List<String>>> subir(
            @RequestParam("files") List<MultipartFile> archivos,
            HttpSession session
    ) throws IOException {

        autorizacionService.verificarAdministrador(session);

        if (archivos == null || archivos.isEmpty()) {
            throw new IllegalArgumentException(
                    "Seleccioná al menos una imagen"
            );
        }

        if (archivos.size() > 5) {
            throw new IllegalArgumentException(
                    "Podés subir hasta 5 imágenes"
            );
        }

        List<String> imagenes = new ArrayList<>();

        for (MultipartFile archivo : archivos) {
            imagenes.add(imagenServiceImpl.guardar(archivo));
        }

        return ResponseEntity
                .status(201)
                .body(Map.of("imagenes", imagenes));
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<byte[]> ver(
            @PathVariable String nombre
    ) throws IOException {

        byte[] imagen = imagenServiceImpl.obtener(nombre);

        MediaType tipo = nombre.endsWith(".png")
                ? MediaType.IMAGE_PNG
                : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(tipo)
                .header(
                        "X-Content-Type-Options",
                        "nosniff"
                )
                .body(imagen);
    }
}