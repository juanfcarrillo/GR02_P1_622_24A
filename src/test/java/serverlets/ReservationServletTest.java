package serverlets;

import model.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Reservation;
import model.services.ReservationService;
import serverlets.ReservationServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServletTest {

    @InjectMocks
    private ReservationServlet reservationServlet;

    @Mock
    private ReservationService reservationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("reservationId")).thenReturn("1");
        when(request.getParameter("roomNumber")).thenReturn("101");
        when(request.getParameter("newCheckIn")).thenReturn(LocalDate.now().toString());
        when(request.getParameter("newCheckOut")).thenReturn(LocalDate.now().plusDays(1).toString());
        when(request.getParameter("peopleAmount")).thenReturn("2");
        when(request.getParameter("newReservationNotes")).thenReturn("Test notes");

        when(reservationService.updateReservation(anyLong(), any(LocalDate.class), any(LocalDate.class), anyInt(), anyInt(), anyString()))
                .thenReturn(true);

        reservationServlet.doPost(request, response);

        verify(response).sendRedirect("reservations.jsp?message=Reservation updatesuccessfully");
    }
}
