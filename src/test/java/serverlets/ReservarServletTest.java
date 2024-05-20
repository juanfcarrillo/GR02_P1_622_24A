package serverlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ReservarServletTest {

    private ReservarServlet reservarServlet;
    private Reserva mockReserva;

    @BeforeEach
    void setUp() {
        reservarServlet = new ReservarServlet();
        mockReserva = Mockito.mock(Reserva.class);
    }

    @Test
    void testCreateReserva() {
        // Configura el objeto mock para devolver valores específicos cuando se llamen sus métodos
        Mockito.when(mockReserva.getCantidadPersonas()).thenReturn(2);
        Mockito.when(mockReserva.getCedulaCliente()).thenReturn(12345678);
        Mockito.when(mockReserva.getDiaEntrada()).thenReturn(java.time.LocalDate.parse("2022-12-01"));
        Mockito.when(mockReserva.getDiaSalida()).thenReturn(java.time.LocalDate.parse("2022-12-05"));
        Mockito.when(mockReserva.getNumeroHabitacion()).thenReturn(101);
        Mockito.when(mockReserva.getEstaReservado()).thenReturn(true);

        // Llama al método que estás probando
        Reserva result = reservarServlet.createReserva(
                "101",
                "12345678",
                "2022-12-01",
                "2022-12-05",
                "2"
        );

        // Comprueba que los resultados son los esperados
        assertEquals(mockReserva.getCantidadPersonas(), result.getCantidadPersonas());
        assertEquals(mockReserva.getCedulaCliente(), result.getCedulaCliente());
        assertEquals(mockReserva.getDiaEntrada(), result.getDiaEntrada());
        assertEquals(mockReserva.getDiaSalida(), result.getDiaSalida());
        assertEquals(mockReserva.getNumeroHabitacion(), result.getNumeroHabitacion());
        assertEquals(mockReserva.getEstaReservado(), result.getEstaReservado());
    }

    @Test
    void testDoPostWithInvalidRoomNumber() {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);

        // Configura el objeto mock para devolver un número de habitación inválido
        Mockito.when(mockRequest.getParameter("numeroHabitacion")).thenReturn("invalid");

        // Llama al método doPostPublic y verifica que se lanza una excepción
        assertThrows(NumberFormatException.class, () -> reservarServlet.doPostPublic(mockRequest, mockResponse));
    }

    @ParameterizedTest
    @CsvSource({
            "101, 12345678, 2022-12-01, 2022-12-05, 2",
            "102, 87654321, 2022-12-10, 2022-12-15, 3"
    })
    void testCreateReserva(String numeroHabitacion, String cedula, String checkIn, String checkOut, String cantidadPersonas) {
        Reserva result = reservarServlet.createReserva(numeroHabitacion, cedula, checkIn, checkOut, cantidadPersonas);

        assertEquals(Integer.parseInt(numeroHabitacion), result.getNumeroHabitacion());
        assertEquals(Integer.parseInt(cedula), result.getCedulaCliente());
        assertEquals(java.time.LocalDate.parse(checkIn), result.getDiaEntrada());
        assertEquals(java.time.LocalDate.parse(checkOut), result.getDiaSalida());
        assertEquals(Integer.parseInt(cantidadPersonas), result.getCantidadPersonas());
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "102", "invalid"})
    void testDoPostPublicWithDifferentRoomNumbers(String numeroHabitacion) {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);

        // Configura el objeto mock para devolver el número de habitación proporcionado
        Mockito.when(mockRequest.getParameter("numeroHabitacion")).thenReturn(numeroHabitacion);

        // Si el número de habitación no es un número válido, se debe lanzar una NumberFormatException
        if ("invalid".equals(numeroHabitacion)) {
            assertThrows(NumberFormatException.class, () -> reservarServlet.doPostPublic(mockRequest, mockResponse));
        } else {
            // Si el número de habitación es válido, el método debe ejecutarse sin lanzar ninguna excepción
            reservarServlet.doPostPublic(mockRequest, mockResponse);
        }
    }


}