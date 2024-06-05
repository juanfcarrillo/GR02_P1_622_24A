package model.services;

import model.entity.Reserva;
import utils.PersistDatabase;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de reservas que maneja las operaciones relacionadas con las reservas de habitaciones.
 */
public class ReservaService {
    // Instancia de PersistDatabase para manejar la persistencia de datos.
    private final PersistDatabase persistDatabase;

    /**
     * Constructor sin parámetros que inicializa una nueva instancia de PersistDatabase.
     */
    public ReservaService() {
        persistDatabase = new PersistDatabase();
    }

    /**
     * Constructor que acepta una instancia de PersistDatabase.
     * @param persistDatabase Instancia de PersistDatabase a utilizar.
     */
    public ReservaService(PersistDatabase persistDatabase) {
        this.persistDatabase = persistDatabase;
    }

    /**
     * Registra una nueva reserva en la base de datos.
     * @param reserva La reserva a registrar.
     */
    public void registrarReserva(Reserva reserva) {
        int result = persistDatabase.persist(reserva);
        if (result == 0) {
            System.out.println("Reserva registrada con éxito");
        } else {
            System.out.println("Error al registrar la reserva");
        }
    }

    /**
     * Obtiene una lista de habitaciones disponibles.
     * @return Lista de reservas que representan habitaciones disponibles.
     */
    public List<Reserva> getAvailableRooms() {
        // Busca todas las reservas
        List<Reserva> allReservas = persistDatabase.getAll(Reserva.class);
        return filterAvailableRooms(allReservas);
    }

    /**
     * Filtra las reservas para obtener solo las habitaciones disponibles.
     * @param allReservas Lista de todas las reservas.
     * @return Lista de reservas que representan habitaciones disponibles.
     */
    public List<Reserva> filterAvailableRooms(List<Reserva> allReservas) {
        return allReservas.stream()
                .filter(Reserva::getEstaReservado) // Filtra las reservas que están disponibles
                .collect(Collectors.toList());
    }
}
