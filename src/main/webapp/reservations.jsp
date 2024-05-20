<%--
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
        <h1 class="title">Reservations</h1>
        <div id="reservations" class="carousel">
        </div>
    </section>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            fetch('reservation-servlet')
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    data.forEach(reservation => {
                        const reservationElement = document.createElement('div')
                        reservationElement.classList.add('card')
                        reservationElement.classList.add("carousel-item")
                        reservationElement.classList.add("reserv")
                        reservationElement.innerHTML =
                            "<p class='card-title'> Reservacion N:" + reservation.id + "</p>" +
                            "<div class='card-body'>" +
                            "<p>Check In: " + reservation.startDate + "</p>" +
                            "<p>Check Out: " + reservation.endDate + "</p>" +
                            "<p>Room: " + reservation.roomNumber + "</p>" +
                            "<p>People Amount: " + reservation.peopleAmount + "</p>" +
                            "</div>";
                        document.getElementById("reservations").appendChild(reservationElement)
                    })
                })
        })
    </script>
</main>
</body>
</html>
