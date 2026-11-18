package Barberia_API.Barberia_API.repository;

import Barberia_API.Barberia_API.entity.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombreNormalizado, Long id);
}
