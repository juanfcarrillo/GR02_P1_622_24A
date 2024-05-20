<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel NewYork - Reservas</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.11.1/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<header class="flex">
    <h1 class="titulo">Gran Hotel NewYork-Cuartos diponibles</h1>
</header>

<main class="flex">
    <div></div>
    <section id="rooms" class="carousel">

    </section>

    <dialog id="my_modal_1" class="modal">
        <div class="modal-box">
            <h2>Reservar Habitacion</h2>
            <form action="reservation-servlet" method="POST">

                <input type="hidden" id="roomNumber" name="roomNumber">


                <label for="checkIn">Check In:</label>
                <input type="date" id="checkIn" name="checkIn" class="input input-bordered" required><br>

                <label for="checkOut">Check Out:</label>
                <input type="date" id="checkOut" name="checkOut" class="input input-bordered" required><br>

                <label for="peopleAmount">Cantidad de Personas:</label>
                <input type="number" id="peopleAmount" name="peopleAmount" class="input input-bordered" min="1" required><br>

                <input type="submit" class="btn" value="Reservar">
            </form>
        </div>
    </dialog>

    <script>
        function reservar(numero) {
            console.log(numero)
            my_modal_1.showModal()
            document.getElementById("roomNumber").value = numero
        }

        document.addEventListener('DOMContentLoaded', () => {
            fetch('room-servlet')
                .then(response => response.json())
                .then(data => {
                    data.forEach(room => {
                        const roomElement = document.createElement('div')
                        roomElement.classList.add('card')
                        roomElement.classList.add("carousel-item")
                        roomElement.classList.add("reserv")
                        roomElement.innerHTML =
                            "<p class='card-title'>Numero: " + room.roomNumber + "</p>" +
                            "<div class='card-body'>" +
                            "<p>Capacidad: " + room.capacity + "</p>" +
                            "<p>Precio: " + room.price + "</p>" +
                            "</div>" +
                            `<button class="btn" onclick=reservar(` + room.roomNumber + `)>Reservar</input>`

                        document.getElementById("rooms").appendChild(roomElement)
                    })
                })

        })
    </script>
</main>
</body>
</html>