import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        isActive = false;
    }
}

// Booking History
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public Reservation findReservation(String id) {
        for (Reservation r : history) {
            if (r.getReservationId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public void displayAll() {
        System.out.println("\n=== Booking History ===");
        for (Reservation r : history) {
            System.out.println("ID: " + r.getReservationId() +
                    " | Room: " + r.getRoomType() +
                    " | RoomID: " + r.getRoomId() +
                    " | Status: " + (r.isActive() ? "ACTIVE" : "CANCELLED"));
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Integer> inventory;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Integer> inventory) {
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationId, BookingHistory history) {

        Reservation r = history.findReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Step 1: Push room ID to rollback stack (LIFO tracking)
        rollbackStack.push(r.getRoomId());

        // Step 2: Restore inventory
        String roomType = r.getRoomType();
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);

        // Step 3: Mark reservation as cancelled
        r.cancel();

        System.out.println("Cancellation successful for Reservation ID: " + reservationId);
        System.out.println("Restored Room: " + rollbackStack.peek());
        System.out.println("Updated " + roomType + " Inventory: " + inventory.get(roomType));
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Inventory Setup
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("STANDARD", 1);
        inventory.put("DELUXE", 1);

        BookingHistory history = new BookingHistory();

        // Pre-confirmed bookings (simulating previous use cases)
        Reservation r1 = new Reservation("R101", "STANDARD", "S1");
        Reservation r2 = new Reservation("R102", "DELUXE", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        CancellationService service = new CancellationService(inventory);

        // Show current bookings
        history.displayAll();

        // Cancellation input
        System.out.print("\nEnter Reservation ID to cancel: ");
        String id = sc.nextLine();

        service.cancelReservation(id, history);

        // Final state
        history.displayAll();

        sc.close();
    }
}