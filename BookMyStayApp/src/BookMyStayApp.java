import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System (THREAD-SAFE)
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("STANDARD", 2);
        inventory.put("DELUXE", 1);
    }

    // Add request (synchronized to protect queue)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request added: " + request.guestName + " -> " + request.roomType);
    }

    // Process booking (CRITICAL SECTION)
    public void processBooking() {
        while (true) {
            BookingRequest request;

            // synchronized block for safe queue access
            synchronized (this) {
                if (bookingQueue.isEmpty()) {
                    return;
                }
                request = bookingQueue.poll();
            }

            // CRITICAL SECTION → inventory update
            synchronized (this) {
                int available = inventory.getOrDefault(request.roomType, 0);

                if (available > 0) {
                    inventory.put(request.roomType, available - 1);
                    System.out.println(Thread.currentThread().getName() +
                            " booked " + request.roomType +
                            " for " + request.guestName +
                            " | Remaining: " + (available - 1));
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " FAILED booking for " + request.guestName +
                            " (No " + request.roomType + " rooms left)");
                }
            }

            // Simulate processing delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Worker Thread
class BookingWorker extends Thread {
    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        processor.processBooking();
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulate multiple guest requests
        processor.addRequest(new BookingRequest("Aman", "STANDARD"));
        processor.addRequest(new BookingRequest("Rahul", "STANDARD"));
        processor.addRequest(new BookingRequest("Simran", "STANDARD")); // should fail
        processor.addRequest(new BookingRequest("Neha", "DELUXE"));
        processor.addRequest(new BookingRequest("Karan", "DELUXE")); // should fail

        // Multiple threads (simulating concurrent users)
        BookingWorker t1 = new BookingWorker(processor);
        BookingWorker t2 = new BookingWorker(processor);
        BookingWorker t3 = new BookingWorker(processor);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}