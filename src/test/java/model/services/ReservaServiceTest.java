package model.services;

import model.entity.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.PersistDatabase;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReservaServiceTest {

    @Mock
    private PersistDatabase mockPersistDatabase;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPartialFilter() {
        Reserva reserva1 = Mockito.mock(Reserva.class);
        Reserva reserva2 = Mockito.mock(Reserva.class);
        Reserva reserva3 = Mockito.mock(Reserva.class);

        when(reserva1.getEstaReservado()).thenReturn(true);
        when(reserva2.getEstaReservado()).thenReturn(false);
        when(reserva3.getEstaReservado()).thenReturn(true);

        List<Reserva> allReservas = Arrays.asList(reserva1, reserva2, reserva3);

        List<Reserva> availableReservas = reservaService.filterAvailableRooms(allReservas);

        assertEquals(2, availableReservas.size());
        assertTrue(availableReservas.contains(reserva1));
        assertTrue(availableReservas.contains(reserva3));
    }

    @Test
    public void testCompleteFilter() {
        Reserva reserva1 = Mockito.mock(Reserva.class);
        Reserva reserva2 = Mockito.mock(Reserva.class);

        when(reserva1.getEstaReservado()).thenReturn(true);
        when(reserva2.getEstaReservado()).thenReturn(true);

        List<Reserva> allReservas = Arrays.asList(reserva1, reserva2);

        List<Reserva> availableReservas = reservaService.filterAvailableRooms(allReservas);

        assertEquals(2, availableReservas.size());
        assertTrue(availableReservas.contains(reserva1));
        assertTrue(availableReservas.contains(reserva2));
    }

    @Test
    public void testEmptyFilter() {
        Reserva reserva1 = Mockito.mock(Reserva.class);
        Reserva reserva2 = Mockito.mock(Reserva.class);

        when(reserva1.getEstaReservado()).thenReturn(false);
        when(reserva2.getEstaReservado()).thenReturn(false);

        List<Reserva> allReservas = Arrays.asList(reserva1, reserva2);

        List<Reserva> availableReservas = reservaService.filterAvailableRooms(allReservas);

        assertEquals(0, availableReservas.size());
    }

    @Test
    public void testRegistrarReserva() {
        Reserva mockReserva = Mockito.mock(Reserva.class);

        when(mockPersistDatabase.persist(mockReserva)).thenReturn(0);

        reservaService.registrarReserva(mockReserva);
    }

    @Test
    public void testGetAvailableRooms() {
        Reserva mockReserva1 = Mockito.mock(Reserva.class);
        Reserva mockReserva2 = Mockito.mock(Reserva.class);

        when(mockReserva1.getEstaReservado()).thenReturn(true);
        when(mockReserva2.getEstaReservado()).thenReturn(false);

        List<Reserva> allReservas = Arrays.asList(mockReserva1, mockReserva2);

        when(mockPersistDatabase.getAll(Reserva.class)).thenReturn(allReservas);

        List<Reserva> availableReservas = reservaService.getAvailableRooms();

        assertEquals(0, availableReservas.size());
        assertFalse(availableReservas.contains(mockReserva1));
    }

}