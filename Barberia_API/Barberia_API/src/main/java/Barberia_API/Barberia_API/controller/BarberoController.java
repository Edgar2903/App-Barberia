package Barberia_API.Barberia_API.controller;


import Barberia_API.Barberia_API.entity.Barbero;
import Barberia_API.Barberia_API.service.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/barbero")
public class BarberoController {
    @Autowired
    private BarberoService barberoService;


    @GetMapping("/{id}")
    public Barbero findById(@PathVariable Long id){
        return barberoService.findById(id);
    }
    @PostMapping
   public Barbero save(@RequestBody Barbero barbero){
        return barberoService.save(barbero);
    }
    @PutMapping("/{id}")
    public Barbero update(@PathVariable Long id, @RequestBody Barbero barbero){
        return barberoService.update(id, barbero);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        barberoService.delete(id);
    }


    @GetMapping
    public List<Barbero> findAll(){
        return barberoService.findAll();
    }

    @GetMapping("/buscar")
    public Barbero findByName(@RequestParam String nombre){
        return barberoService.findByName(nombre);

    }

}