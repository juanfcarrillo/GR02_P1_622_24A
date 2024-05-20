package model.services;

import model.entity.Reservation;
import model.entity.Room;
import utils.PersistDatabase;

import java.util.List;

public class ReservationService {
    private final PersistDatabase persistDatabase;
    private RoomService roomService;

    public ReservationService() {
        this.roomService = new RoomService();
        this.persistDatabase = new PersistDatabase();
    }

    public ReservationService(PersistDatabase persistDatabase, RoomService roomService) {
        this.persistDatabase = persistDatabase;
        this.roomService = roomService;
    }

    public void createReservation(Reservation reservation, int roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);

        if (room != null) {
            reservation.setRoom(room);
            int result = persistDatabase.persist(reservation);
            if (result == 0) {
                System.out.println("Reservation created successfully");
            } else {
                System.out.println("Error creating the reservation");
            }
        } else {
            System.out.println("Room not found");
        }
    }

    public void updateReservation() {
        // Update an existing reservation
    }

    public void deleteReservation() {
        // Delete an existing reservation
    }

    public void getReservation() {
        // Get an existing reservation
    }

    public List<Reservation> getAllReservations() {
        return persistDatabase.getAll(Reservation.class);
    }
}
