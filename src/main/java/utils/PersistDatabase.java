package utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class PersistDatabase {
    private final ConexionBD conexionBD;

    public PersistDatabase() {
        this.conexionBD = new ConexionBD();
    }

    public PersistDatabase(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public int persist(Object object) {
        int transactionResult;

        try {
            persistObject(object);
            transactionResult = 0;
        } catch (Exception e) {
            rollbackTransaction();
            transactionResult = 1;
        } finally {
            endConnection();
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
        conexionBD.getTransaction().commit();
    }
}
