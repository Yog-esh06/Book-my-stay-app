/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC8: Booking History & Reporting
 * Demonstrates historical tracking of reservations.
 *
 * @author Yogesh R Mehta
 * @version 8.1
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

// Booking History
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation);
    }

    public List<Reservation> getHistory() {
        return Collections.unmodifiableList(history);
    }
}

// Reporting Service
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void generateReport() {
        System.out.println("\n=== Booking Report ===");
        List<Reservation> reservations = history.getHistory();
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println(r);
        }
        System.out.println("Total Bookings: " + reservations.size());
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC8");
        System.out.println("   Version: 8.1");
        System.out.println("=====================================");

        BookingHistory history = new BookingHistory();

        // Confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Double Room");

        // Add to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService(history);
        reportService.generateReport();
    }
}