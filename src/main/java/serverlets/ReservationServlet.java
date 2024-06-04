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
        //String action = request.getParameter("action");

        /*if ("create".equals(action)) {
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
        } else if ("update".equals(action)) {
            */Long reservationId = Long.parseLong(request.getParameter("reservationId"));
            String newRoomNumber = request.getParameter("newRoomNumber"); // Asegúrate de usar el nombre correcto del parámetro
            String newCheckIn = request.getParameter("newCheckIn"); // Asegúrate de usar el nombre correcto del parámetro
            String newCheckOut = request.getParameter("newCheckOut"); // Asegúrate de usar el nombre correcto del parámetro
            String newPeople = request.getParameter("newPeopleAmount"); // Asegúrate de usar el nombre correcto del parámetro

            boolean success = reservationService.updateReservation(
                    reservationId,
                    LocalDate.parse(newCheckIn),
                    LocalDate.parse(newCheckOut),
                    Integer.parseInt(newPeople),
                    Integer.parseInt(newRoomNumber)
            );

            response.sendRedirect("reservations.jsp");
            /*if (success) {
                response.sendRedirect("reservations.jsp?message=Reservation updated successfully");
            } else {
                response.sendRedirect("reservations.jsp?error=Reservation update failed due to unavailability");
            }
        }*/
    }

}
