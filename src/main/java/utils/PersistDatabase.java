package utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class PersistDatabase {
    public PersistDatabase() {
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
            ConexionBD.endConnection();
        }

        return transactionResult;
    }

    private void createConection() {
        ConexionBD.transaction.begin();
    }

    private void rollbackTransaction() {
        if (ConexionBD.transaction.isActive()) {
            ConexionBD.transaction.rollback();
        }
    }

    private <T> void persistObject(T entityClass) {
        createConection();
        ConexionBD.entityManager.persist(entityClass);
        commitTransaction();
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


    private void commitTransaction() {
        ConexionBD.transaction.commit();
    }
}
