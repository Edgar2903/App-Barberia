package Barberia_API.Barberia_API.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

@Service
public class ImagenServiceImpl {

    private final Path carpeta;

    public ImagenServiceImpl(
            @Value("${app.upload-dir:./uploads}") String directorio
    ) throws IOException {
        this.carpeta = Path.of(directorio)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(this.carpeta);
    }

    public String guardar(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Seleccioná una imagen"
            );
        }

        if (archivo.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "La imagen no puede superar los 2 MB"
            );
        }

        String extension;

        try (ImageInputStream entrada =
                     ImageIO.createImageInputStream(archivo.getInputStream())) {

            if (entrada == null) {
                throw new IllegalArgumentException("Imagen no válida");
            }

            Iterator<ImageReader> lectores =
                    ImageIO.getImageReaders(entrada);

            if (!lectores.hasNext()) {
                throw new IllegalArgumentException(
                        "El archivo no es una imagen válida"
                );
            }

            ImageReader lector = lectores.next();

            try {
                lector.setInput(entrada);

                String formato = lector.getFormatName()
                        .toLowerCase(Locale.ROOT);

                if (!formato.equals("jpeg") && !formato.equals("png")) {
                    throw new IllegalArgumentException(
                            "Solo se permiten imágenes JPG o PNG"
                    );
                }

                long pixeles =
                        (long) lector.getWidth(0) * lector.getHeight(0);

                if (pixeles > 20_000_000) {
                    throw new IllegalArgumentException(
                            "La imagen es demasiado grande en dimensiones"
                    );
                }

                // Comprueba que la imagen se pueda leer.
                lector.read(0);

                extension = formato.equals("jpeg") ? ".jpg" : ".png";
            } finally {
                lector.dispose();
            }
        }

        String nombre = UUID.randomUUID() + extension;
        Path destino = carpeta.resolve(nombre);

        Files.write(
                destino,
                archivo.getBytes(),
                StandardOpenOption.CREATE_NEW
        );

        return "/imagenes/" + nombre;
    }
    public byte[] obtener(String nombre) throws IOException {
        if (
                nombre == null ||
                        !nombre.matches(
                                "[a-f0-9\\-]{36}\\.(jpg|png)"
                        )
        ) {
            throw new IllegalArgumentException(
                    "Nombre de imagen no válido"
            );
        }

        Path archivo = carpeta.resolve(nombre).normalize();

        if (
                !archivo.startsWith(carpeta) ||
                        !Files.isRegularFile(archivo)
        ) {
            throw new IllegalArgumentException(
                    "Imagen no encontrada"
            );
        }

        return Files.readAllBytes(archivo);
    }
}