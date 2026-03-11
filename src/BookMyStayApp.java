/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC9: Error Handling & Validation
 * Demonstrates input validation and custom exceptions.
 *
 * @author Yogesh R Mehta
 * @version 9.1
 */

import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// RoomInventory class with validation
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Room count cannot be negative.");
        }
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, -1);
    }

    public boolean decrementAvailability(String roomType) throws InvalidBookingException {
        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        int current = availability.get(roomType);
        if (current <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
        availability.put(roomType, current - 1);
        return true;
    }

    public void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
    }
}

// BookingService class with validation logic
class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processRequest(Reservation reservation) {
        try {
            inventory.decrementAvailability(reservation.getRoomType());
            System.out.println("Reservation confirmed for " + reservation.getGuestName() +
                    " | Room Type: " + reservation.getRoomType());
        } catch (InvalidBookingException e) {
            System.out.println("Reservation failed for " + reservation.getGuestName() +
                    " | Reason: " + e.getMessage());
        }
    }
}

// Application entry point
public class bookmystayapp{
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay!");
        System.out.println("Application: Hotel Booking Management System");
        System.out.println("Version: 9.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1);
        inventory.addRoomType("Double Room", 0); // No availability
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking service
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.processRequest(new Reservation("Alice", "Single Room"));

        // Invalid booking: no availability
        service.processRequest(new Reservation("Bob", "Double Room"));

        // Invalid booking: unknown room type
        service.processRequest(new Reservation("Charlie", "Penthouse"));

        // Display final inventory state
        inventory.displayInventory();
    }
}
