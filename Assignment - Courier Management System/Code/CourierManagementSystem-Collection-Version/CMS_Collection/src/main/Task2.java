package main;

import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask 2 - Choose an option:");
            System.out.println("1. Display Orders for a Specific Customer");
            System.out.println("2. Track CourierC LocationC");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name to view orders: ");
                    String customer = scanner.nextLine();
                    displayOrders(customer);
                    break;

                case 2:
                    System.out.println("\nStarting courier tracking...");
                    trackCourier();
                    break;

                case 3:
                    System.out.println("Exiting the program. Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
    }
}

    // 1. Display Orders for a Specific Customer (Using For Loop)
    public static void displayOrders(String customer) {
        Map<String, List<String>> customerOrders = new HashMap<>();

        customerOrders.put("alice", Arrays.asList("Order 101", "Order 102", "Order 103"));
        customerOrders.put("bob", Arrays.asList("Order 201", "Order 202"));
        customerOrders.put("charlie", Arrays.asList("Order 301"));

        if (customerOrders.containsKey(customer.toLowerCase())) {
            System.out.println("Orders for " + customer + ":");
            List<String> orders = customerOrders.get(customer);

            for (int i = 0; i < orders.size(); i++) {
                System.out.println((i + 1) + ". " + orders.get(i));
            }
        } else {
            System.out.println("No orders found for " + customer + ".");
        }
    }

    // 2. Track CourierC LocationC Until Destination (Using While Loop)
    public static void trackCourier() {
        int currentLocation = 0;
        int destination = 10;
        Random random = new Random();

        while (currentLocation < destination) {
            int step = random.nextInt(3) + 1;
            currentLocation += step;

            if (currentLocation > destination) {
                currentLocation = destination;
            }

            System.out.println("CourierC moved to location: " + currentLocation);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("CourierC has reached the destination!");
    }
}
