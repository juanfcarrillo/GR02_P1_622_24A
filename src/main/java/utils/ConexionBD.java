package utils;

import jakarta.persistence.*;

import java.util.List;

public class ConexionBD {

    public ConexionBD() {
        String envType = System.getenv("ENVIRONMENT");
        System.out.println("AKHJSDhajksd: "+ envType);
        if (envType.equals("production")) {
            entityManagerFactory = Persistence.createEntityManagerFactory("production");
        }else {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(EntityTransaction transaction) {
        this.transaction = transaction;
    }

    public EntityManagerFactory entityManagerFactory;
    public EntityManager entityManager;
    public EntityTransaction transaction;


    public void endConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }
    public int persist(Object object) {
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

    public EntityManager getStaticEntityManager() {
        return this.entityManager;
    }

    public EntityTransaction getStaticTransaction() {
        return this.transaction;
    }
    public <T> List<T> getAll(Class<T> clazz) {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz);
        return query.getResultList();
    }
}
