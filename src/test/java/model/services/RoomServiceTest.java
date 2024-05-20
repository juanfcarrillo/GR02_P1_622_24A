package model.services;

import model.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.PersistDatabase;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Mock
    private PersistDatabase mockPersistDatabase;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterRoom() {
        Room mockRoom = Mockito.mock(Room.class);

        when(mockPersistDatabase.persist(mockRoom)).thenReturn(0);

        roomService.registerRoom(mockRoom);
    }

    @Test
    public void testGetAllRooms() {
        Room mockRoom1 = Mockito.mock(Room.class);
        Room mockRoom2 = Mockito.mock(Room.class);

        List<Room> allRooms = Arrays.asList(mockRoom1, mockRoom2);

        when(mockPersistDatabase.getAll(Room.class)).thenReturn(allRooms);

        List<Room> retrievedRooms = roomService.getAllRooms();

        assertEquals(2, retrievedRooms.size());
        assertTrue(retrievedRooms.contains(mockRoom1));
        assertTrue(retrievedRooms.contains(mockRoom2));
    }

    @Test
    public void testGetAvailableRooms() {
        Room mockRoom1 = Mockito.mock(Room.class);
        Room mockRoom2 = Mockito.mock(Room.class);

        when(mockRoom1.getCapacity()).thenReturn(5);
        when(mockRoom2.getCapacity()).thenReturn(3);

        List<Room> allRooms = Arrays.asList(mockRoom1, mockRoom2);

        when(mockPersistDatabase.getAll(Room.class)).thenReturn(allRooms);

        List<Room> availableRooms = roomService.getAvailableRooms(4);

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(mockRoom1));
    }

    @Test
    public void testFilterAvailableRooms() {
        Room mockRoom1 = Mockito.mock(Room.class);
        Room mockRoom2 = Mockito.mock(Room.class);

        when(mockRoom1.getCapacity()).thenReturn(5);
        when(mockRoom2.getCapacity()).thenReturn(3);

        List<Room> allRooms = Arrays.asList(mockRoom1, mockRoom2);

        List<Room> availableRooms = roomService.filterAvailableRooms(allRooms, 4);

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(mockRoom1));
    }
}