// Version 5.1
// Use Case 5: Booking Request (First-Come-First-Served)

import java.util.*;

// ---------------- ABSTRACT ROOM CLASS ----------------
abstract class Room {

    private String roomType;
    private int beds;
    private int size;
    private double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : ₹" + price);
    }
}

// ---------------- ROOM TYPES ----------------

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 5000);
    }
}

// ---------------- RESERVATION CLASS ----------------

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

    public void displayReservation() {
        System.out.println("Guest : " + guestName + " | Requested Room : " + roomType);
    }
}

// ---------------- BOOKING QUEUE CLASS ----------------

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all requests in queue
    public void displayQueue() {

        System.out.println("\n===== Current Booking Request Queue =====");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }

        System.out.println("=========================================");
    }
}

// ---------------- MAIN CLASS ----------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Booking Request System =====");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Harrish", "Single Room");
        Reservation r3 = new Reservation("Chris", "Suite Room");
        Reservation r4 = new Reservation("varnikka", "Single Room");

        // Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Display queued requests
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored in FIFO order (First-Come-First-Served).");
        System.out.println("No inventory updates performed at this stage.");
    }
}