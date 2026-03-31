package Barberia_API.Barberia_API.controller;


import Barberia_API.Barberia_API.dto.TurnoDTO;
import Barberia_API.Barberia_API.entity.Turno;
import Barberia_API.Barberia_API.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public List<Turno> findAll(){
        return turnoService.findAll();
    }

    @GetMapping("/{id}")
    public Turno findById(@PathVariable Long id){
        return turnoService.findById(id);
    }

    @PostMapping
    public Turno save(@RequestBody TurnoDTO dto){
        return turnoService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        turnoService.delete(id);
    }
    @PutMapping("/{id}")
    public Turno update(@PathVariable Long id, @RequestBody Turno turno) {
        return turnoService.update(id, turno);
    }

}
