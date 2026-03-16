// Version 4.1
// Use Case 4: Room Search & Availability Check

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


// ---------------- INVENTORY CLASS ----------------

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);   // Now available
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


// ---------------- SEARCH SERVICE ----------------

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(List<Room> rooms) {

        System.out.println("\n===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                room.displayRoomDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("---------------------------");
            }
        }
    }
}


// ---------------- MAIN CLASS ----------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Room Search =====");

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Store rooms in a list
        List<Room> roomList = new ArrayList<>();

        roomList.add(single);
        roomList.add(doubleRoom);
        roomList.add(suite);

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize Search Service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(roomList);

        System.out.println("\nSearch Completed (Inventory Unchanged)");
    }
}