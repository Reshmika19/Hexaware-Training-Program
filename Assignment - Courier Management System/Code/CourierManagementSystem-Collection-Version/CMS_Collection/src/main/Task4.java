package main;

import java.util.Random;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask 4 - Choose an option:");
            System.out.println("1. Parcel Tracking");
            System.out.println("2. Customer Data Validation");
            System.out.println("3. Address Formatting");
            System.out.println("4. Order Confirmation Email");
            System.out.println("5. Calculate Shipping Costs");
            System.out.println("6. Generate Password");
            System.out.println("7. Find Similar Addresses");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    parcelTracking(scanner);
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    customerDataValidation(name, "name");

                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    customerDataValidation(phone, "phone");

                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    customerDataValidation(address, "address");
                    break;
                case 3:
                    addressFormatting(scanner);
                    break;
                case 4:
                    orderConfirmationEmail(scanner);
                    break;
                case 5:
                    calculateShippingCost(scanner);
                    break;
                case 6:
                    System.out.print("Enter the desired password length: ");
                    int length = scanner.nextInt();

                    String password = generateRandomPassword(length);
                    System.out.println("Generated password: " + password);
                    break;
                case 7:
                    findSimilarAddresses(scanner);
                    break;
                case 8:
                    System.out.println("Exiting Task 4...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }

    // 1. Parcel Tracking
    public static void parcelTracking(Scanner scanner) {
        String[][] trackingData = {
                {"12345", "Parcel in transit"},
                {"67890", "Parcel out for delivery"},
                {"54321", "Parcel delivered"},
                {"98765", "Parcel in transit"},
                {"11111", "Parcel shipped"},
                {"22222", "Parcel processing"},
                {"33333", "Parcel at sorting facility"},
                {"44444", "Parcel pending pickup"}
        };

        System.out.print("Enter tracking number: ");
        String trackingNumber = scanner.nextLine();

        boolean found = false;
        for (String[] parcel : trackingData) {
            if (parcel[0].equals(trackingNumber)) {
                System.out.println("Status: " + parcel[1]);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Tracking number not found!");
        }
    }

    // 2. Customer Data Validation
    public static void customerDataValidation(String data, String type) {
        switch (type.toLowerCase()) {
            case "name":
                if (data.matches("[A-Za-z ]+")) {
                    System.out.println("Valid name format.");
                } else {
                    System.out.println("Invalid name! Name should contain only letters.");
                }
                break;
            case "address":
                if (data.matches("[A-Za-z0-9 ,.-]+")) {
                    System.out.println("Valid address format.");
                } else {
                    System.out.println("Invalid address! Address should not contain special characters.");
                }
                break;
            case "phone":
                if (data.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println("Valid phone number format.");
                } else {
                    System.out.println("Invalid phone number! Format should be ###-###-####.");
                }
                break;
            default:
                System.out.println("Invalid detail type!");
        }
    }

    // 3. Address Formatting
    public static void addressFormatting(Scanner scanner) {
        System.out.print("Enter Street: ");
        String street = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        System.out.print("Enter Zip Code: ");
        String zip = scanner.nextLine();

        if (!zip.matches("\\d{6}(-\\d{4})?")) {
            System.out.println("Invalid Zip Code! Format should be ##### or #####-####.");
            return;
        }

        street = capitalizeWords(street);
        city = capitalizeWords(city);
        state = capitalizeWords(state);

        System.out.println("Formatted Address: " + street + ", " + city + ", " + state + " - " + zip);
    }

    private static String capitalizeWords(String str) {
        String[] words = str.split(" ");
        StringBuilder capitalizedStr = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedStr.toString().trim();
    }

    // 4. Order Confirmation Email
    public static void orderConfirmationEmail(Scanner scanner) {

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter order number: ");
        String orderNumber = scanner.nextLine();

        System.out.print("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Enter expected delivery date (YYYY-MM-DD): ");
        String deliveryDate = scanner.nextLine();

        String emailContent = generateOrderConfirmationEmail(name, orderNumber, deliveryAddress, deliveryDate);

        System.out.println("\n--- Order Confirmation Email ---");
        System.out.println(emailContent);
    }

    public static String generateOrderConfirmationEmail(String name, String orderNumber, String deliveryAddress, String deliveryDate) {
        return "Dear " + name + ",\n\n" +
                "Thank you for your order! Your order details are as follows:\n" +
                "Order Number: " + orderNumber + "\n" +
                "Delivery Address: " + deliveryAddress + "\n" +
                "Expected Delivery Date: " + deliveryDate + "\n\n" +
                "We appreciate your business and will notify you once your parcel is shipped.\n" +
                "\nBest regards,\n" +
                "The CourierC Team";
    }

    // 5. Calculate Shipping Costs
    public static void calculateShippingCost(Scanner scanner) {

        System.out.print("Enter source location: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination location: ");
        String destination = scanner.nextLine();
        System.out.print("Enter the weight of the parcel (in kg): ");

        double weight = scanner.nextDouble();
        double costPerKm = 2.0;
        double costPerKg = 5.0;

        double distance = calculateDistance(source, destination);

        double shippingCost = (costPerKm * distance) + (costPerKg * weight);

        System.out.println("Shipping Cost from " + source + " to " + destination + " is: Rs. " + shippingCost);
    }

    public static double calculateDistance(String source, String destination) {
        return Math.abs(source.length() - destination.length()) * 10;
    }

    //6. Generate Password
    public static String generateRandomPassword(int length) {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()-_+=<>?/[]{}|";

        String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;
        Random random = new Random();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(index));
        }

        return password.toString();
    }

    // 7. Find Similar Addresses
    public static void findSimilarAddresses(Scanner scanner) {
        String[] existingAddresses = {
                "No 10, Anna Nagar, Chennai, Tamil Nadu 600040",
                "No 45, Tidel Park, Chennai, Tamil Nadu 600100",
                "No 22, Kotturpuram, Chennai, Tamil Nadu 600085",
                "No 89, Egmore, Chennai, Tamil Nadu 600008",
                "No 12, Mount Road, Chennai, Tamil Nadu 600002",
                "No 56, Adyar, Chennai, Tamil Nadu 600020",
                "No 33, Mylapore, Chennai, Tamil Nadu 600004",
                "No 78, T Nagar, Chennai, Tamil Nadu 600017"
        };

        System.out.print("Enter an address to find similar addresses: ");
        String inputAddress = scanner.nextLine().toLowerCase().replaceAll("[^a-z0-9 ]", "").trim();

        System.out.println("Searching for similar addresses...");
        boolean foundSimilar = false;

        for (String address : existingAddresses) {
            String formattedAddress = address.toLowerCase().replaceAll("[^a-z0-9 ]", "").trim();
            if (formattedAddress.contains(inputAddress)) {
                System.out.println("Similar address found: " + address);
                foundSimilar = true;
            }
        }

        if (!foundSimilar) {
            System.out.println("No similar addresses found.");
        }
    }


}
