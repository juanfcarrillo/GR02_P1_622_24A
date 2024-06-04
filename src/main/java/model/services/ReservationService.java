package model.services;

import model.entity.Reservation;
import model.entity.Room;
import utils.PersistDatabase;

import java.time.LocalDate;
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
        System.out.println("Room found: " + room);
        if (room != null) {
            reservation.setRoom(room);
            System.out.println("Creating reservation: " + reservation);
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

    public boolean updateReservation(Long reservationId, LocalDate newStartDate, LocalDate newEndDate, int newPeopleAmount, int newRoomNumber) {
        Reservation existingReservation = persistDatabase.find(Reservation.class, reservationId);

        if (existingReservation != null) {
            Room newRoom = roomService.getRoomByNumber(newRoomNumber);
            if (newRoom == null) {
                return false; // Nueva habitación no encontrada
            }

            // Validar disponibilidad
            List<Reservation> conflictingReservations = persistDatabase
                    .checkReservationAvailability(newRoom.getId(), reservationId, newStartDate, newEndDate);

            if (conflictingReservations.isEmpty()) {
                existingReservation.setStartDate(newStartDate);
                existingReservation.setEndDate(newEndDate);
                existingReservation.setPeopleAmount(newPeopleAmount);
                existingReservation.setRoom(newRoom);
                persistDatabase.update(existingReservation);
                return true;
            } else {
                return false; // Conflicto con otra reserva
            }
        }
        return false; // Reserva no encontrada
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
