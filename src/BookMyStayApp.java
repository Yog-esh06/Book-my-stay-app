/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC10: Booking Cancellation & Inventory Rollback
 * Demonstrates safe cancellation with rollback logic.
 *
 * @author Yogesh R Mehta
 * @version 10.1
 */

import java.util.*;

class Reservation {
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

// Booking service with allocation
class BookingService {
    private Map<String, Reservation> activeReservations = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public Reservation confirmBooking(String guestName, String roomType) {
        if (!inventory.isAvailable(roomType)) {
            System.out.println("Booking failed for " + guestName + " | Room: " + roomType);
            return null;
        }

        Reservation reservation = new Reservation(guestName, roomType);
        String roomId = UUID.randomUUID().toString();

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        inventory.decrementAvailability(roomType);
        activeReservations.put(reservation.getReservationId(), reservation);

        System.out.println("Booking confirmed: " + reservation + " | Room ID: " + roomId);
        return reservation;
    }

    public boolean isReservationActive(String reservationId) {
        return activeReservations.containsKey(reservationId);
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

// Cancellation service
class CancellationService {
    private BookingService bookingService;
    private RoomInventory inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingService bookingService, RoomInventory inventory) {
        this.bookingService = bookingService;
        this.inventory = inventory;
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation == null || !bookingService.isReservationActive(reservation.getReservationId())) {
            System.out.println("Cancellation failed: Reservation not found or already cancelled.");
            return;
        }

        // Rollback logic
        rollbackStack.push(reservation.getReservationId());
        inventory.incrementAvailability(reservation.getRoomType());

        System.out.println("Reservation cancelled: " + reservation);
        System.out.println("Rollback recorded for Reservation ID: " + reservation.getReservationId());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (recent cancellations): " + rollbackStack);
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC10");
        System.out.println("   Version: 10.1");
        System.out.println("=====================================");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        BookingService bookingService = new BookingService(inventory);
        CancellationService cancellationService = new CancellationService(bookingService, inventory);

        // Confirm bookings
        Reservation r1 = bookingService.confirmBooking("Alice", "Single Room");
        Reservation r2 = bookingService.confirmBooking("Bob", "Double Room");

        bookingService.displayAllocations();
        inventory.displayInventory();

        // Cancel a booking
        cancellationService.cancelReservation(r1);

        // Display rollback and inventory after cancellation
        cancellationService.displayRollbackStack();
        inventory.displayInventory();
    }
}