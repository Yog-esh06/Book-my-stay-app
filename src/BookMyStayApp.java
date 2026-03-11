/**
 * BookMyStayApp.java
 * -----------------------------------
 * UC1: Welcome Message
 * UC2: Basic Room Types & Static Availability
 *
 * @author Yogesh R Mehta
 * @version 2.1
 */

// Abstract class for Room
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 1500.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 2500.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 5000.0); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// Main application class
public class BookMyStayApp {
    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App!");
        System.out.println("   Hotel Booking Management System");
        System.out.println("   Version: 2.1");
        System.out.println("=====================================");

        // UC2: Room Initialization & Availability
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        single.displayDetails();
        System.out.println("Availability: " + singleRoomAvailability);

        doubleR.displayDetails();
        System.out.println("Availability: " + doubleRoomAvailability);

        suite.displayDetails();
        System.out.println("Availability: " + suiteRoomAvailability);
    }
}
