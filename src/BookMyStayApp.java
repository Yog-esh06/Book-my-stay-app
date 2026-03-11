/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC6: Reservation Confirmation & Room Allocation
 * Demonstrates safe allocation with uniqueness enforcement.
 *
 * @author Yogesh R Mehta
 * @version 6.1
 */

import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room: " + roomType + "]";
    }
}

// Inventory service
class RoomInventory {
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

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking service
class BookingService {
    private Queue<Reservation> requestQueue;
    private Map<String, Set<String>> allocatedRooms;
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.requestQueue = new LinkedList<>();
        this.allocatedRooms = new HashMap<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request queued: " + reservation);
    }

    // Process next request
    public void processNextRequest() {
        if (requestQueue.isEmpty()) {
            System.out.println("No requests to process.");
            return;
        }

        Reservation reservation = requestQueue.poll();
        String roomType = reservation.getRoomType();

        if (inventory.isAvailable(roomType)) {
            // Generate unique room ID
            String roomId = UUID.randomUUID().toString();

            // Ensure uniqueness
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);

            // Update inventory
            inventory.decrementAvailability(roomType);

            System.out.println("Reservation confirmed for " + reservation.getGuestName() +
                    " | Room Type: " + roomType +
                    " | Room ID: " + roomId);
        } else {
            System.out.println("Reservation failed for " + reservation.getGuestName() +
                    " | Room Type: " + roomType + " (Not Available)");
        }
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC6");
        System.out.println("   Version: 6.1");
        System.out.println("=====================================");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        BookingService bookingService = new BookingService(inventory);

        // Queue requests
        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Single Room"));
        bookingService.addRequest(new Reservation("David", "Suite Room")); // not available

        // Process requests
        bookingService.processNextRequest();
        bookingService.processNextRequest();
        bookingService.processNextRequest();
        bookingService.processNextRequest();

        // Display allocations and inventory
        bookingService.displayAllocations();
        System.out.println();
        inventory.displayInventory();
    }
}