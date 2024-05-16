package utils;

import jakarta.persistence.*;

import java.util.List;

public class ConexionBD {
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static EntityTransaction transaction = entityManager.getTransaction();

    public static void endConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }
    public static int persist(Object object) {
        try {
            transaction.begin();
            entityManager.persist(object);
            transaction.commit();
            return 0; // Devuelve 0 si la operación fue exitosa
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return 1; // Devuelve 1 si hubo un error
        }
    }

    public static EntityManager getStaticEntityManager() {
        return ConexionBD.entityManager;
    }

    public static <T> List<T> getAll(Class<T> clazz) {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz);
        return query.getResultList();
    }
}
