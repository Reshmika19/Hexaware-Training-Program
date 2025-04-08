package dao;

import entity.CourierC;
import entity.PaymentC;
import entity.UserC;

import java.util.*;

public class CourierUserServiceImpCollection implements ICourierUserServiceCollection {

    @Override
    public void createUser(Scanner scanner, ArrayList<UserC> userCList) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        int userId = userCList.size() + 1;

        UserC newUserC = new UserC(userId, name, email, password, phoneNumber, address);
        userCList.add(newUserC);

        System.out.println("UserC created successfully! UserC ID: " + newUserC.getUserID());
    }

    @Override
    public UserC loginUser(Scanner scanner, ArrayList<UserC> userCList) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            for (UserC userC : userCList) {
                if (userC.getEmail().equals(email) && userC.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + userC.getUserName());
                    return userC;
                }
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Invalid email or password. Attempts left: " + attempts);
            } else {
                System.out.println("Too many failed attempts. Returning to menu.");
            }
        }
        return null;
    }

    public String placeCourier(UserC userC, Map<Integer, List<CourierC>> courierMap, ArrayList<PaymentC> paymentCList, Scanner scanner) {
        System.out.print("Enter Receiver's Name: ");
        String receiverName = scanner.nextLine();
        System.out.print("Enter Receiver's Address: ");
        String receiverAddress = scanner.nextLine();
        System.out.print("Enter Weight (in kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        double costPerKg = 50;
        double cost = weight * costPerKg;
        System.out.println("Calculated Cost: ₹" + cost);

        CourierC newCourierC = new CourierC(userC.getUserName(), userC.getAddress(), receiverName, receiverAddress,
                weight, userC.getUserID());
        courierMap.putIfAbsent(userC.getUserID(), new ArrayList<>());
        courierMap.get(userC.getUserID()).add(newCourierC);
        System.out.print("Do you want to proceed with the payment of ₹" + cost + " (yes/no)? ");
        String paymentChoice = scanner.nextLine();

        if ("yes".equalsIgnoreCase(paymentChoice)) {
            long paymentID = System.currentTimeMillis();
            PaymentC paymentC = new PaymentC(paymentID, newCourierC.getCourierID(), cost, new Date());
            paymentCList.add(paymentC);
            System.out.println("PaymentC Successful! Your Tracking Number: " + newCourierC.getTrackingNumber());
            return newCourierC.getTrackingNumber();
        } else {
            System.out.println("CourierC placed without payment.");
            return null;
        }
    }

    public String getOrderStatus(String trackingNumber, Map<Integer, List<CourierC>> courierMap) {
        for (List<CourierC> courierCList : courierMap.values()) {
            for (CourierC courierC : courierCList) {
                if (courierC.getTrackingNumber().equals(trackingNumber)) {
                    return "Order Status: " + courierC.getStatus();
                }
            }
        }
        return "Tracking Number not found!";
    }

    public String cancelOrder(String trackingNumber, Map<Integer, List<CourierC>> courierMap) {

        for (Map.Entry<Integer, List<CourierC>> entry : courierMap.entrySet()) {
            List<CourierC> courierCList = entry.getValue();

            Iterator<CourierC> iterator = courierCList.iterator();
            while (iterator.hasNext()) {
                CourierC courierC = iterator.next();
                if (courierC.getTrackingNumber().equals(trackingNumber)) {

                    if (courierC.getStatus().equalsIgnoreCase("Delivered")) {
                        return "Order cannot be canceled as it has already been delivered!";
                    }

                    iterator.remove();
                    return "Order with Tracking Number " + trackingNumber + " has been successfully canceled.";
                }
            }
        }
        return "Tracking Number not found!";
    }

}
