// Version 6.1
// Use Case 6: Reservation Confirmation & Room Allocation

import java.util.*;

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
}


// ---------------- INVENTORY SERVICE ----------------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {

        int count = inventory.get(roomType);

        if (count > 0) {
            inventory.put(roomType, count - 1);
        }
    }

    public void displayInventory() {

        System.out.println("\n===== Current Inventory =====");

        for (Map.Entry<String,Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available : " + entry.getValue());
        }

        System.out.println("=============================");
    }
}


// ---------------- BOOKING REQUEST QUEUE ----------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll(); // FIFO
    }

    public boolean hasRequests() {
        return !requestQueue.isEmpty();
    }
}


// ---------------- ROOM ALLOCATION SERVICE ----------------
class BookingService {

    private RoomInventory inventory;

    // Track all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Track rooms allocated per type
    private HashMap<String, Set<String>> allocatedRoomsByType;

    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();

        allocatedRoomsByType = new HashMap<>();
    }

    public void processRequests(BookingRequestQueue queue) {

        System.out.println("\n===== Processing Booking Requests =====");

        while(queue.hasRequests()) {

            Reservation reservation = queue.getNextRequest();

            String guest = reservation.getGuestName();
            String roomType = reservation.getRoomType();

            int available = inventory.getAvailability(roomType);

            if(available > 0) {

                // Generate unique room ID
                String roomId = roomType.replace(" ", "") + "-" + roomCounter++;

                // Ensure uniqueness
                if(!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    allocatedRoomsByType
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory
                    inventory.decrementRoom(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest : " + guest);
                    System.out.println("Room Type : " + roomType);
                    System.out.println("Assigned Room ID : " + roomId);
                    System.out.println("----------------------------");
                }

            } else {

                System.out.println("Reservation Failed (No rooms available)");
                System.out.println("Guest : " + guest);
                System.out.println("Requested Room : " + roomType);
                System.out.println("----------------------------");
            }
        }
    }

    public void displayAllocatedRooms() {

        System.out.println("\n===== Allocated Rooms =====");

        for(Map.Entry<String,Set<String>> entry : allocatedRoomsByType.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("===========================");
    }
}


// ---------------- MAIN CLASS ----------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== BookMyStay Reservation Allocation =====");

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize Request Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guest booking requests
        queue.addRequest(new Reservation("Alice","Single Room"));
        queue.addRequest(new Reservation("Bob","Double Room"));
        queue.addRequest(new Reservation("Charlie","Suite Room"));
        queue.addRequest(new Reservation("David","Single Room"));
        queue.addRequest(new Reservation("Eva","Suite Room"));

        // Booking Service
        BookingService service = new BookingService(inventory);

        // Process requests
        service.processRequests(queue);

        // Show allocated rooms
        service.displayAllocatedRooms();

        // Show updated inventory
        inventory.displayInventory();
    }
}