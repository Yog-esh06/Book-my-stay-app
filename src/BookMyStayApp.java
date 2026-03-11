/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC3: Centralized Room Inventory Management
 * Demonstrates use of HashMap for availability tracking.
 *
 * @author Yogesh R Mehta
 * @version 3.1
 */

import java.util.HashMap;

// Inventory manager class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register room type with availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " -> " + inventory.get(roomType));
        }
    }
}

// Main application class
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC3");
        System.out.println("   Version: 3.1");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display inventory
        inventory.displayInventory();

        // Update availability
        inventory.updateAvailability("Single Room", 4);
        System.out.println("\nAfter booking one Single Room:");
        inventory.displayInventory();
    }
}
