/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC12: Data Persistence & System Recovery
 * Demonstrates saving and restoring system state using serialization.
 *
 * @author Yogesh R Mehta
 * @version 12.1
 */

import java.io.*;
import java.util.*;

// Reservation class
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.reservationId = UUID.randomUUID().toString();
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation [ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType + "]";
    }
}

// Inventory class
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementAvailability(String roomType) {
        if (isAvailable(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public void incrementAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Persistence Service
class PersistenceService {
    public static void saveState(Object state, String filename