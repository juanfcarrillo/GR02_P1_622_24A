package model.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.entity.Reservation;
import model.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.PersistDatabase;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    @Mock
    private PersistDatabase mockPersistDatabase;

    @Mock
    private RoomService mockRoomService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        mockPersistDatabase = Mockito.mock(PersistDatabase.class);
        when(mockPersistDatabase.getEntityManager()).thenReturn(Mockito.mock(EntityManager.class));
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(mockPersistDatabase, mockRoomService);
    }

    @Test
    public void testCreateReservation() {
        Reservation mockReservation = Mockito.mock(Reservation.class);
        Room mockRoom = Mockito.mock(Room.class);

        when(mockRoomService.getRoomByNumber(Mockito.anyInt())).thenReturn(mockRoom);
        when(mockPersistDatabase.persist(mockReservation)).thenReturn(0);

        reservationService.createReservation(mockReservation, 1);

        Mockito.verify(mockReservation).setRoom(mockRoom);
    }

    @Test
    public void testGetAllReservations() {
        Reservation mockReservation1 = Mockito.mock(Reservation.class);
        Reservation mockReservation2 = Mockito.mock(Reservation.class);

        List<Reservation> allReservations = Arrays.asList(mockReservation1, mockReservation2);

        when(mockPersistDatabase.getAll(Reservation.class)).thenReturn(allReservations);

        List<Reservation> retrievedReservations = reservationService.getAllReservations();

        assertEquals(2, retrievedReservations.size());
        assertEquals(mockReservation1, retrievedReservations.get(0));
        assertEquals(mockReservation2, retrievedReservations.get(1));
    }
/*
    @Test
    public void testCheckReservationAvailability() {
        // Create mocks
        PersistDatabase mockPersistDatabase = Mockito.mock(PersistDatabase.class);
        RoomService mockRoomService = Mockito.mock(RoomService.class);
        TypedQuery<Reservation> mockTypedQuery = Mockito.mock(TypedQuery.class);

        // Define behavior
        when(mockPersistDatabase.createQuery(Mockito.anyString(), Mockito.<Class<Reservation>>any())).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Use mocks in test
        ReservationService reservationService = new ReservationService(mockPersistDatabase, mockRoomService);
        List<Reservation> result = reservationService.checkReservationAvailability(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(1));

        // Assertions
        assertTrue(result.isEmpty());
    }
*/
    @Test
    public void testUpdateReservation() {
        Reservation mockReservation = Mockito.mock(Reservation.class);
        Room mockRoom = Mockito.mock(Room.class);

        reservationService = new ReservationService(mockPersistDatabase, mockRoomService);
        TypedQuery<Reservation> mockTypedQuery = Mockito.mock(TypedQuery.class);

        when(mockPersistDatabase.find(Reservation.class, 1L)).thenReturn(mockReservation);
        when(mockRoomService.getRoomByNumber(101)).thenReturn(mockRoom);
        when(mockPersistDatabase.createQuery(Mockito.anyString(), Mockito.<Class<Reservation>>any())).thenReturn(mockTypedQuery);

        when(mockTypedQuery.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(Collections.emptyList());

        boolean result = reservationService.updateReservation(1L, LocalDate.now(), LocalDate.now().plusDays(1), 2, 101, "Test notes");
        assertTrue(result);

        when(mockPersistDatabase.find(Reservation.class, 2L)).thenReturn(null);

        result = reservationService.updateReservation(2L, LocalDate.now(), LocalDate.now().plusDays(1), 2, 101, "Test notes");

        assertFalse(result);
    }

    @Test
    public void testDeleteReservation() {
        Reservation mockReservation = Mockito.mock(Reservation.class);

        when(mockPersistDatabase.find(Reservation.class, 1L)).thenReturn(mockReservation);

        boolean result = reservationService.deleteReservation(1L);
        assertTrue(result);

        when(mockPersistDatabase.find(Reservation.class, 2L)).thenReturn(null);

        result = reservationService.deleteReservation(2L);
        assertFalse(result);
    }
}