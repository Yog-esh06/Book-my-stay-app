/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC5: Booking Request (First-Come-First-Served)
 * Demonstrates fair request handling using a Queue.
 *
 * @author Yogesh R Mehta
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class
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

// Booking Request Queue
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added: " + reservation);
    }

    // Display queue
    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO):");
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC5");
        System.out.println("   Version: 5.1");
        System.out.println("=====================================");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue
        bookingQueue.displayQueue();
    }
}