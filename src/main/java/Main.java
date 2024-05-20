import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entity.Reservation;
import model.entity.Room;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Room room = new Room(101, 100.0, 2);
        em.persist(room);

        Reservation reservation1 = new Reservation(LocalDate.now(), LocalDate.now().plusDays(1), 2, room);
        Reservation reservation2 = new Reservation(LocalDate.now(), LocalDate.now().plusDays(2), 3, room);

        room.addReservation(reservation1);
        room.addReservation(reservation2);

        em.persist(reservation1);
        em.persist(reservation2);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
