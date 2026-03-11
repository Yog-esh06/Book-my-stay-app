/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC4: Room Search & Availability Check
 * Demonstrates read-only search operations on inventory.
 *
 * @author YourName
 * @version 4.1
 */

import java.util.HashMap;
import java.util.Map;

// Room domain model
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public int getBeds() { return beds; }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 1500.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 2500.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 5000.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// Inventory manager
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory); // defensive copy
    }
}

// Search service (read-only)
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            int availability = inventory.getAvailability(room.getRoomType());
            if (availability > 0) {
                room.displayDetails();
                System.out.println("Availability: " + availability);
            }
        }
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC4");
        System.out.println("   Version: 4.1");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // unavailable
        inventory.addRoomType("Suite Room", 2);

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms(rooms);
    }
}
