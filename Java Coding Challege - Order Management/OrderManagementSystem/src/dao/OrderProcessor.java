package dao;

import entity.Order;
import entity.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class OrderProcessor implements IOrderManagementRepository {

    @Override
    public void createUser(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();
        scanner.nextLine(); // clear buffer
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO user (username, password, role, address) VALUES (?, ?, 'User', ?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, address);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    System.out.println("User created successfully! Details:");
                    System.out.println("User ID: " + userId + ", Username: " + username + ", Role: User, Address: " + address);
                    System.out.println("\nNow, please login using your credentials.");

                }
            } else {
                System.out.println("Failed to create user.");
            }
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public User loginUser(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ? AND password = ? AND role = 'User'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));

                System.out.println("Login Successful! Welcome, " + user.getUsername());
                return user;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        return null;
    }

    public void createOrder(User user) {
        Scanner sc = new Scanner(System.in);
        AdminProcessor adminProcessor = new AdminProcessor();
        adminProcessor.viewAllProducts(); // Display products table

        System.out.print("Enter Product ID to order: ");
        int productId = sc.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        String getProductQuery = "SELECT price, quantity_in_stock FROM product WHERE product_id = ?";
        String insertOrderQuery = "INSERT INTO orders (user_id, product_id, quantity, total_amt, payment_mode, status) VALUES (?, ?, ?, ?, ?, 'Placed')";
        String updateQtyQuery = "UPDATE product SET quantity_in_stock = quantity_in_stock - ? WHERE product_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement productPs = conn.prepareStatement(getProductQuery)) {

            // Get product price and stock
            productPs.setInt(1, productId);
            ResultSet rs = productPs.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                int availableQty = rs.getInt("quantity_in_stock");

                if (quantity > availableQty) {
                    System.out.println("Not enough stock available!");
                    return;
                }

                double totalAmount = price * quantity;
                System.out.println("Total Amount: ₹" + totalAmount);

                // Payment mode
                System.out.println("Choose Payment Mode:");
                System.out.println("1. Cash");
                System.out.println("2. UPI");
                System.out.println("3. Card");
                System.out.print("Enter your choice: ");
                int paymentChoice = sc.nextInt();

                String paymentMode;
                switch (paymentChoice) {
                    case 1: paymentMode = "Cash"; break;
                    case 2: paymentMode = "UPI"; break;
                    case 3: paymentMode = "Card"; break;
                    default:
                        System.out.println("Invalid choice! Defaulting to 'Cash'");
                        paymentMode = "Cash";
                }

                // Insert into orders
                try (PreparedStatement insertPs = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertPs.setInt(1, user.getUserId());
                    insertPs.setInt(2, productId);
                    insertPs.setInt(3, quantity);
                    insertPs.setDouble(4, totalAmount);
                    insertPs.setString(5, paymentMode);

                    int rows = insertPs.executeUpdate();

                    if (rows > 0) {
                        ResultSet keys = insertPs.getGeneratedKeys();
                        if (keys.next()) {
                            int orderId = keys.getInt(1);

                            // Update product stock
                            try (PreparedStatement updatePs = conn.prepareStatement(updateQtyQuery)) {
                                updatePs.setInt(1, quantity);
                                updatePs.setInt(2, productId);
                                updatePs.executeUpdate();
                            }

                            // Fetch product name and type
                            String productDetailsQuery = "SELECT product_name, type FROM product WHERE product_id = ?";
                            try (PreparedStatement productDetailPs = conn.prepareStatement(productDetailsQuery)) {
                                productDetailPs.setInt(1, productId);
                                ResultSet pdRs = productDetailPs.executeQuery();

                                if (pdRs.next()) {
                                    String productName = pdRs.getString("product_name");
                                    String type = pdRs.getString("type");

                                    String extraDetails = "";

                                    if (type.equalsIgnoreCase("Clothing")) {
                                        String clothingQuery = "SELECT size, color FROM clothing WHERE product_id = ?";
                                        try (PreparedStatement clothingPs = conn.prepareStatement(clothingQuery)) {
                                            clothingPs.setInt(1, productId);
                                            ResultSet cRs = clothingPs.executeQuery();
                                            if (cRs.next()) {
                                                String size = cRs.getString("size");
                                                String color = cRs.getString("color");
                                                extraDetails = "Size       : " + size + "\n" +
                                                        "Color      : " + color;
                                            }
                                        }
                                    } else if (type.equalsIgnoreCase("Electronics")) {
                                        String electronicsQuery = "SELECT brand, warranty_period FROM electronics WHERE product_id = ?";
                                        try (PreparedStatement elecPs = conn.prepareStatement(electronicsQuery)) {
                                            elecPs.setInt(1, productId);
                                            ResultSet eRs = elecPs.executeQuery();
                                            if (eRs.next()) {
                                                String brand = eRs.getString("brand");
                                                int warranty = eRs.getInt("warranty_period");
                                                extraDetails = "Brand      : " + brand + "\n" +
                                                        "Warranty   : " + warranty + " months";
                                            }
                                        }
                                    }

                                    // Display full order details
                                    System.out.println("✅ Order Placed Successfully!");
                                    System.out.println("Order Details:");
                                    System.out.println("Order ID   : " + orderId);
                                    System.out.println("Product    : " + productName + " (" + type + ")");
                                    System.out.println(extraDetails);
                                    System.out.println("Quantity   : " + quantity);
                                    System.out.println("Amount     : ₹" + totalAmount);
                                    System.out.println("Payment    : " + paymentMode);
                                    System.out.println("Status     : Placed");
                                }
                            }

                        }
                    } else {
                        System.out.println("Failed to place order.");
                    }

                }

            } else {
                System.out.println("Product not found!");
            }

        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    public List<String> cancelOrder(int orderId, int userId) throws UserNotFoundException, OrderNotFoundException {
        List<String> messages = new ArrayList<>();

        String checkUserQuery = "SELECT user_id FROM orders WHERE order_id = ?";
        String fetchQuery = "SELECT o.product_id, o.quantity, p.product_name " +
                "FROM orders o JOIN product p ON o.product_id = p.product_id " +
                "WHERE o.order_id = ?";
        String deleteQuery = "DELETE FROM orders WHERE order_id = ?";
        String updateStockQuery = "UPDATE product SET quantity_in_stock = quantity_in_stock + ? WHERE product_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
             PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {

            // Verify user
            userStmt.setInt(1, orderId);
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                int foundUserId = userRs.getInt("user_id");
                if (foundUserId != userId) {
                    throw new UserNotFoundException("You are not authorized to cancel this order.");
                }
            } else {
                throw new OrderNotFoundException("Order ID not found.");
            }

            // Proceed to cancel
            fetchStmt.setInt(1, orderId);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                String productName = rs.getString("product_name");

                deleteStmt.setInt(1, orderId);
                int deleted = deleteStmt.executeUpdate();

                if (deleted > 0) {
                    updateStockStmt.setInt(1, quantity);
                    updateStockStmt.setInt(2, productId);
                    updateStockStmt.executeUpdate();

                    messages.add("Your order (Order ID: " + orderId + ") for \"" + productName + "\" has been cancelled.");
                    messages.add("Refund initiated.");
                } else {
                    throw new OrderNotFoundException("Order already cancelled or doesn't exist.");
                }
            } else {
                throw new OrderNotFoundException("Order not found.");
            }

        } catch (SQLException e) {
            messages.add("Error cancelling order: " + e.getMessage());
        }

        return messages;
    }


    public void viewHistory(User user) {
        String query = """
        SELECT o.order_id, p.product_name, p.type, o.quantity, o.total_amt,
               o.payment_mode, o.status, o.order_date,
               e.brand AS e_brand, e.warranty_period,
               c.size AS c_size, c.color AS c_color
        FROM orders o
        JOIN product p ON o.product_id = p.product_id
        LEFT JOIN electronics e ON p.product_id = e.product_id
        LEFT JOIN clothing c ON p.product_id = c.product_id
        WHERE o.user_id = ?
        ORDER BY o.order_date DESC
    """;

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();

            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-15s | %-10s | %-12s | %-15s | %-5s | %-8s | %-10s | %-10s | %-20s |\n",
                    "ID", "Product", "Type", "Brand/Size", "Warranty/Color", "Qty", "Amount", "Payment", "Status", "Date");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;

                int orderId = rs.getInt("order_id");
                String productName = rs.getString("product_name");
                String type = rs.getString("type");
                int qty = rs.getInt("quantity");
                double amount = rs.getDouble("total_amt");
                String payment = rs.getString("payment_mode");
                String status = rs.getString("status");
                Timestamp date = rs.getTimestamp("order_date");

                String brandOrSize = type.equalsIgnoreCase("Electronics") ?
                        rs.getString("e_brand") : rs.getString("c_size");
                String warrantyOrColor = type.equalsIgnoreCase("Electronics") ?
                        rs.getInt("warranty_period") + " months" : rs.getString("c_color");

                System.out.printf("| %-3d | %-15s | %-10s | %-12s | %-15s | %-5d | %-8.2f | %-10s | %-10s | %-20s |\n",
                        orderId, productName, type, brandOrSize, warrantyOrColor,
                        qty, amount, payment, status, date.toString());
            }

            if (!hasData) {
                System.out.println("|                                  No orders found for your account.                                                 |");
            }

            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error fetching order history: " + e.getMessage());
        }
    }

    public void updateProfile(User user) {
        Scanner sc = new Scanner(System.in);
        boolean updated = false;

        System.out.print("Do you want to update your password? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new password: ");
            String newPassword = sc.nextLine();
            try (Connection conn = DBConnUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE user SET password=? WHERE user_id=?")) {
                ps.setString(1, newPassword);
                ps.setInt(2, user.getUserId());
                ps.executeUpdate();
                System.out.println("Password updated successfully.");
                updated = true;
            } catch (Exception e) {
                System.out.println("Error updating password: " + e.getMessage());
            }
        }

        System.out.print("Do you want to update your address? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new address: ");
            String newAddress = sc.nextLine();
            try (Connection conn = DBConnUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE user SET address=? WHERE user_id=?")) {
                ps.setString(1, newAddress);
                ps.setInt(2, user.getUserId());
                ps.executeUpdate();
                System.out.println("Address updated successfully.");
                updated = true;
            } catch (Exception e) {
                System.out.println("Error updating address: " + e.getMessage());
            }
        }

        if (!updated) {
            System.out.println("No changes made to your profile.");
        }
    }

    public void deleteAccount(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you sure you want to delete your account?");
        System.out.println("All related data including orders will be permanently removed.");
        System.out.print("Type 'yes' to confirm: ");
        if (!sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Account deletion cancelled.");
            return;
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Get all pending orders
            String getOrdersQuery = "SELECT order_id, product_id, quantity, status FROM orders WHERE user_id = ?";
            try (PreparedStatement getOrdersPS = conn.prepareStatement(getOrdersQuery)) {
                getOrdersPS.setInt(1, user.getUserId());
                ResultSet rs = getOrdersPS.executeQuery();

                while (rs.next()) {
                    String status = rs.getString("status");
                    if (!status.equalsIgnoreCase("Delivered")) {
                        int productId = rs.getInt("product_id");
                        int qty = rs.getInt("quantity");

                        // Update stock back
                        try (PreparedStatement updateStockPS = conn.prepareStatement(
                                "UPDATE product SET quantity_in_stock = quantity_in_stock + ? WHERE product_id = ?")) {
                            updateStockPS.setInt(1, qty);
                            updateStockPS.setInt(2, productId);
                            updateStockPS.executeUpdate();
                        }
                    }
                }
            }

            // 2. Delete orders
            try (PreparedStatement deleteOrders = conn.prepareStatement("DELETE FROM orders WHERE user_id = ?")) {
                deleteOrders.setInt(1, user.getUserId());
                deleteOrders.executeUpdate();
            }

            // 3. Delete user
            try (PreparedStatement deleteUser = conn.prepareStatement("DELETE FROM user WHERE user_id = ?")) {
                deleteUser.setInt(1, user.getUserId());
                deleteUser.executeUpdate();
            }

            conn.commit();
            System.out.println("Your account and all associated data have been successfully deleted.");
            System.out.println("We're sad to see you go. Thank you for being with us.");
        } catch (Exception e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }




}
