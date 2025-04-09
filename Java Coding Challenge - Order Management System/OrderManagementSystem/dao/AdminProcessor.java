package dao;

import entity.Product;
import entity.User;
import util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Scanner;

public class AdminProcessor implements IAdmin {

    @Override
    public void createAdmin(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        String defaultAddress = "OrderManagement HQ";

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO user (username, password, role, address) VALUES (?, ?, 'Admin', ?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, defaultAddress);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    System.out.println("Admin created successfully! Details:");
                    System.out.println("User ID: " + userId + ", Username: " + username + ", Role: Admin, Address: " + defaultAddress);
                    System.out.println("Now, Login");
                }
            } else {
                System.out.println("Failed to create admin.");
            }
        } catch (Exception e) {
            System.out.println("Error creating admin: " + e.getMessage());
        }
    }

    @Override
    public User loginAdmin(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ? AND password = ? AND role = 'Admin'";
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

    @Override
    public void createProduct(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();

        System.out.println("Select Product Type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Enter your choice: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        String type = null;

        String brand = null, size = null, color = null;
        int warranty = 0;

        if (typeChoice == 1) {
            type = "Electronics";
            System.out.print("Enter Brand: ");
            brand = scanner.next();
            System.out.print("Enter Warranty Period (in months): ");
            warranty = scanner.nextInt();
        } else if (typeChoice == 2) {
            type = "Clothing";
            System.out.print("Enter Size: ");
            size = scanner.next();
            System.out.print("Enter Color: ");
            color = scanner.next();
        } else {
            System.out.println("Invalid choice. Product not created.");
            return;
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO product (product_name, description, price, quantity_in_stock, type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            ps.setString(5, type);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int productId = rs.getInt(1);

                if ("Electronics".equalsIgnoreCase(type)) {
                    String esql = "INSERT INTO electronics (product_id, brand, warranty_period) VALUES (?, ?, ?)";
                    PreparedStatement eps = conn.prepareStatement(esql);
                    eps.setInt(1, productId);
                    eps.setString(2, brand);
                    eps.setInt(3, warranty);
                    eps.executeUpdate();

                    System.out.println("\nElectronics Product Created Successfully!");
                    System.out.println("Product ID: " + productId);
                    System.out.println("Name: " + name);
                    System.out.println("Description: " + description);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Type: " + type);
                    System.out.println("Brand: " + brand);
                    System.out.println("Warranty: " + warranty + " months");
                } else {
                    String csql = "INSERT INTO clothing (product_id, size, color) VALUES (?, ?, ?)";
                    PreparedStatement cps = conn.prepareStatement(csql);
                    cps.setInt(1, productId);
                    cps.setString(2, size);
                    cps.setString(3, color);
                    cps.executeUpdate();

                    System.out.println("\nClothing Product Created Successfully!");
                    System.out.println("Product ID: " + productId);
                    System.out.println("Name: " + name);
                    System.out.println("Description: " + description);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Type: " + type);
                    System.out.println("Size: " + size);
                    System.out.println("Color: " + color);
                }
            } else {
                System.out.println("Failed to retrieve generated Product ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllProducts() {
        String productQuery = "SELECT * FROM product";
        String electronicsQuery = "SELECT brand, warranty_period FROM electronics WHERE product_id = ?";
        String clothingQuery = "SELECT size, color FROM clothing WHERE product_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(productQuery);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-13s | %-15s | %-7s | %-3s | %-12s | %-12s | %-18s |\n",
                    "ID", "Name", "Description", "Price", "Qty", "Type", "Brand/Size", "Warranty/Color");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
                int qty = rs.getInt("quantity_in_stock");
                String type = rs.getString("type");

                String col1 = "", col2 = "";

                if ("Electronics".equalsIgnoreCase(type)) {
                    try (PreparedStatement eps = conn.prepareStatement(electronicsQuery)) {
                        eps.setInt(1, id);
                        ResultSet ers = eps.executeQuery();
                        if (ers.next()) {
                            col1 = ers.getString("brand");
                            col2 = ers.getInt("warranty_period") + " months";
                        }
                    }
                } else if ("Clothing".equalsIgnoreCase(type)) {
                    try (PreparedStatement cps = conn.prepareStatement(clothingQuery)) {
                        cps.setInt(1, id);
                        ResultSet crs = cps.executeQuery();
                        if (crs.next()) {
                            col1 = crs.getString("size");
                            col2 = crs.getString("color");
                        }
                    }
                }

                System.out.printf("| %-3d | %-13s | %-15s | %-7.2f | %-3d | %-12s | %-12s | %-18s |\n",
                        id, name, desc, price, qty, type, col1, col2);
            }

            System.out.println("----------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    public void viewAllUsers() {
        String query = "SELECT * FROM user where Role = 'user'";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-15s | %-10s | %-40s |\n",
                    "ID", "Username", "Role", "Address");
            System.out.println("----------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String role = rs.getString("role");
                String address = rs.getString("address");

                System.out.printf("| %-3d | %-15s | %-10s | %-40s |\n",
                        id, username, role, address);
            }

            System.out.println("----------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }

    public void viewAllElectronicProducts() {
        String query = "SELECT p.product_id, p.product_name, p.description, p.price, p.quantity_in_stock, " +
                "e.brand, e.warranty_period " +
                "FROM product p INNER JOIN electronics e ON p.product_id = e.product_id";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-15s | %-25s | %-7s | %-8s | %-10s | %-15s |\n",
                    "ID", "Name", "Description", "Price", "Stock", "Brand", "Warranty(Mo)");
            System.out.println("--------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("quantity_in_stock");
                String brand = rs.getString("brand");
                int warranty = rs.getInt("warranty_period");

                System.out.printf("| %-3d | %-15s | %-25s | %-7.2f | %-8d | %-10s | %-15d |\n",
                        id, name, desc, price, stock, brand, warranty);
            }

            System.out.println("--------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving electronic products: " + e.getMessage());
        }
    }

    public void viewAllClothingProducts() {
        String query = "SELECT p.product_id, p.product_name, p.description, p.price, p.quantity_in_stock, " +
                "c.size, c.color " +
                "FROM product p INNER JOIN clothing c ON p.product_id = c.product_id";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-15s | %-25s | %-7s | %-8s | %-8s | %-15s |\n",
                    "ID", "Name", "Description", "Price", "Stock", "Size", "Color");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("quantity_in_stock");
                String size = rs.getString("size");
                String color = rs.getString("color");

                System.out.printf("| %-3d | %-15s | %-25s | %-7.2f | %-8d | %-8s | %-15s |\n",
                        id, name, desc, price, stock, size, color);
            }

            System.out.println("----------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving clothing products: " + e.getMessage());
        }
    }

    public void viewAllOrders() {
        String query = "SELECT o.order_id, u.username, p.product_name, p.type, " +
                "e.brand, e.warranty_period, c.size, c.color, " +
                "o.quantity, o.total_amt, o.payment_mode, o.status, o.order_date " +
                "FROM orders o " +
                "JOIN user u ON o.user_id = u.user_id " +
                "JOIN product p ON o.product_id = p.product_id " +
                "LEFT JOIN electronics e ON p.product_id = e.product_id " +
                "LEFT JOIN clothing c ON p.product_id = c.product_id";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-12s | %-15s | %-10s | %-10s | %-12s | %-6s | %-10s | %-8s | %-10s | %-10s | %-10s | %-20s |\n",
                    "ID", "Username", "Product", "Type", "Brand", "Warranty", "Size", "Color", "Qty", "Amount", "Payment", "Status", "Order Date");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String username = rs.getString("username");
                String product = rs.getString("product_name");
                String type = rs.getString("type");
                String brand = rs.getString("brand");
                String warranty = rs.getString("warranty_period") != null ? rs.getString("warranty_period") + " months" : "-";
                String size = rs.getString("size") != null ? rs.getString("size") : "-";
                String color = rs.getString("color") != null ? rs.getString("color") : "-";
                int qty = rs.getInt("quantity");
                double amount = rs.getDouble("total_amt");
                String payment = rs.getString("payment_mode");
                String status = rs.getString("status");
                Timestamp date = rs.getTimestamp("order_date");

                System.out.printf("| %-3d | %-12s | %-15s | %-10s | %-10s | %-12s | %-6s | %-10s | %-8d | %-10.2f | %-10s | %-10s | %-20s |\n",
                        orderId, username, product, type, brand != null ? brand : "-", warranty, size, color, qty, amount, payment, status, date);
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving all orders: " + e.getMessage());
        }
    }

    public void viewOrdersByUsername(String username) {
        String query = "SELECT o.order_id, p.product_name, p.type, e.brand, e.warranty_period, c.size, c.color, " +
                "o.quantity, o.total_amt, o.payment_mode, o.status, o.order_date " +
                "FROM orders o " +
                "JOIN user u ON o.user_id = u.user_id " +
                "JOIN product p ON o.product_id = p.product_id " +
                "LEFT JOIN electronics e ON p.product_id = e.product_id " +
                "LEFT JOIN clothing c ON p.product_id = c.product_id " +
                "WHERE u.username = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-15s | %-10s | %-10s | %-12s | %-6s | %-10s | %-8s | %-10s | %-10s | %-20s |\n",
                    "ID", "Product", "Type", "Brand", "Warranty", "Size", "Color", "Qty", "Amount", "Status", "Order Date");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                found = true;
                int orderId = rs.getInt("order_id");
                String product = rs.getString("product_name");
                String type = rs.getString("type");
                String brand = rs.getString("brand") != null ? rs.getString("brand") : "-";
                String warranty = rs.getString("warranty_period") != null ? rs.getString("warranty_period") + " months" : "-";
                String size = rs.getString("size") != null ? rs.getString("size") : "-";
                String color = rs.getString("color") != null ? rs.getString("color") : "-";
                int qty = rs.getInt("quantity");
                double amount = rs.getDouble("total_amt");
                String status = rs.getString("status");
                Timestamp date = rs.getTimestamp("order_date");

                System.out.printf("| %-3d | %-15s | %-10s | %-10s | %-12s | %-6s | %-10s | %-8d | %-10.2f | %-10s | %-20s |\n",
                        orderId, product, type, brand, warranty, size, color, qty, amount, status, date);
            }

            if (!found) {
                System.out.println("No orders found for user: " + username);
            }

            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error viewing user's orders: " + e.getMessage());
        }
    }

    public Product getProductById(int productId) {
        String query = "SELECT * FROM product WHERE product_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity_in_stock"),
                        rs.getString("type")
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching product: " + e.getMessage());
        }
        return null;
    }


    @Override
    public void updateProductDetails(int id, String name, String desc, double price, int qty) {
        String query = "UPDATE product SET product_name = ?, description = ?, price = ?, quantity_in_stock = ? WHERE product_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.setInt(5, id); // Use `id`, not `productId`

            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found.");
            }

        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }





}
