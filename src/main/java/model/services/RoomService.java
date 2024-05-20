package model.services;

import model.entity.Room;
import utils.PersistDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    private final PersistDatabase persistDatabase;

    public RoomService() {
        this.persistDatabase = new PersistDatabase();
    }

    public RoomService(PersistDatabase persistDatabase) {
        this.persistDatabase = persistDatabase;
    }

    public void registerRoom(Room room) {
        int result = persistDatabase.persist(room);
        if (result == 0) {
            System.out.println("Room registered successfully");
        } else {
            System.out.println("Error registering the room");
        }
    }

    public Room getRoomByNumber(int roomNumber) {
        List<Room> allRooms = getAllRooms();
        return allRooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    public List<Room> getAllRooms() {
        return persistDatabase.getAll(Room.class);
    }

    public List<Room> getAvailableRooms(int requiredCapacity) {
        List<Room> allRooms = getAllRooms();
        return filterAvailableRooms(allRooms, requiredCapacity);
    }

    public List<Room> filterAvailableRooms(List<Room> allRooms, int requiredCapacity) {
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= requiredCapacity)
                .collect(Collectors.toList());
    }
}
