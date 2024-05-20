package utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class PersistDatabase {

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
        CriteriaBuilder cb = ConexionBD.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        List<T> resultList = ConexionBD.entityManager.createQuery(cq).getResultList();
        commitTransaction();
        return resultList;
    }

    private void createConection() {
        ConexionBD.transaction.begin();
    }

    private void rollbackTransaction() {
        if (ConexionBD.transaction.isActive()) {
            ConexionBD.transaction.rollback();
        }
    }

    private void endConnection() {
        ConexionBD.entityManager.close();
        ConexionBD.entityManagerFactory.close();
    }

    private <T> void persistObject(T entityClass) {
        ConexionBD.persist(entityClass);
    }

    private void commitTransaction() {
        ConexionBD.transaction.commit();
    }
}
