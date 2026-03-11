/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC7: Add-On Service Selection
 * Demonstrates attaching optional services to reservations.
 *
 * @author Yogesh R Mehta
 * @version 7.1
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

// Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addServiceToReservation(Reservation reservation, AddOnService service) {
        reservationServices.putIfAbsent(reservation.getReservationId(), new ArrayList<>());
        reservationServices.get(reservation.getReservationId()).add(service);
        System.out.println("Added service: " + service + " to " + reservation);
    }

    public void displayServicesForReservation(Reservation reservation) {
        List<AddOnService> services = reservationServices.getOrDefault(reservation.getReservationId(), new ArrayList<>());
        System.out.println("\nServices for " + reservation + ":");
        double totalCost = 0;
        for (AddOnService s : services) {
            System.out.println(" - " + s);
            totalCost += s.getCost();
        }
        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

// Main application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   Book My Stay App - UC7");
        System.out.println("   Version: 7.1");
        System.out.println("=====================================");

        // Create reservation
        Reservation reservation = new Reservation("Alice", "Suite Room");

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500.0);
        AddOnService spa = new AddOnService("Spa Access", 1500.0);
        AddOnService pickup = new AddOnService("Airport Pickup", 800.0);

        // Manage services
        AddOnServiceManager manager = new AddOnServiceManager();
        manager.addServiceToReservation(reservation, breakfast);
        manager.addServiceToReservation(reservation, spa);
        manager.addServiceToReservation(reservation, pickup);

        // Display services
        manager.displayServicesForReservation(reservation);
    }
}