package utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import jakarta.persistence.*;

public class PersistDatabase {
    private final ConexionBD conexionBD;

    public PersistDatabase() {
        this.conexionBD = new ConexionBD();
    }

    public PersistDatabase(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public EntityManager getEntityManager() {
        return conexionBD.getEntityManager();
    }

    public int persist(Object object) {
        int transactionResult;

        try {
            persistObject(object);
            transactionResult = 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            rollbackTransaction();
            transactionResult = 1;
        }
        return transactionResult;
    }

    public <T> List<T> getAll(Class<T> entityClass) {
        createConection();
        CriteriaBuilder cb = conexionBD.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        List<T> resultList = conexionBD.getEntityManager().createQuery(cq).getResultList();
        commitTransaction();
        return resultList;
    }

    private void createConection() {

        conexionBD.getTransaction().begin();
    }

    private void rollbackTransaction() {
        if (conexionBD.getTransaction().isActive()) {
            conexionBD.getTransaction().rollback();
        }
    }

    private void endConnection() {
        conexionBD.getEntityManager().close();
        conexionBD.getEntityManagerFactory().close();
    }

    private <T> void persistObject(T entityClass) {
        createConection();
        conexionBD.persist(entityClass);
        commitTransaction();
    }

    private void commitTransaction() {
        if (!conexionBD.getTransaction().isActive()) {
            conexionBD.getTransaction().begin();
        }
        conexionBD.getTransaction().commit();
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return conexionBD.getEntityManager().find(entityClass, primaryKey);
    }

    public <T> void create(T entity) {
        try {
            createConection();
            conexionBD.getEntityManager().persist(entity);
            commitTransaction();
        }catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }

    public <T> void update(T entity) {
        try {
            createConection();
            conexionBD.getEntityManager().merge(entity);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }

    public <T> TypedQuery<T> createQuery(String qlString, Class<T> entityClass) {
        createConection();
        TypedQuery result = conexionBD.getEntityManager().createQuery(qlString, entityClass);
        commitTransaction();
        return result;
    }
}
