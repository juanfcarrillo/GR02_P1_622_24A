<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel NewYork - Reservas</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.11.1/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/index.css">
</head>
<body class="bg-gray-100 text-gray-800">
<header class="bg-gray-800 text-white py-4">
    <h1 class="titulo flex flex-col items-center">Hotel NewYork</h1>
    <h2 class="subtitulo text-left ml-4 text-2xl">Cuartos disponibles</h2>
</header>

<main class="flex justify-center p-4">
    <section id="rooms" class="carousel flex flex-wrap gap-4 md:gap-8">
        <dialog id="my_modal_1" class="modal" role="dialog" aria-labelledby="modal-title" aria-describedby="modal-description">
            <div class="modal-box">
                <h2 id="modal-title">Reservar Habitación</h2>
                <form action="reservation-servlet" method="POST" class="space-y-4">
                    <input type="hidden" id="roomNumber" name="roomNumber">
                    <div>
                        <label for="checkIn" class="block">Check In:</label>
                        <input type="date" id="checkIn" name="checkIn" class="input input-bordered w-full" required>
                    </div>
                    <div>
                        <label for="checkOut" class="block">Check Out:</label>
                        <input type="date" id="checkOut" name="checkOut" class="input input-bordered w-full" required>
                    </div>
                    <div>
                        <label for="peopleAmount" class="block">Cantidad de Personas:</label>
                        <input type="number" id="peopleAmount" name="peopleAmount" class="input input-bordered w-full" min="1" required>
                    </div>
                    <input type="submit" class="btn w-full bg-blue-500 text-white" value="Reservar">
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
                            roomElement.classList.add('card', 'carousel-item', 'reserv', 'bg-white', 'shadow-md', 'p-4', 'rounded-lg', 'text-center', 'w-full', 'md:w-1/5')
                            roomElement.innerHTML =
                                "<p class='card-title font-bold text-lg'>Numero: " + room.roomNumber + "</p>" +
                                "<div class='card-body mt-2'>" +
                                "<p>Name: " + room.roomName + "</p>" +
                                "<p>Capacidad: " + room.capacity + "</p>" +
                                "<p>Precio: $" + room.price + "</p>" +
                                "</div>" +
                                `<button class="btn bg-blue-500 text-white mt-4" onclick=reservar(` + room.roomNumber + `)>Reservar</button>`

                            document.getElementById("rooms").appendChild(roomElement)
                        })
                    })
                    .catch(error => {
                        console.error('Error fetching room data:', error)
                    })
            })
        </script>
    </section>
</main>
</body>
</html>
