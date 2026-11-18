package Barberia_API.Barberia_API.service;

import Barberia_API.Barberia_API.dto.CategoriaDTO;
import Barberia_API.Barberia_API.entity.Categoria;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> findAll();
    CategoriaDTO findById(Long id);
    CategoriaDTO save(CategoriaDTO categoriaDTO);



    void delete(Long id);
    CategoriaDTO update(Long id, CategoriaDTO categoriaDTO);


    boolean existsByName(String nombreNormalizado);
    boolean existsByNombreIgnoreCaseAndIdNot(
            String nombre,
            Long id
    );
}
