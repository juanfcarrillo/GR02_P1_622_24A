package serverlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Reservation;

import java.io.IOException;

import model.services.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name="reservation-servlet", urlPatterns = {"/reservation-servlet"})
public class ReservationServlet extends HttpServlet {
    private ReservationService reservationService;
    public void init() {
        reservationService = new ReservationService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Reservation> activeReservations = new ArrayList<>();
        List<Reservation> cancelledReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.isReserved()) {
                activeReservations.add(reservation);
            } else {
                cancelledReservations.add(reservation);
            }
        }

        // Convertir las listas a JSON
        String activeReservationsJson = activeReservations.isEmpty() ?
                "[]" :
                activeReservations.stream()
                 .map(Reservation::toString)
                 .reduce("[", (acc, room) -> acc + room + ",")
                 .replaceFirst(".$", "]");

        String cancelledReservationsJson = cancelledReservations.isEmpty() ?
                "[]" :
                cancelledReservations.stream()
                        .map(Reservation::toString)
                        .collect(Collectors.joining(",", "[", "]"));


        // Crear un objeto JSON que contenga ambas listas
        String combinedJson = "{\"activeReservations\":" + activeReservationsJson + ",\"cancelledReservations\":" + cancelledReservationsJson + "}";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(combinedJson);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        boolean success = false;

        switch (action)
        {
            case "create":
                // Obtener los parámetros del formulario
                String roomNumber = request.getParameter("roomNumber");
                String checkIn = request.getParameter("checkIn");
                String checkOut = request.getParameter("checkOut");
                String people = request.getParameter("capacity");
                String reservationNotes = request.getParameter("reservationNotes");

                // Crear una nueva instancia de Reservation
                Reservation newReservation = Reservation.createReservation(
                        LocalDate.parse(checkIn),
                        LocalDate.parse(checkOut),
                        Integer.parseInt(people),
                        reservationNotes // Añadir las notas de la reservación
                );
                success = reservationService.createReservation(newReservation, Integer.parseInt(roomNumber));
                break;
            case "update":

                Long reservationId = Long.parseLong(request.getParameter("reservationId"));
                String newRoomNumber = request.getParameter("roomNumber");
                String newCheckIn = request.getParameter("newCheckIn");
                String newCheckOut = request.getParameter("newCheckOut");
                String newPeople = request.getParameter("peopleAmount");
                String newReservationNotes = request.getParameter("newReservationNotes");

                success = reservationService.updateReservation(
                        reservationId,
                        LocalDate.parse(newCheckIn),
                        LocalDate.parse(newCheckOut),
                        Integer.parseInt(newPeople),
                        Integer.parseInt(newRoomNumber),
                        newReservationNotes
                );
                break;
            case "delete":
                Long reservationIdDelete = Long.parseLong(request.getParameter("reservationIdEliminar"));
                success = reservationService.deleteReservation(reservationIdDelete);
                break;
        }

        if (success) {
            response.sendRedirect("reservations.jsp?message=Reservation " + action + "successfully");
        } else {
            response.sendRedirect("reservations.jsp?error=Reservation " + action + "failed");
            response.getWriter().write("<script>alert(`No se puede cancelar reservas 15 días previo a su fecha de inicio.`);</script>");
        }
    }

}
