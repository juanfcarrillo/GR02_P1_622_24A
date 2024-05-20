package model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int peopleAmount;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Constructors, getters, and setters
    public Reservation() {}

    public Reservation(LocalDate startDate, LocalDate endDate, int peopleAmount, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.peopleAmount = peopleAmount;
        this.room = room;
    }

    public static Reservation createReservation(LocalDate startDate, LocalDate endDate, int peopleAmount) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setPeopleAmount(peopleAmount);
        return reservation;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"startDate\":\"" + startDate + "\"" +
                ", \"endDate\":\"" + endDate + "\"" +
                ", \"peopleAmount\":" + peopleAmount +
                ", \"roomNumber\":" + room.getRoomNumber() +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
