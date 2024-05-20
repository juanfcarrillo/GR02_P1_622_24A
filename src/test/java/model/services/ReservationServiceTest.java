package model.services;

import model.entity.Reservation;
import model.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.PersistDatabase;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}