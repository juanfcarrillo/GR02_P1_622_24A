<%@ page import="model.entity.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservations</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.11.1/dist/full.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/reservations.css">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<header>
    <h1 class="title flex flex-col items-center text-4xl font-bold text-white py-4">Reservations</h1>
</header>
<main class="main">
    <section class="container carousel gap-4 md:gap-6 flex flex-col">
        <h2 class="text-2xl font-bold">Active Reservations</h2>
        <div id="active-reservations" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-5 gap-2 md:gap-2">
            <!-- Tarjetas de reservaciones activas se generarán aquí -->
        </div>
        <h2 class="text-2xl font-bold">Cancelled Reservations</h2>
        <div id="cancelled-reservations" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-5 gap-2 md:gap-2">
            <!-- Tarjetas de reservaciones inactivas se generarán aquí -->
        </div>
    </section>

    <!-- Modal para modificar reserva -->

    <div class="flex justify-center mt-6">
        <a href="http://localhost:8080/hotel/" class="btn bg-green-500 text-white">Realizar otra reservación</a>
    </div>

    <dialog id="modalModificarReserva" class="modal" role="dialog" aria-labelledby="modal-title" aria-describedby="modal-description">
        <div class="modal-box text-white">
            <h2 id="modal-title">Modificar Reserva</h2>
            <form id="modificarReservaForm" action="reservation-servlet" method="POST" class="space-y-4">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="reservationId" name="reservationId">

                <div>
                    <label for="roomNumber" class="block">Número de Habitación:</label>
                    <input type="text" id="roomNumber" name="roomNumber" class="input input-bordered w-full" readonly style="color: gray;">
                </div>

                <div>
                    <label for="peopleAmount" class="block">Cantidad de Personas:</label>
                    <input type="text" id="peopleAmount" name="peopleAmount" class="input input-bordered w-full" readonly style="color: gray;">
                </div>

                <div>
                    <label for="newCheckIn" class="block">Nueva Fecha de Check In:</label>
                    <input type="date" id="newCheckIn" name="newCheckIn" class="input input-bordered w-full" required>
                </div>

                <div>
                    <label for="newCheckOut" class="block">Nueva Fecha de Check Out:</label>
                    <input type="date" id="newCheckOut" name="newCheckOut" class="input input-bordered w-full" required>
                </div>

                <div>
                    <label for="newReservationNotes" class="block">Nuevas Notas de Reserva:</label>
                    <textarea id="newReservationNotes" name="newReservationNotes" class="textarea textarea-bordered w-full" rows="4"></textarea>
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
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Data received from server:", data);

                    const activeReservations = data.activeReservations || [];
                    const cancelledReservations = data.cancelledReservations || [];

                    console.log("Active Reservations:", activeReservations);
                    console.log("Cancelled Reservations:", cancelledReservations);

                    activeReservations.forEach(reservation => {
                        console.log("Processing active reservation:", reservation);

                        const reservationElement = document.createElement('div');
                        reservationElement.classList.add('card', 'carousel-item', 'reserv', 'm-2');
                        reservationElement.innerHTML =
                            "<p class='card-title'>Reservación N:" + reservation.id + "</p>" +
                            "<div class='card-body mt-2 relative'>" +
                            "<p>Check In: " + reservation.startDate + "</p>" +
                            "<p>Check Out: " + reservation.endDate + "</p>" +
                            "<p>Room: " + reservation.roomNumber + "</p>" +
                            "<p>People Amount: " + reservation.peopleAmount + "</p>" +
                            "<p>Notes: " + (reservation.reservationNotes ? reservation.reservationNotes : "No notes") + "</p>" +

                            `<button onclick='modificarReserva(`
                            + reservation.id + `, `
                            + reservation.roomNumber + `, `
                            + reservation.peopleAmount + `) 'class='btn bg-blue-500 text-white m-2'>Modificar</button>` +
                            "<button onclick='eliminarReserva(" + reservation.id + ")' class='btn bg-blue-500 text-white ml-2 mr-2'>Eliminar</button>" +
                            "</div>";
                        document.getElementById("active-reservations").appendChild(reservationElement);
                    });

                    cancelledReservations.forEach(reservation => {
                        console.log("Processing cancelled reservation:", reservation);

                        const reservationElement = document.createElement('div');
                        reservationElement.classList.add('card', 'carousel-item', 'reserv', 'm-2');
                        reservationElement.innerHTML =
                            "<p class='card-title'>Reservación N:" + reservation.id + "</p>" +
                            "<div class='card-body'>" +
                            "<p>Check In: " + reservation.startDate + "</p>" +
                            "<p>Check Out: " + reservation.endDate + "</p>" +
                            "<p>Room: " + reservation.roomNumber + "</p>" +
                            "<p>People Amount: " + reservation.peopleAmount + "</p>" +
                            "<p>Notes: " + (reservation.reservationNotes ? reservation.reservationNotes : "No notes") + "</p>" +
                            "</div>";
                        document.getElementById("cancelled-reservations").appendChild(reservationElement);
                    });
                })
                .catch(error => {
                    console.error('Error fetching reservations:', error);
                });
        });


        function modificarReserva(id, room, capacity) {
            // Llenar los campos del formulario con la información de la reserva seleccionada
            document.getElementById("reservationId").value = id;
            document.getElementById("roomNumber").value = room;
            document.getElementById("peopleAmount").value = capacity;

            // Mostrar el modal
            const modal = document.getElementById("modalModificarReserva");
            if (typeof modal.showModal === "function") {
                modal.showModal();
            } else {
                console.error("Dialog API not supported");
            }

    
        }

        function eliminarReserva(id) {
            document.getElementById("reservationIdEliminar").value = id;
            document.getElementById("modalEliminarReserva").showModal();
        }


    </script>
</main>
</body>
</html>
