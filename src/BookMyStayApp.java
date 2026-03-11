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
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public synchronized void addRoomType(String roomType, int count) {
        availability.put(roomType, count);
    }

    public synchronized int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    // Synchronized method ensures thread-safe updates
    public synchronized boolean allocateRoom(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availability.put(roomType, current - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
    }
}

// BookingService class handling allocation
class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processRequest(Reservation reservation) {
        synchronized (inventory) { // Critical section
            if (inventory.allocateRoom(reservation.getRoomType())) {
                System.out.println("Reservation confirmed for " + reservation.getGuestName() +
                                   " | Room Type: " + reservation.getRoomType());
            } else {
                System.out.println("Reservation failed for " + reservation.getGuestName() +
                                   " | Room Type: " + reservation.getRoomType() +
                                   " | Reason: No availability");
            }
        }
    }
}

// Runnable task for concurrent booking
class BookingTask implements Runnable {
    private BookingService service;
    private Reservation reservation;

    public BookingTask(BookingService service, Reservation reservation) {
        this.service = service;
        this.reservation = reservation;
    }

    @Override
    public void run() {
        service.processRequest(reservation);
    }
}

// Application entry point
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay!");
        System.out.println("Application: Hotel Booking Management System");
        System.out.println("Version: 11.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Initialize booking service
        BookingService service = new BookingService(inventory);

        // Simulate multiple guests booking concurrently
        Thread t1 = new Thread(new BookingTask(service, new Reservation("Alice", "Single Room")));
        Thread t2 = new Thread(new BookingTask(service, new Reservation("Bob", "Single Room")));
        Thread t3 = new Thread(new BookingTask(service, new Reservation("Charlie", "Single Room"))); // Should fail
        Thread t4 = new Thread(new BookingTask(service, new Reservation("Diana", "Double Room")));
        Thread t5 = new Thread(new BookingTask(service, new Reservation("Ethan", "Double Room"))); // Should fail

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final inventory state
        inventory.displayInventory();
    }
}
