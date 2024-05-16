package model;

import model.entity.Reserva;

import java.time.LocalDate;

public class ReservaDTO {
    private int cedula;
    private int numeroHabitacion;
    private String checkIn;
    private String checkOut;
    private int cantidadPersonas;

    public ReservaDTO(int cedula, int numeroHabitacion, String checkIn, String checkOut, int cantidadPersonas) {
        this.cedula = cedula;
        this.numeroHabitacion = numeroHabitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cantidadPersonas = cantidadPersonas;
    }

    public Reserva createReserva(
            int cedula,
            int numeroHabitacion,
            String checkIn,
            String checkOut,
            int cantidadPersonas
    ) {
        Reserva reserva = new Reserva();
        reserva.setCedulaCliente(cedula);
        reserva.setNumeroHabitacion(numeroHabitacion);
        reserva.setDiaEntrada(LocalDate.parse(checkIn));
        reserva.setDiaSalida(LocalDate.parse(checkOut));
        reserva.setCantidadPersonas(cantidadPersonas);

        return reserva;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}
