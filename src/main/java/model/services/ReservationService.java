package model.services;

import model.entity.Reservation;
import model.entity.Room;
import utils.PersistDatabase;

import java.util.List;

public class ReservationService {
    private final PersistDatabase persistDatabase;

    public ReservationService() {
        this.persistDatabase = new PersistDatabase();
    }

    public void createReservation(Reservation reservation, int roomNumber) {
        RoomService roomService = new RoomService();
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
