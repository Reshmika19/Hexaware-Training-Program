package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> trackingHistory = new ArrayList<>();

        while (true) {
            System.out.println("\nTask 3 - Choose an option:");
            System.out.println("1. Update Tracking History of a Parcel");
            System.out.println("2. Find Nearest Available CourierC");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    updateTrackingHistory(scanner, trackingHistory);
                    break;
                case 2:
                    findNearestCourier(scanner);
                    break;
                case 3:
                    System.out.println("Exiting Task 3...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }

    // 1. Update Tracking History of a Parcel
    public static void updateTrackingHistory(Scanner scanner, ArrayList<String> trackingHistory) {
        while (true) {
            System.out.print("Enter tracking update (or type 'exit' to finish): ");
            String update = scanner.nextLine().trim();

            if (update.equalsIgnoreCase("exit")) {
                break;
            }
            trackingHistory.add(update);
        }

        System.out.println("\nTracking History:");
        for (String record : trackingHistory) {
            System.out.print("--> " + record);
        }
    }

    // 2. Find Nearest Available CourierC
    public static void findNearestCourier(Scanner scanner) {
        String[] couriers = {"CourierC A", "CourierC B", "CourierC C", "CourierC D"};
        int[] locations = {5, 10, 3, 8};

        System.out.print("Enter shipment location (distance in km): ");
        int shipmentLocation = scanner.nextInt();

        String nearestCourier = null;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < couriers.length; i++) {
            int distance = Math.abs(locations[i] - shipmentLocation);
            if (distance < minDistance) {
                minDistance = distance;
                nearestCourier = couriers[i];
            }
        }

        System.out.println("Nearest available courier: " + nearestCourier + " (Distance: " + minDistance + " km)");
    }
}
