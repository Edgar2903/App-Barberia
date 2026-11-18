package Barberia_API.Barberia_API.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barberos")
public class Barbero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private Double valoracion;

    @ElementCollection
    @CollectionTable(name = "barbero_imagenes", joinColumns = @JoinColumn(name = "barbero_id"))
    @Column(name = "imagen")
    private List<String> imagenes = new ArrayList<>();

    public Barbero() {
    }

    public Barbero(Long id, String nombre, String apellido, String especialidad, Double valoracion, List<String> imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.valoracion = valoracion;
        this.imagenes = imagenes;
    }

    public Barbero(String nombre, String apellido, String especialidad, Double valoracion, List<String> imagenes) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.valoracion = valoracion;
        this.imagenes = imagenes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }
}
