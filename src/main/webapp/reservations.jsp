<%@ page import="model.entity.Reservation" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: juancarrillo
  Date: 19/5/24
  Time: 7:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservations</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.11.1/dist/full.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/reservations.css">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<main class="main">
    <section class="container">
        <h1 class="title flex flex-col items-center text-4xl font-bold">Reservations</h1>
        <div id="reservations" class="carousel">
            <%
                List<Reservation> reservations = (List<model.entity.Reservation>) request.getAttribute("reservations");
                if (reservations != null) {
                    for (model.entity.Reservation reservation : reservations) {
            %>
            <div class="card carousel-item reserv">
                <p class="card-title">Reservación N: <%= reservation.getId() %></p>
                <div class="card-body">
                    <p>Check In: <%= reservation.getStartDate() %></p>
                    <p>Check Out: <%= reservation.getEndDate() %></p>
                    <p>Room: <%= reservation.getRoom().getRoomNumber() %></p>
                    <p>People Amount: <%= reservation.getPeopleAmount() %></p>
                    <button onclick="modificarReserva(<%= reservation.getId() %>)" class="btn bg-blue-500 text-white">Modificar</button>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </section>

    <!-- Modal para modificar reserva -->
    <dialog id="modalModificarReserva" class="modal" role="dialog" aria-labelledby="modal-title" aria-describedby="modal-description">
        <div class="modal-box text-white">
            <h2 id="modal-title">Modificar Reserva</h2>
            <form action="reservation-servlet" method="POST" class="space-y-4">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="reservationId" name="reservationId">
                <div>
                    <label for="newCheckIn" class="block">Nueva Fecha de Check In:</label>
                    <input type="date" id="newCheckIn" name="newCheckIn" class="input input-bordered w-full" required>
                </div>
                <div>
                    <label for="newCheckOut" class="block">Nueva Fecha de Check Out:</label>
                    <input type="date" id="newCheckOut" name="newCheckOut" class="input input-bordered w-full" required>
                </div>
                <div>
                    <label for="newPeopleAmount" class="block">Nueva Cantidad de Personas:</label>
                    <input type="number" id="newPeopleAmount" name="newPeopleAmount" class="input input-bordered w-full" min="1" required>
                </div>
                <div>
                    <label for="newRoomNumber" class="block">Nuevo Número de Habitación:</label>
                    <input type="number" id="newRoomNumber" name="newRoomNumber" class="input input-bordered w-full" required>
                </div>
                <input type="submit" class="btn w-full bg-blue-500 text-white" value="Modificar">
            </form>
        </div>
    </dialog>

    <!-- Modal para eliminar reserva -->
    <dialog id="modalEliminarReserva" class="modal" role="dialog" aria-labelledby="modal-title-eliminar" aria-describedby="modal-description-eliminar">
        <div class="modal-box text-white">
            <h2 id="modal-title-eliminar">Eliminar Reserva</h2>
            <form id="formEliminarReserva" action="reservation-servlet" method="POST" class="space-y-4">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" id="reservationIdEliminar" name="reservationIdEliminar">
                <p>¿Estás seguro que deseas eliminar esta reserva?</p>
                <div class="flex justify-end space-x-4">
                    <button type="button" class="btn bg-gray-400 text-white" onclick="closeModal('modalEliminarReserva')">Cancelar</button>
                    <input type="submit" class="btn bg-red-500 text-white" value="Eliminar">
                </div>
            </form>
        </div>
    </dialog>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            fetch('reservation-servlet')
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    data.forEach(reservation => {
                        const reservationElement = document.createElement('div');
                        reservationElement.classList.add('card', 'carousel-item', 'reserv');
                        reservationElement.innerHTML =
                            "<p class='card-title'>Reservacion N:" + reservation.id + "</p>" +
                            "<div class='card-body'>" +
                            "<p>Check In: " + reservation.startDate + "</p>" +
                            "<p>Check Out: " + reservation.endDate + "</p>" +
                            "<p>Room: " + reservation.roomNumber + "</p>" +
                            "<p>People Amount: " + reservation.peopleAmount + "</p>" +
                            "<button onclick='modificarReserva(" + reservation.id + ")' class='btn bg-blue-500 text-white m-2'>Modificar</button>" +
                            "<button onclick='eliminarReserva(" + reservation.id + ")' class='btn bg-blue-500 text-white m-2'>Eliminar</button>" +
                        "</div>";
                        document.getElementById("reservations").appendChild(reservationElement);
                    });
                });
        });

        function modificarReserva(id) {
            document.getElementById("reservationId").value = id;
            document.getElementById("modalEliminarReserva")
        }

        function eliminarReserva(id) {
            document.getElementById("reservationIdEliminar").value = id;
            document.getElementById("modalEliminarReserva").showModal();
        }
    </script>
</main>
</body>
</html>
