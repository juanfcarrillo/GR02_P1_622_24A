package model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageURL;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    // Constructors, getters, and setters
    public Room() {}

    public Room(int roomNumber, double price, int capacity, String roomName, String description, String imageURL) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.capacity = capacity;
        this.roomName = roomName;
        this.description = description;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"roomName\": \"" + roomName + "\"" +
                ", \"roomNumber\": " + roomNumber +
                ", \"price\": " + price +
                ", \"capacity\": " + capacity +
                ", \"description\": \"" + description + "\"" +
                ", \"imageURL\": \"" + imageURL + "\"" +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setRoom(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setRoom(null);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
