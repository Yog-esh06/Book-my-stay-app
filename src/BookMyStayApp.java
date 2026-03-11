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

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationID;
    private String guestName;
    private String roomType;

    public Reservation(String reservationID, String guestName, String roomType) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// RoomInventory class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availability.put(roomType, current - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
    }
}

// PersistenceService class
class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void saveState(RoomInventory inventory, List<Reservation> reservations) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(reservations);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state
    @SuppressWarnings("unchecked")
    public static Object[] loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> reservations = (List<Reservation>) ois.readObject();
            System.out.println("\nSystem state loaded successfully.");
            return new Object[]{inventory, reservations};
        } catch (FileNotFoundException e) {
            System.out.println("\nNo previous state found. Starting fresh.");
            return new Object[]{new RoomInventory(), new ArrayList<Reservation>()};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            return new Object[]{new RoomInventory(), new ArrayList<Reservation>()};
        }
    }
}

// Application entry point
public class bookmystayapp{
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay!");
        System.out.println("Application: Hotel Booking Management System");
        System.out.println("Version: 12.0\n");

        // Load previous state
        Object[] state = PersistenceService.loadState();
        RoomInventory inventory = (RoomInventory) state[0];
        List<Reservation> reservations = (List<Reservation>) state[1];

        // If inventory is empty, initialize fresh
        if (inventory.getAvailability("Single Room") == 0 &&
                inventory.getAvailability("Double Room") == 0 &&
                inventory.getAvailability("Suite Room") == 0) {
            inventory.addRoomType("Single Room", 2);
            inventory.addRoomType("Double Room", 1);
            inventory.addRoomType("Suite Room", 1);
        }

        // Simulate new reservations
        Reservation r1 = new Reservation("R001", "Alice", "Single Room");
        Reservation r2 = new Reservation("R002", "Bob", "Suite Room");

        if (inventory.allocateRoom(r1.getRoomType())) reservations.add(r1);
        if (inventory.allocateRoom(r2.getRoomType())) reservations.add(r2);

        // Display reservations
        System.out.println("\n=== Current Reservations ===");
        for (Reservation r : reservations) {
            r.displayReservation();
        }

        // Display inventory
        inventory.displayInventory();

        // Save state before shutdown
        PersistenceService.saveState(inventory, reservations);
    }
}
