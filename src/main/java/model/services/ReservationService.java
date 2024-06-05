package model.services;

import model.entity.Reservation;
import model.entity.Room;
import utils.PersistDatabase;

import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de reservas que maneja las operaciones relacionadas con las reservas de habitaciones.
 */
public class ReservationService {
    // Instancia de PersistDatabase para manejar la persistencia de datos.
    private final PersistDatabase persistDatabase;
    private RoomService roomService;

    /**
     * Constructor sin parámetros que inicializa una nueva instancia de RoomService y PersistDatabase.
     */
    public ReservationService() {
        this.roomService = new RoomService();
        this.persistDatabase = new PersistDatabase();
    }

    /**
     * Constructor que acepta instancias de PersistDatabase y RoomService.
     * @param persistDatabase Instancia de PersistDatabase a utilizar.
     * @param roomService Instancia de RoomService a utilizar.
     */
    public ReservationService(PersistDatabase persistDatabase, RoomService roomService) {
        this.persistDatabase = persistDatabase;
        this.roomService = roomService;
    }

    /**
     * Crea una nueva reserva para una habitación específica.
     * @param reservation La reserva a crear.
     * @param roomNumber El número de la habitación a reservar.
     * @return true si la reserva se crea exitosamente, false si la habitación no se encuentra.
     */
    public boolean createReservation(Reservation reservation, int roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);
        System.out.println("Room found: " + room);
        if (room != null) {
            reservation.setRoom(room);
            System.out.println("Creating reservation: " + reservation);
            persistDatabase.create(reservation);
            return true;
        } else {
            System.out.println("Room not found");
            return false;
        }
    }

    /**
     * Actualiza una reserva existente con nuevos datos.
     * @param reservationId ID de la reserva a actualizar.
     * @param newStartDate Nueva fecha de inicio.
     * @param newEndDate Nueva fecha de fin.
     * @param newPeopleAmount Nuevo número de personas.
     * @param newRoomNumber Nuevo número de habitación.
     * @param reservationNotes Notas adicionales para la reserva.
     * @return true si la reserva se actualiza exitosamente, false en caso contrario.
     */
    public boolean updateReservation(Long reservationId, LocalDate newStartDate, LocalDate newEndDate, int newPeopleAmount, int newRoomNumber, String reservationNotes) {
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
                existingReservation.setReservationNotes(reservationNotes);
                persistDatabase.update(existingReservation);
                return true;
            } else {
                return false; // Conflicto con otra reserva
            }
        }
        return false; // Reserva no encontrada
    }

    /**
     * Elimina una reserva existente.
     * @param reservationId ID de la reserva a eliminar.
     * @return true si la reserva se elimina exitosamente, false si la reserva no se encuentra.
     */
    public boolean deleteReservation(Long reservationId) {
        Reservation reservation = persistDatabase.find(Reservation.class, reservationId);

        if (reservation != null) {
            reservation.setReserved(false);
            persistDatabase.update(reservation);
            return true;
        }

        return false; // Reserva no encontrada
    }

    /**
     * Obtiene una lista de todas las reservas.
     * @return Lista de todas las reservas.
     */
    public List<Reservation> getAllReservations() {
        return persistDatabase.getAll(Reservation.class);
    }

    /**
     * Valida la disponibilidad de una reserva en una habitación específica.
     * @param roomId ID de la habitación a verificar.
     * @param reservationId ID de la reserva a actualizar.
     * @param newStartDate Nueva fecha de inicio.
     * @param newEndDate Nueva fecha de fin.
     * @return Lista de reservas que causan conflicto de disponibilidad.
     */
    public List<Reservation> checkReservationAvailability(Long roomId, Long reservationId, LocalDate newStartDate, LocalDate newEndDate) {
        TypedQuery<Reservation> typedQuery = persistDatabase.createQuery(
                        "SELECT r FROM Reservation r " +
                                "WHERE r.room.id = :roomId " +
                                "AND r.id <> :reservationId " +
                                "AND (:newStartDate BETWEEN r.startDate AND r.endDate OR :newEndDate BETWEEN r.startDate AND r.endDate)",
                        Reservation.class)
                .setParameter("roomId", roomId)
                .setParameter("reservationId", reservationId)
                .setParameter("newStartDate", newStartDate)
                .setParameter("newEndDate", newEndDate);

        return typedQuery.getResultList();
    }
}
