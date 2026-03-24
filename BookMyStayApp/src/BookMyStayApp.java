import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// Wrapper class for full system state
class SystemState implements Serializable {
    List<Reservation> reservations;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Corrupted data. Starting with safe defaults.");
        }
        return null;
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        List<Reservation> reservations;
        Map<String, Integer> inventory;

        // Load previous state
        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            reservations = loadedState.reservations;
            inventory = loadedState.inventory;
        } else {
            // Default state
            reservations = new ArrayList<>();
            inventory = new HashMap<>();
            inventory.put("STANDARD", 2);
            inventory.put("DELUXE", 1);
        }

        Scanner sc = new Scanner(System.in);

        // Add a new booking
        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Room Type: ");
        String roomType = sc.nextLine().toUpperCase();

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            reservations.add(new Reservation(id, name, roomType));
            inventory.put(roomType, available - 1);
            System.out.println("Booking successful.");
        } else {
            System.out.println("Booking failed. No rooms available.");
        }

        // Display current state
        System.out.println("\n=== Current Bookings ===");
        for (Reservation r : reservations) {
            r.display();
        }

        System.out.println("\n=== Inventory ===");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }

        // Save state before exit
        SystemState currentState = new SystemState(reservations, inventory);
        PersistenceService.save(currentState);

        sc.close();
    }
}