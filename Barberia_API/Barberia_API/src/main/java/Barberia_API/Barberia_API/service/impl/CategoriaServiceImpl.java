package Barberia_API.Barberia_API.service.impl;

import Barberia_API.Barberia_API.dto.CategoriaDTO;
import Barberia_API.Barberia_API.entity.Categoria;
import Barberia_API.Barberia_API.service.CategoriaService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import Barberia_API.Barberia_API.repository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {


    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream().map(categoria -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId(categoria.getId());
            categoriaDTO.setNombre(categoria.getNombre());
            categoriaDTO.setDescripcion(categoria.getDescripcion());
            return categoriaDTO;
        }).toList();

    }

    @Override
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());

        return categoriaDTO;

    }

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        String nombreNormalizado = categoriaDTO.getNombre().trim();

        if (categoriaRepository.existsByNombreIgnoreCase(nombreNormalizado)) {
            throw new IllegalArgumentException("La categoría ya existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(nombreNormalizado);

        String descripcion = categoriaDTO.getDescripcion();
        categoria.setDescripcion(descripcion == null ? null : descripcion.trim());

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        CategoriaDTO categoriaRespuesta = new CategoriaDTO();

        categoriaRespuesta.setId(categoriaGuardada.getId());
        categoriaRespuesta.setNombre(categoriaGuardada.getNombre());
        categoriaRespuesta.setDescripcion(categoriaGuardada.getDescripcion());

        return categoriaRespuesta;
    }


    @Override
    public void delete(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        String nombreNormalizado = categoriaDTO.getNombre().trim();

        boolean nombreRepetido = categoriaRepository.existsByNombreIgnoreCaseAndIdNot(nombreNormalizado, id);

        if (nombreRepetido) {
            throw new IllegalArgumentException("La categoría ya existe");
        }

        categoria.setNombre(nombreNormalizado);

        String descripcion = categoriaDTO.getDescripcion();

        categoria.setDescripcion(descripcion == null ? null : descripcion.trim());

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        CategoriaDTO categoriaRespuesta = new CategoriaDTO();

        categoriaRespuesta.setId(categoriaActualizada.getId());

        categoriaRespuesta.setNombre(categoriaActualizada.getNombre());

        categoriaRespuesta.setDescripcion(categoriaActualizada.getDescripcion());

        return categoriaRespuesta;
    }

    @Override
    public boolean existsByName(String nombreNormalizado) {
        return false;
    }

    @Override
    public boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id) {
        return false;
    }
}




