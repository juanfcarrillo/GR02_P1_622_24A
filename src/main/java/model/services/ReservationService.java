package model.services;

import jakarta.persistence.EntityManager;
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

    public boolean createReservation(Reservation reservation, int roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);
        System.out.println("Room found: " + room);
        if (room != null) {
            reservation.setRoom(room);
            System.out.println("Creating reservation: " + reservation);
            //int result = persistDatabase.persist(reservation);
            persistDatabase.create(reservation);
            return true;
        } else {
            System.out.println("Room not found");
            return false;
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
            List<Reservation> conflictingReservations = checkReservationAvailability(newRoom.getId(), reservationId, newStartDate, newEndDate);

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

    public boolean deleteReservation(Long reservationId) {
        Reservation reservation = persistDatabase.find(Reservation.class, reservationId);

        if (reservation != null) {
            reservation.setReserved(false);
            persistDatabase.update(reservation);
            return true;
        }

        return false; // Reserva no encontrada
    }


    public void getReservation() {
        // Get an existing reservation
    }

    public List<Reservation> getAllReservations() {
        return persistDatabase.getAll(Reservation.class);
    }

    // Método para validar disponibilidad de reserva
    public List<Reservation> checkReservationAvailability(Long roomId, Long reservationId, LocalDate newStartDate, LocalDate newEndDate) {
        EntityManager entityManager = persistDatabase.getEntityManager();
        List<Reservation> conflictingReservations = entityManager.createQuery(
                        "SELECT r FROM Reservation r " +
                                "WHERE r.room.id = :roomId " +
                                "AND r.id != :reservationId " +
                                "AND (:newStartDate BETWEEN r.startDate AND r.endDate OR :newEndDate BETWEEN r.startDate AND r.endDate)",
                        Reservation.class)
                .setParameter("roomId", roomId)
                .setParameter("reservationId", reservationId)
                .setParameter("newStartDate", newStartDate)
                .setParameter("newEndDate", newEndDate)
                .getResultList();

        return conflictingReservations;
    }
}
