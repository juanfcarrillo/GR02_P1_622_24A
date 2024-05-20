package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersistDatabaseTest {

    @Mock
    private ConexionBD mockConexionBD;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityManagerFactory mockEntityManagerFactory;

    @Mock
    private CriteriaBuilder mockCriteriaBuilder;

    @Mock
    private CriteriaQuery<Object> mockCriteriaQuery;

    @Mock
    private EntityTransaction mockEntityTransaction;

    @InjectMocks
    private PersistDatabase persistDatabase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockConexionBD.getEntityManager()).thenReturn(mockEntityManager);
        when(mockConexionBD.getEntityManagerFactory()).thenReturn(mockEntityManagerFactory);
        when(mockConexionBD.getEntityManager()).thenReturn(mockEntityManager);
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockConexionBD.getTransaction()).thenReturn(mockEntityTransaction);
        when(mockCriteriaBuilder.createQuery(Object.class)).thenReturn(mockCriteriaQuery);
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Object.class)).thenReturn(mockCriteriaQuery);
    }

    @Test
    public void testPersist() {
        Object testObject = new Object();

        when(mockConexionBD.getTransaction().isActive()).thenReturn(true);
        when(mockConexionBD.persist(testObject)).thenReturn(0);

        int result = persistDatabase.persist(testObject);

        assertEquals(0, result);
        verify(mockConexionBD, times(1)).persist(testObject);
    }

    @Test
    public void testFailPersist() {
        Object testObject = new Object();

        when(mockConexionBD.getTransaction().isActive()).thenReturn(true);
        when(mockConexionBD.persist(testObject)).thenThrow(new RuntimeException("Error"));

        int result = persistDatabase.persist(testObject);

        assertEquals(1, result);
    }
}