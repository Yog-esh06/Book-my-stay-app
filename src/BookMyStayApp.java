/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC11: Concurrent Booking Simulation (Thread Safety)
 * Demonstrates synchronized booking under multi-threaded conditions.
 *
 * @author Yogesh R Mehta
 * @version 11.1
 */

import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public synchronized void decrementAvailability(String roomType) {
        if (isAvailable(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public synchronized void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public synchronized void confirmBooking(String guestName, String roomType) {
        if (!inventory.isAvailable(roomType)) {
            System.out.println("Booking failed for " + guestName + " | Room: " + roomType);
            return;
        }
        String roomId = UUID.randomUUID().toString();
        inventory.decrementAvailability(roomType);
        System.out.println("Booking confirmed: Guest " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}

// Thread class to simulate concurrent booking
class BookingThread extends Thread {
    private BookingService bookingService;
    private String guestName;
    private String roomType;

    public BookingThread(BookingService bookingService, String guestName, String roomType) {
        this.bookingService = bookingService;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        bookingService.confirmBooking(guestName, roomType);
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC11");
        System.out.println("   Version: 11.1");
        System.out.println("================================