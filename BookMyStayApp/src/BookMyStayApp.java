import java.util.*;

// Service class representing an add-on
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Manager class handling mapping of reservation -> services
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add services to a reservation
    public void addServices(String reservationId, List<Service> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service service : getServices(reservationId)) {
            total += service.getCost();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Services for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("- " + s.getName() + " : ₹" + s.getCost());
        }
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("Enter number of services to add: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        List<Service> selectedServices = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Service Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Service Cost: ");
            double cost = sc.nextDouble();
            sc.nextLine();

            selectedServices.add(new Service(name, cost));
        }

        // Add services to reservation
        manager.addServices(reservationId, selectedServices);

        // Display services
        manager.displayServices(reservationId);

        // Show total cost
        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost);

        sc.close();
    }
}