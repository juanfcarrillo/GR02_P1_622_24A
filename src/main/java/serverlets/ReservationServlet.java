package serverlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Reserva;
import model.entity.Reservation;
import model.entity.Room;
import model.services.ReservaService;

import java.io.IOException;

import model.services.ReservationService;

import java.time.LocalDate;
import java.util.List;

@WebServlet(name="reservation-servlet", urlPatterns = {"/reservation-servlet"})
public class ReservationServlet extends HttpServlet {
    private ReservationService reservationService;
    public void init() {
        reservationService = new ReservationService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         List<Reservation> reservations = reservationService.getAllReservations();
         String reservationsJson = reservations.stream()
                 .map(Reservation::toString)
                 .reduce("[", (acc, room) -> acc + room + ",")
                 .replaceFirst(".$", "]");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print(reservationsJson);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String roomNumber = request.getParameter("roomNumber");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String people = request.getParameter("peopleAmount");

        // Crear una nueva instancia de Reservation
        Reservation newReservation = Reservation.createReservation(
                LocalDate.parse(checkIn),
                LocalDate.parse(checkOut),
                Integer.parseInt(people)
        );

        reservationService.createReservation(newReservation, Integer.parseInt(roomNumber));

        response.sendRedirect("reservations.jsp");
    }
}
