import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator Class
class InvalidBookingValidator {

    private static final Set<String> VALID_ROOM_TYPES = new HashSet<>(
            Arrays.asList("STANDARD", "DELUXE", "SUITE")
    );

    // Validate booking input
    public static void validate(String roomType, int nights, int availableRooms) throws InvalidBookingException {

        // Validate room type
        if (!VALID_ROOM_TYPES.contains(roomType.toUpperCase())) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate nights
        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0.");
        }

        // Validate inventory
        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for booking.");
        }
    }
}

// Booking System
class BookingSystem {
    private Map<String, Integer> roomInventory;

    public BookingSystem() {
        roomInventory = new HashMap<>();
        roomInventory.put("STANDARD", 2);
        roomInventory.put("DELUXE", 2);
        roomInventory.put("SUITE", 1);
    }

    public void bookRoom(String roomType, int nights) throws InvalidBookingException {
        roomType = roomType.toUpperCase();

        int availableRooms = roomInventory.getOrDefault(roomType, 0);

        // Validate before modifying state (FAIL-FAST)
        InvalidBookingValidator.validate(roomType, nights, availableRooms);

        // Safe state update
        roomInventory.put(roomType, availableRooms - 1);

        System.out.println("Booking confirmed for " + roomType + " for " + nights + " nights.");
        System.out.println("Remaining " + roomType + " rooms: " + (availableRooms - 1));
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        try {
            System.out.print("Enter Room Type (STANDARD/DELUXE/SUITE): ");
            String roomType = sc.nextLine();

            System.out.print("Enter Number of Nights: ");
            int nights = sc.nextInt();

            system.bookRoom(roomType, nights);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        } catch (Exception e) {
            // Catch unexpected errors
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}