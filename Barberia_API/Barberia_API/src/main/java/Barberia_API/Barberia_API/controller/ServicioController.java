package Barberia_API.Barberia_API.controller;


import Barberia_API.Barberia_API.entity.Servicio;
import Barberia_API.Barberia_API.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> findAll(){
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public Servicio findById(@PathVariable Long id){
        return servicioService.findById(id);
    }

    @GetMapping("/buscar")
    public Servicio findByNombre(@RequestParam String nombre){
        return servicioService.findByNombre(nombre);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Servicio servicio) {
        try {
            Servicio nuevo = servicioService.save(servicio);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @PutMapping
    public Servicio update(@PathVariable Long id, @RequestBody Servicio servicio){
        return servicioService.update(id, servicio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        servicioService.delete(id);
    }
}
