package Barberia_API.Barberia_API.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "barbero_id")
    private Barbero barbero;
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    public Turno() {
    }

    public Turno(Long id, LocalDate fecha, LocalTime hora, Cliente cliente, Barbero barbero, Servicio servicio) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.barbero = barbero;
        this.servicio = servicio;
    }

    public Turno(LocalDate fecha, LocalTime hora, Cliente cliente, Barbero barbero, Servicio servicio) {
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.barbero = barbero;
        this.servicio = servicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}
