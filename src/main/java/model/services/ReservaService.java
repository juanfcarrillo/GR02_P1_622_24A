package model.services;

import model.entity.Reserva;
import utils.PersistDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class ReservaService {
    private final PersistDatabase persistDatabase;
    public ReservaService(PersistDatabase mockPersistDatabase) {
        persistDatabase = new PersistDatabase();
    }

    public ReservaService() {
        persistDatabase = new PersistDatabase();
    }

    public void registrarReserva(Reserva reserva) {
        int result = persistDatabase.persist(reserva);
        if (result == 0) {
            System.out.println("Reserva registrada con éxito");
        } else {
            System.out.println("Error al registrar la reserva");
        }
    }

    public List<Reserva> getAvailableRooms() {
        // Search for available rooms
        List<Reserva> allReservas = persistDatabase.getAll(Reserva.class);
        return filterAvailableRooms(allReservas);
    }

    public List<Reserva> filterAvailableRooms(List<Reserva> allReservas) {
        return allReservas.stream()
                .filter(Reserva::getEstaReservado)
                .collect(Collectors.toList());
    }
}
