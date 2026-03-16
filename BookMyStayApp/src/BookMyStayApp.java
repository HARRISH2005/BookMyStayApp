// Version 3.1
// Use Case 3: Centralized Room Inventory Management

import java.util.HashMap;
import java.util.Map;

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


// ---------------- INVENTORY CLASS ----------------

class RoomInventory {

    // Centralized HashMap
    private HashMap<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display entire inventory
    public void displayInventory() {

        System.out.println("\n===== Current Room Inventory =====");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available : " + entry.getValue());
        }

        System.out.println("==================================");
    }
}


// ---------------- MAIN CLASS ----------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Room Inventory System =====");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details
        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println();

        suite.displayRoomDetails();

        // Display inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Single Room Availability...\n");

        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}