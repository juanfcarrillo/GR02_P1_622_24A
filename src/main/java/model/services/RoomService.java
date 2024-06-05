package model.services;

import model.entity.Room;
import utils.PersistDatabase;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de habitaciones que maneja las operaciones relacionadas con las habitaciones.
 */
public class RoomService {
    // Instancia de PersistDatabase para manejar la persistencia de datos.
    private final PersistDatabase persistDatabase;

    /**
     * Constructor sin parámetros que inicializa una nueva instancia de PersistDatabase.
     */
    public RoomService() {
        this.persistDatabase = new PersistDatabase();
    }

    /**
     * Constructor que acepta una instancia de PersistDatabase.
     * @param persistDatabase Instancia de PersistDatabase a utilizar.
     */
    public RoomService(PersistDatabase persistDatabase) {
        this.persistDatabase = persistDatabase;
    }

    /**
     * Registra una nueva habitación en la base de datos.
     * @param room La habitación a registrar.
     */
    public void registerRoom(Room room) {
        int result = persistDatabase.persist(room);
        if (result == 0) {
            System.out.println("Room registered successfully");
        } else {
            System.out.println("Error registering the room");
        }
    }

    /**
     * Obtiene una habitación por su número.
     * @param roomNumber El número de la habitación a buscar.
     * @return La habitación encontrada o null si no se encuentra.
     */
    public Room getRoomByNumber(int roomNumber) {
        List<Room> allRooms = getAllRooms();
        return allRooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene una lista de todas las habitaciones.
     * @return Lista de todas las habitaciones.
     */
    public List<Room> getAllRooms() {
        return persistDatabase.getAll(Room.class);
    }

    /**
     * Obtiene una lista de habitaciones disponibles con la capacidad requerida.
     * @param requiredCapacity La capacidad requerida de la habitación.
     * @return Lista de habitaciones disponibles que cumplen con la capacidad requerida.
     */
    public List<Room> getAvailableRooms(int requiredCapacity) {
        List<Room> allRooms = getAllRooms();
        return filterAvailableRooms(allRooms, requiredCapacity);
    }

    /**
     * Filtra una lista de habitaciones para obtener solo aquellas que cumplen con la capacidad requerida.
     * @param allRooms Lista de todas las habitaciones.
     * @param requiredCapacity La capacidad requerida.
     * @return Lista de habitaciones que cumplen con la capacidad requerida.
     */
    public List<Room> filterAvailableRooms(List<Room> allRooms, int requiredCapacity) {
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= requiredCapacity)
                .collect(Collectors.toList());
    }
}
