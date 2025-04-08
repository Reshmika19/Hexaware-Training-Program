package dao;

import entity.User;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CourierUserServiceImp implements ICourierUserService {

    private final Connection connection;

    public CourierUserServiceImp() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void createUser(Scanner scanner) {
        System.out.println("\n------------------------------ Create New User --------------------------------------------");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String sql = "INSERT INTO user (userName, email, password, contactNumber, address) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, contact);
            pstmt.setString(5, address);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        System.out.println("\nUser created successfully!");
                        System.out.println("User ID: " + userId);
                        System.out.println("Name: " + name);
                        System.out.println("Email: " + email);
                        System.out.println("Contact: " + contact);
                        System.out.println("Address: " + address);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while creating user: " + e.getMessage());
        }
    }

    @Override
    public User loginUser(Scanner scanner) {
        System.out.println("\n----------------------------------- User Login --------------------------------------------");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userID");
                String userName = rs.getString("userName");
                String contact = rs.getString("contactNumber");
                String address = rs.getString("address");

                User user = new User(userId, userName, email, password, contact, address);

                System.out.println("\nWelcome, " + userName + "! You are successfully logged in.");

                return user;
            } else {
                System.out.println("Invalid email or password.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String placeCourier(User user) {
        System.out.println("\n----------------------------- Place New Courier ------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Receiver Name: ");
        String receiverName = scanner.nextLine();

        System.out.print("Enter Receiver Address: ");
        String receiverAddress = scanner.nextLine();

        System.out.print("Enter Weight (kg): ");
        double weight = scanner.nextDouble();

        System.out.print("Enter Distance (km): ");
        double distance = scanner.nextDouble();
        scanner.nextLine();

        String trackingNumber = "TKN" + new java.text.SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date());

        LocalDate deliveryDate = LocalDate.now().plusDays(4);

        String status = "Pending";
        int employeeId = 10;
        int locationId = 10;
        int courierCompanyId = 10;
        double amount = 0.0;

        String courierSql = "INSERT INTO courier (senderName, senderAddress, receiverName, receiverAddress, weight, status, trackingNumber, deliveryDate, userId, employeeId, locationId, courierCompany, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(courierSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, receiverName);
            pstmt.setString(4, receiverAddress);
            pstmt.setDouble(5, weight);
            pstmt.setString(6, status);
            pstmt.setString(7, trackingNumber);
            pstmt.setDate(8, java.sql.Date.valueOf(deliveryDate));
            pstmt.setInt(9, user.getUserID());
            pstmt.setInt(10, employeeId);
            pstmt.setInt(11, locationId);
            pstmt.setInt(12, courierCompanyId);
            pstmt.setDouble(13, distance);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long courierID = rs.getLong(1);

                        if (distance <= 5) {
                            amount = weight * 30;
                        } else if (distance <= 10) {
                            amount = weight * 40;
                        } else {
                            amount = weight * 50;
                        }

                        System.out.println("\nCalculated Cost: ₹" + amount);
                        System.out.print("Do you want to pay now? (yes/no): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("yes")) {
                            // Insert into payment table
                            String paymentSql = "INSERT INTO payment (courierID, amount, paymentDate) VALUES (?, ?, ?)";
                            try (PreparedStatement paymentStmt = connection.prepareStatement(paymentSql, Statement.RETURN_GENERATED_KEYS)) {
                                paymentStmt.setLong(1, courierID);
                                paymentStmt.setDouble(2, amount);
                                paymentStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

                                int paymentRows = paymentStmt.executeUpdate();
                                if (paymentRows > 0) {
                                    try (ResultSet payRs = paymentStmt.getGeneratedKeys()) {
                                        if (payRs.next()) {
                                            long paymentID = payRs.getLong(1);

                                            String updateCourierAmountSql = "UPDATE courier SET amount = ? WHERE courierID = ?";
                                            try (PreparedStatement updateCourierStmt = connection.prepareStatement(updateCourierAmountSql)) {
                                                updateCourierStmt.setDouble(1, amount);
                                                updateCourierStmt.setLong(2, courierID);
                                                updateCourierStmt.executeUpdate();
                                            }

                                            System.out.println("\nCourier Placed Successfully and Payment Done!");
                                            System.out.println("Tracking Number: " + trackingNumber);
                                            System.out.println("Courier ID: " + courierID);
                                            System.out.println("Payment ID: " + paymentID);
                                            System.out.println("Amount Paid: ₹" + amount);
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Courier Placed Successfully, but Payment Pending.");
                            System.out.println("Tracking Number: " + trackingNumber);
                        }

                        return trackingNumber;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error placing courier: " + e.getMessage());
        }

        return null;
    }


    @Override
    public String getOrderStatus(String trackingNumber) {
        String sql = "SELECT status FROM courier WHERE trackingNumber = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, trackingNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    System.out.println("Order Status: " + status);
                    return status;
                } else {
                    System.out.println("No courier found with the provided tracking number.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order status: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String cancelOrder(long courierId) {
        String statusQuery = "SELECT status FROM courier WHERE courierID = ?";
        String deletePaymentSQL = "DELETE FROM payment WHERE courierID = ?";
        String deleteCourierSQL = "DELETE FROM courier WHERE courierID = ?";

        try {
            try (PreparedStatement statusStmt = connection.prepareStatement(statusQuery)) {
                statusStmt.setLong(1, courierId);
                ResultSet rs = statusStmt.executeQuery();

                if (rs.next()) {
                    String status = rs.getString("status");

                    if (status.equalsIgnoreCase("Out for Delivery") || status.equalsIgnoreCase("Delivered")) {
                        return "Cannot cancel. Courier is already '" + status + "'.";
                    }
                } else {
                    return "No courier found with ID: " + courierId;
                }
            }

            connection.setAutoCommit(false);

            try (PreparedStatement paymentStmt = connection.prepareStatement(deletePaymentSQL)) {
                paymentStmt.setLong(1, courierId);
                paymentStmt.executeUpdate();
            }

            try (PreparedStatement courierStmt = connection.prepareStatement(deleteCourierSQL)) {
                courierStmt.setLong(1, courierId);
                int rows = courierStmt.executeUpdate();

                if (rows > 0) {
                    connection.commit();
                    return "Courier with ID " + courierId + " cancelled successfully.";
                } else {
                    connection.rollback();
                    return "Failed to cancel courier.";
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            return "Error during cancellation: " + e.getMessage();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }

    @Override
    public void viewOrderHistory(int userId) {
        String sql = "SELECT c.courierID, c.receiverName, c.weight, c.distance, " +
                "c.trackingNumber, c.status, c.deliveryDate, p.amount, cc.Name as  `Company name`  " +
                "FROM courier c " +
                "LEFT JOIN payment p ON c.courierID = p.courierID " +
                "LEFT JOIN couriercompany cc ON c.courierCompany = cc.id " +
                "WHERE c.userId = ? ORDER BY c.courierID DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;

            System.out.println("\n-------------------------------------------------- ORDER HISTORY ------------------------------------------------------------");
            System.out.printf("| %-10s | %-12s | %-6s | %-8s | %-8s | %-13s | %-17s | %-13s | %-15s |\n",
                    "Courier ID", "Receiver", "Weight", "Distance", "Cost", "Status", "Tracking No", "Delivery Date", "Courier Company");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                found = true;
                System.out.printf("| %-10d | %-12s | %-6.1f | %-8.1f | ₹%-7.2f | %-13s | %-17s | %-13s | %-15s |\n",
                        rs.getLong("courierID"),
                        rs.getString("receiverName"),
                        rs.getDouble("weight"),
                        rs.getDouble("distance"),
                        rs.getDouble("amount"),
                        rs.getString("status"),
                        rs.getString("trackingNumber"),
                        rs.getDate("deliveryDate"),
                        rs.getString("Company name"));
            }

            if (!found) {
                System.out.println("No courier orders found for this user.");
            } else {
                System.out.println("--------------------------------------------------------------------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order history: " + e.getMessage());
        }
    }



}

