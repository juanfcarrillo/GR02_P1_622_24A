import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entity.Cliente;


public class Main {
    public static void main(String[] args) {

        System.out.println("ASJGDHJASHDJKU");


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        try (emf; EntityManager em = emf.createEntityManager()) {

            System.out.println("ASJGDHJASHDJKU");

            // Crear un nuevo objeto Cliente
            Cliente cliente = new Cliente();
            cliente.setId(200);
            cliente.setNombre("Juan");
            cliente.setApellidos("Perez");

            em.getTransaction().begin();

            em.persist(cliente);

            em.getTransaction().commit();


        } catch (Exception e) {
            // Manejar cualquier excepción
            //if (ConexionBD.transaction.isActive()) {
            //    ConexionBD.transaction.rollback();
            //}
            System.out.println("ASJGDHJASHDJKU");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        // Cerrar el EntityManager
    }
}
