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
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }
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

// Inventory service with validation
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) throws InvalidBookingException {
        if (count < 0) {
            throw new InvalidBookingException("Room count cannot be negative.");
        }
        inventory.put(roomType, count);
    }

    public boolean isAvailable(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        return inventory.get(roomType) > 0;
    }

    public void decrementAvailability(String roomType) throws InvalidBookingException {
        if (!isAvailable(roomType)) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.key