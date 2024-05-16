package model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Integer id;

    @Column(name = "cedula_cliente")
    private Integer cedulaCliente;

    @Column(name = "numero_habitacion")
    private Integer numeroHabitacion;

    @Column(name = "dia_entrada")
    private LocalDate diaEntrada;

    @Column(name = "dia_salida")
    private LocalDate diaSalida;

    @Column(name = "cantidad_personas")
    private Integer cantidadPersonas;

    @Column(name = "estaReservado")
    private Boolean estaReservado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public LocalDate getDiaEntrada() {
        return diaEntrada;
    }

    public void setDiaEntrada(LocalDate diaEntrada) {
        this.diaEntrada = diaEntrada;
    }

    public LocalDate getDiaSalida() {
        return diaSalida;
    }

    public void setDiaSalida(LocalDate diaSalida) {
        this.diaSalida = diaSalida;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Boolean getEstaReservado() {
        return estaReservado;
    }

    public void setEstaReservado(Boolean estaReservado) {
        this.estaReservado = estaReservado;
    }
}