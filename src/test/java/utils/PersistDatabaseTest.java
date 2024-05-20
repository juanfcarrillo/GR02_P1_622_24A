package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PersistDatabaseTest {

    @Test
    public void should_return_0_if_succeeded() {
        MockitoAnnotations.openMocks(this);
        Object testObject = new Object();

        try (MockedStatic<ConexionBD> mockConexionBD = Mockito.mockStatic(ConexionBD.class)) {
            mockConexionBD.when(() -> ConexionBD.persist(testObject)).thenReturn(0);

            PersistDatabase persistDatabase = new PersistDatabase();
            int result = persistDatabase.persist(testObject);

            assertEquals(0, result);
        }
    }

    @Test
    void should_return_1_if_failed() {
        Object testObject = new Object();

        try (MockedStatic<ConexionBD> mockConexionBD = Mockito.mockStatic(ConexionBD.class)) {
            mockConexionBD.when(() -> ConexionBD.persist(testObject)).thenThrow(new RuntimeException("Error"));

            PersistDatabase persistDatabase = new PersistDatabase();
            int result = persistDatabase.persist(testObject);

            assertEquals(1, result);
        }
    }
}