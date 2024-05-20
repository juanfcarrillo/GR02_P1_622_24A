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

/**
 * Clase de prueba para la clase PersistDatabase.
 * Utiliza Mockito para simular objetos y comportamientos en las pruebas.
 */
public class PersistDatabaseTest {

    // Mocks para las dependencias de PersistDatabase
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

    // Objeto de la clase a probar
    @InjectMocks
    private PersistDatabase persistDatabase;

    /**
     * Configura los comportamientos de los mocks antes de cada prueba.
     */
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

    /**
     * Prueba el método persist() cuando la persistencia tiene éxito.
     */
    @Test
    public void testPersist() {
        // Objeto de prueba
        Object testObject = new Object();

        // Configura el comportamiento esperado del mock
        when(mockConexionBD.getTransaction().isActive()).thenReturn(true);
        when(mockConexionBD.persist(testObject)).thenReturn(0);

        // Ejecuta el método a probar
        int result = persistDatabase.persist(testObject);

        // Verifica el resultado esperado y las interacciones con los mocks
        assertEquals(0, result);
        verify(mockConexionBD, times(1)).persist(testObject);
    }

    /**
     * Prueba el método persist() cuando la persistencia falla.
     */
    @Test
    public void testFailPersist() {
        // Objeto de prueba
        Object testObject = new Object();

        // Configura el comportamiento esperado del mock
        when(mockConexionBD.getTransaction().isActive()).thenReturn(true);
        when(mockConexionBD.persist(testObject)).thenThrow(new RuntimeException("Error"));

        // Ejecuta el método a probar
        int result = persistDatabase.persist(testObject);

        // Verifica el resultado esperado
        assertEquals(1, result);
    }
}
