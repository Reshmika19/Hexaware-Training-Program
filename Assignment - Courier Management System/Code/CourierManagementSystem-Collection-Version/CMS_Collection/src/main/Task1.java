package main;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask 1 - Choose an option:");
            System.out.println("1. Check Order Status");
            System.out.println("2. Categorize Parcel by Weight");
            System.out.println("3. UserC Authentication");
            System.out.println("4. EmployeeC Authentication");
            System.out.println("5. Assign CourierC to Shipment");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    checkOrderStatus(scanner);
                    break;
                case 2:
                    categorizeParcelWeight(scanner);
                    break;
                case 3:
                    userAuthentication(scanner);
                    break;
                case 4:
                    employeeAuthentication(scanner);
                    break;
                case 5:
                    assignCourier(scanner);
                    break;
                case 6:
                    System.out.println("Exiting Task 1...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }

    // 1. Check Order Status
    public static void checkOrderStatus(Scanner scanner) {
        System.out.print("Enter order status (Processing/Delivered/Cancelled): ");
        String status = scanner.nextLine().trim();

        if (status.equalsIgnoreCase("Processing")) {
            System.out.println("Your order is still being processed.");
        } else if (status.equalsIgnoreCase("Delivered")) {
            System.out.println("Your order has been delivered! ");
        } else if (status.equalsIgnoreCase("Cancelled")) {
            System.out.println("Your order has been cancelled.");
        } else {
            System.out.println("Invalid status entered. Please enter a valid order status.");
        }
    }

    // 2. Categorize Parcel Weight
    public static void categorizeParcelWeight(Scanner scanner) {
        System.out.print("Enter parcel weight in kg: ");
        double weight = scanner.nextDouble();

        String category;
        if (weight < 1) {
            category = "Light";
        } else if (weight <= 5) {
            category = "Medium";
        } else {
            category = "Heavy";
        }

        System.out.println("The parcel is categorized as: " + category);
    }

    // 3. UserC Authentication
    public static void userAuthentication(Scanner scanner) {
        String correctUsername = "admin";
        String correctPassword = "password123";

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    // 4. EmployeeC Authentication
    public static void employeeAuthentication(Scanner scanner) {
        String[] employeeIDs = {"EMP001", "EMP002", "EMP003"};
        String[] employeePasswords = {"pass1", "pass2", "pass3"};

        System.out.print("Enter EmployeeC ID: ");
        String empID = scanner.nextLine();
        System.out.print("Enter Password: ");
        String empPassword = scanner.nextLine();

        boolean authenticated = false;
        for (int i = 0; i < employeeIDs.length; i++) {
            if (employeeIDs[i].equals(empID) && employeePasswords[i].equals(empPassword)) {
                authenticated = true;
                break;
            }
        }

        if (authenticated) {
            System.out.println("EmployeeC authentication successful!");
        } else {
            System.out.println("Invalid EmployeeC ID or Password.");
        }
    }

    // 5. Assign CourierC using Loops
    public static void assignCourier(Scanner scanner) {
        String[] couriers = {"CourierC A", "CourierC B", "CourierC C", "CourierC D"};
        int[] distances = {5, 3, 8, 2};
        double[] capacities = {10.0, 15.0, 8.0, 12.0};

        System.out.print("Enter shipment weight (kg): ");
        double shipmentWeight = scanner.nextDouble();

        String assignedCourier = null;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < couriers.length; i++) {
            if (capacities[i] >= shipmentWeight && distances[i] < minDistance) {
                assignedCourier = couriers[i];
                minDistance = distances[i];
            }
        }

        if (assignedCourier != null) {
            System.out.println("Assigned " + assignedCourier + " (Distance: " + minDistance + " km) ");
        } else {
            System.out.println("No available couriers for this shipment! ");
        }
    }
}
