package dao;

import util.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class CourierAdminServiceImp implements ICourierAdminService {

    private final Connection connection;

    public CourierAdminServiceImp() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void createCourierCompany(Scanner scanner) {
        System.out.print("Enter Courier Company Name: ");
        String companyName = scanner.nextLine();

        String insertQuery = "INSERT INTO couriercompany (name) VALUES (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, companyName);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        String generatedId = rs.getString(1);
                        System.out.println("Courier Company created with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println("Failed to create courier company.");
            }
        } catch (SQLException e) {
            System.out.println("Error while creating courier company: " + e.getMessage());
        }
    }

    @Override
    public void createEmployee(Scanner scanner) {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter courier company ID to assign the employee: ");
        int courierCompanyId = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO employee (name, courierCompanyId) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, courierCompanyId);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Employee created with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while creating employee: " + e.getMessage());
        }
    }

    @Override
    public void createLocation(Scanner scanner) {
        System.out.print("Enter location name: ");
        String name = scanner.nextLine();

        System.out.print("Enter courier company ID to assign the location: ");
        int courierCompanyId = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO location (name, courierCompanyId) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, courierCompanyId);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Location created with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while creating location: " + e.getMessage());
        }
    }

    @Override
    public void viewAllUsers() {
        String sql = "SELECT userID, userName, email, contactNumber, address FROM user";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n------------------------------------------------ All Users ---------------------------------------");
            System.out.printf("| %-10s | %-20s | %-25s | %-15s | %-30s |\n",
                    "User ID", "Name", "Email", "Contact", "Address");
            System.out.println("----------------------------------------------------------------------" +
                    "--------------------------------------------");

            while (rs.next()) {
                int userID = rs.getInt("userID");
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String contactNumber = rs.getString("contactNumber");
                String address = rs.getString("address");

                System.out.printf("| %-10d | %-20s | %-25s | %-15s | %-30s |\n",
                        userID, userName, email, contactNumber, address);
            }

            System.out.println("----------------------------------------------------------------------" +
                    "-----------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error while viewing users: " + e.getMessage());
        }
    }

    @Override
    public void viewAllLocations() {
        String sql = "SELECT l.id, l.name, c.name AS companyName " +
                "FROM location l " +
                "INNER JOIN couriercompany c ON l.courierCompanyId = c.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n---------------------------- All Locations ----------------------------");
            System.out.printf("| %-10s | %-25s | %-30s |\n", "ID", "Location Name", "Courier Company");
            System.out.println("-----------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String companyName = rs.getString("companyName");

                System.out.printf("| %-10d | %-25s | %-30s |\n", id, name, companyName);
            }
            System.out.println("-----------------------------------------------------------------------");


        } catch (SQLException e) {
            System.out.println("Error while viewing locations: " + e.getMessage());
        }
    }

    @Override
    public void viewAllEmployees() {
        String sql = "SELECT e.id, e.name, c.name AS companyName " +
                "FROM employee e " +
                "INNER JOIN couriercompany c ON e.courierCompanyId = c.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n----------------------------- All Employees ----------------------------");
            System.out.printf("| %-10s | %-25s | %-30s |\n", "ID", "Employee Name", "Courier Company");
            System.out.println("-----------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String companyName = rs.getString("companyName");

                System.out.printf("| %-10d | %-25s | %-30s |\n", id, name, companyName);
            }

            System.out.println("-----------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error while viewing employees: " + e.getMessage());
        }
    }

    @Override
    public void viewAllCourierCompanies() {
        String sql = "SELECT id, name FROM couriercompany where id <> 10";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n-------------- All Courier Companies ------------------");
            System.out.printf("| %-10s | %-25s |\n", "ID", "Company Name");
            System.out.println("-----------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.printf("| %-10d | %-25s |\n", id, name);
            }
            System.out.println("-----------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error while viewing courier companies: " + e.getMessage());
        }
    }

    @Override
    public void viewAllOrders() {
        String sql = "SELECT c.courierID, c.senderName, c.receiverName, c.weight, c.status, " +
                "c.trackingNumber, c.deliveryDate, e.name AS employeeName, " +
                "l.name AS locationName, cc.name AS companyName " +
                "FROM courier c " +
                "INNER JOIN employee e ON c.employeeId = e.id " +
                "INNER JOIN location l ON c.locationId = l.id " +
                "INNER JOIN couriercompany cc ON c.courierCompany = cc.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n-------------------------------------------------- All Orders ----------------------------------------------------------------------------------");
            System.out.printf("| %-12s | %-15s | %-15s | %-8s | %-13s | %-17s | %-15s | %-25s | %-15s |\n",
                    "Courier ID", "Sender Name", "Receiver Name", "Weight", "Status", "Tracking No", "Delivery Date", "Employee", "Courier Company");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                long courierID = rs.getLong("courierID");
                String senderName = rs.getString("senderName");
                String receiverName = rs.getString("receiverName");
                double weight = rs.getDouble("weight");
                String status = rs.getString("status");
                String trackingNumber = rs.getString("trackingNumber");
                Date deliveryDate = rs.getDate("deliveryDate");
                String employeeName = rs.getString("employeeName");
                String locationName = rs.getString("locationName");
                String companyName = rs.getString("companyName");

                System.out.printf("| %-12d | %-15s | %-15s | %-8.1f | %-13s | %-17s | %-15s | %-25s | %-15s |\n",
                        courierID, senderName, receiverName, weight, status, trackingNumber, deliveryDate, employeeName, companyName);
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error while viewing orders: " + e.getMessage());
        }
    }

    @Override
    public void viewAllPayments() {
        String sql = "SELECT p.paymentID, p.courierID, p.amount, p.paymentDate " +
                "FROM payment p " +
                "INNER JOIN courier c ON p.courierID = c.courierID";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n----------------- All Payments ----------------------------");
            System.out.printf("| %-12s | %-12s | %-8s | %-15s |\n",
                    "Payment ID", "Courier ID", "Amount", "Payment Date");
            System.out.println("-------------------------------------------------------------");

            while (rs.next()) {
                long paymentID = rs.getLong("paymentID");
                long courierID = rs.getLong("courierID");
                double amount = rs.getDouble("amount");
                Date paymentDate = rs.getDate("paymentDate");

                System.out.printf("| %-12d | %-12d | ₹%-8.2f | %-15s |\n",
                        paymentID, courierID, amount, paymentDate);
            }

            System.out.println("-------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error while viewing payments: " + e.getMessage());
        }
    }

    @Override
    public boolean updateCourierStatus(Scanner scanner, String courierId) {
        String sqlCheckCourier = "SELECT status FROM courier WHERE courierID = ?";
        String sqlUpdateStatus = "UPDATE courier SET status = ? WHERE courierID = ?";

        try {
            try (PreparedStatement stmtCheck = connection.prepareStatement(sqlCheckCourier)) {
                stmtCheck.setString(1, courierId);
                ResultSet rs = stmtCheck.executeQuery();

                if (!rs.next()) {
                    System.out.println("No courier found with ID: " + courierId);
                    return false;
                }

                String currentStatus = rs.getString("status");

                if ("Delivered".equalsIgnoreCase(currentStatus)) {
                    System.out.println("This courier has already been delivered.");
                    return false;
                }

                System.out.print("Enter the new status for courier " + courierId + ": ");
                String newStatus = scanner.nextLine().trim();

                try (PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdateStatus)) {
                    stmtUpdate.setString(1, newStatus);
                    stmtUpdate.setString(2, courierId);
                    int rowsUpdated = stmtUpdate.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Status updated successfully for courier ID: " + courierId);
                        return true;
                    } else {
                        System.out.println("Failed to update status.");
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error updating courier status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void assignEmployeeLocationCompanyToCourier(Scanner scanner) {
        System.out.print("Enter the Courier ID to assign: ");
        String courierIdInput = scanner.nextLine().trim();

        String selectSql = "SELECT courierID, receiverAddress, courierCompany, locationId, employeeId FROM courier WHERE courierID = ?";
        String updateSql = "UPDATE courier SET courierCompany = ?, locationId = ?, employeeId = ? WHERE courierID = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
            selectStmt.setString(1, courierIdInput);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No courier found with the provided ID.");
                return;
            }

            int currentCompany = rs.getInt("courierCompany");
            int currentLocation = rs.getInt("locationId");
            int currentEmployee = rs.getInt("employeeId");

            if (currentCompany != 10 || currentLocation != 10 || currentEmployee != 10) {
                System.out.println("Courier already assigned with company/location/employee.");
                return;
            }

            String receiverAddress = rs.getString("receiverAddress");
            System.out.println("Receiver Address: " + receiverAddress);

            viewAllCourierCompanies();

            System.out.print("Enter the Courier Company ID to assign: ");
            int selectedCompanyId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("\nAvailable Locations for selected company:");
            String locSql = "SELECT id, name FROM location WHERE courierCompanyId = ?";
            try (PreparedStatement locStmt = connection.prepareStatement(locSql)) {
                locStmt.setInt(1, selectedCompanyId);
                ResultSet locRs = locStmt.executeQuery();

                System.out.printf("%-10s %-25s%n", "ID", "Location Name");
                System.out.println("-----------------------------------");
                while (locRs.next()) {
                    System.out.printf("%-10d %-25s%n", locRs.getInt("id"), locRs.getString("name"));
                }
            }

            System.out.print("Enter Location ID to assign: ");
            int locationId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("\n👤 Available Employees for selected company:");
            String empSql = "SELECT id, name FROM employee WHERE courierCompanyId = ?";
            try (PreparedStatement empStmt = connection.prepareStatement(empSql)) {
                empStmt.setInt(1, selectedCompanyId);
                ResultSet empRs = empStmt.executeQuery();

                System.out.printf("%-10s %-25s%n", "ID", "Employee Name");
                System.out.println("-----------------------------------");
                while (empRs.next()) {
                    System.out.printf("%-10d %-25s%n", empRs.getInt("id"), empRs.getString("name"));
                }
            }

            System.out.print("Enter Employee ID to assign: ");
            int employeeId = Integer.parseInt(scanner.nextLine().trim());

            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setInt(1, selectedCompanyId);
                updateStmt.setInt(2, locationId);
                updateStmt.setInt(3, employeeId);
                updateStmt.setString(4, courierIdInput);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    String confirmSql = "SELECT c.name AS companyName, e.name AS employeeName " +
                            "FROM couriercompany c, employee e WHERE c.id = ? AND e.id = ?";
                    try (PreparedStatement confirmStmt = connection.prepareStatement(confirmSql)) {
                        confirmStmt.setInt(1, selectedCompanyId);
                        confirmStmt.setInt(2, employeeId);
                        ResultSet confirmRs = confirmStmt.executeQuery();

                        if (confirmRs.next()) {
                            String companyName = confirmRs.getString("companyName");
                            String employeeName = confirmRs.getString("employeeName");

                            System.out.println("\nCourier assigned successfully!");
                            System.out.println("Assigned Employee : " + employeeName);
                            System.out.println("Courier Company   : " + companyName);
                        }
                    }
                } else {
                    System.out.println("Failed to update the courier.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    @Override
    public void viewCourierCompanyReport(Scanner scanner) {
        viewAllCourierCompanies();
        System.out.print("Enter Courier Company ID to View Report: ");
        int companyId = scanner.nextInt();
        scanner.nextLine();

        String companyName = null;
        String getCompanyQuery = "SELECT name FROM couriercompany WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(getCompanyQuery)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                companyName = rs.getString("name");
                System.out.println("\nCourier Company: " + companyName);
            } else {
                System.out.println("No such Courier Company ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String statsQuery = "SELECT COUNT(*) AS total, COALESCE(SUM(amount), 0) AS revenue FROM courier WHERE courierCompany = ?";
        try (PreparedStatement ps = connection.prepareStatement(statsQuery)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                double revenue = rs.getDouble("revenue");

                System.out.println("\nReport Summary:");
                System.out.println("Total Couriers Handled: " + total);
                System.out.println("Total Revenue Generated: ₹" + revenue);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching summary: " + e.getMessage());
        }

        String courierDetails = "SELECT userId, senderName, receiverName, receiverAddress, amount, status, trackingNumber, deliveryDate, employeeId " +
                "FROM courier WHERE courierCompany = ?";

        System.out.println("\nCourier List for Company: " + companyName);
        System.out.printf("%-6s %-15s %-15s %-25s %-10s %-10s %-18s %-12s %-10s\n",
                "UserID", "Sender", "Receiver", "Receiver Address", "Amount", "Status", "Tracking No", "Delivery", "Emp ID");

        try (PreparedStatement ps = connection.prepareStatement(courierDetails)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("userId");
                String sender = rs.getString("senderName");
                String receiver = rs.getString("receiverName");
                String address = rs.getString("receiverAddress");
                double amt = rs.getDouble("amount");
                String status = rs.getString("status");
                String tracking = rs.getString("trackingNumber");
                Date delivery = rs.getDate("deliveryDate");
                int empId = rs.getInt("employeeId");

                System.out.printf("%-6d %-15s %-15s %-25s ₹%-9.2f %-10s %-18s %-12s %-10d\n",
                        userId, sender, receiver, address, amt, status, tracking,
                        (delivery != null ? delivery.toString() : "N/A"), empId);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching courier details: " + e.getMessage());
        }

        String empLocQuery = "SELECT e.id AS empId, e.name AS empName, l.name AS locName " +
                "FROM employee e " +
                "JOIN location l ON e.courierCompanyId = l.courierCompanyId " +
                "WHERE e.courierCompanyId = ?";

        System.out.println("\n👥 Employees and Locations in " + companyName);
        System.out.printf("%-10s %-20s %-20s\n", "Emp ID", "Employee Name", "Location");

        try (PreparedStatement ps = connection.prepareStatement(empLocQuery)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int empId = rs.getInt("empId");
                String empName = rs.getString("empName");
                String locName = rs.getString("locName");

                System.out.printf("%-10d %-20s %-20s\n", empId, empName, locName);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employee/location details: " + e.getMessage());
        }
    }

    @Override
    public String deleteEmployee(long empId) {
        String checkSQL = "SELECT COUNT(*) FROM courier WHERE employeeId = ?";
        String deleteSQL = "DELETE FROM employee WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
            checkStmt.setLong(1, empId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "Cannot delete. Employee is assigned to one or more couriers.";
            }
        } catch (SQLException e) {
            return "Error checking employee assignment: " + e.getMessage();
        }

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
            deleteStmt.setLong(1, empId);
            int rows = deleteStmt.executeUpdate();

            if (rows > 0) {
                return "Employee deleted successfully.";
            } else {
                return "Employee not found.";
            }
        } catch (SQLException e) {
            return "Error deleting employee: " + e.getMessage();
        }
    }

    @Override
    public String deleteLocation(long locationId) {
        String checkSQL = "SELECT COUNT(*) FROM courier WHERE locationId = ?";
        String deleteSQL = "DELETE FROM location WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
            checkStmt.setLong(1, locationId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "Cannot delete. Location is assigned to one or more couriers.";
            }
        } catch (SQLException e) {
            return "Error checking location assignment: " + e.getMessage();
        }

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
            deleteStmt.setLong(1, locationId);
            int rows = deleteStmt.executeUpdate();

            if (rows > 0) {
                return "Location deleted successfully.";
            } else {
                return "Location not found.";
            }
        } catch (SQLException e) {
            return "Error deleting location: " + e.getMessage();
        }
    }

    @Override
    public String deleteCourierCompanyCascade(long companyId) {
        String selectCouriersSQL = "SELECT courierID, employeeId, locationId, userId FROM courier WHERE courierCompany = ?";
        String deletePaymentSQL = "DELETE FROM payment WHERE courierID = ?";
        String deleteCourierSQL = "DELETE FROM courier WHERE courierCompany = ?";
        String deleteEmployeeSQL = "DELETE FROM employee WHERE courierCompanyId = ?";
        String deleteLocationSQL = "DELETE FROM location WHERE courierCompanyId = ?";
        String deleteCompanySQL = "DELETE FROM couriercompany WHERE id = ?";

        try {
            try (PreparedStatement selectStmt = connection.prepareStatement(selectCouriersSQL)) {
                selectStmt.setLong(1, companyId);
                ResultSet rs = selectStmt.executeQuery();

                boolean hasData = false;
                System.out.println("\nThe following couriers are associated with this Courier Company:");
                while (rs.next()) {
                    hasData = true;
                    System.out.println("  • CourierID: " + rs.getLong("courierID") +
                            ", EmployeeID: " + rs.getLong("employeeId") +
                            ", LocationID: " + rs.getLong("locationId") +
                            ", UserID: " + rs.getLong("userId"));
                }

                if (!hasData) {
                    System.out.println("No couriers found for this company, but will still delete employees, locations, and the company.");
                }
            }

            System.out.println("\nWARNING: Deleting this Courier Company will also delete:");
            System.out.println("   • All its Couriers");
            System.out.println("   • All Payments related to its Couriers");
            System.out.println("   • All Employees of this company");
            System.out.println("   • All Locations of this company");
            System.out.println("Are you sure you want to proceed? Type 'yes' to confirm:");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (!input.equalsIgnoreCase("yes")) {
                return "Deletion cancelled by user.";
            }

            connection.setAutoCommit(false);

            try (PreparedStatement selectStmt = connection.prepareStatement(selectCouriersSQL)) {
                selectStmt.setLong(1, companyId);
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    long courierId = rs.getLong("courierID");

                    try (PreparedStatement deletePaymentStmt = connection.prepareStatement(deletePaymentSQL)) {
                        deletePaymentStmt.setLong(1, courierId);
                        deletePaymentStmt.executeUpdate();
                    }
                }
            }

            try (PreparedStatement courierStmt = connection.prepareStatement(deleteCourierSQL)) {
                courierStmt.setLong(1, companyId);
                courierStmt.executeUpdate();
            }

            try (PreparedStatement empStmt = connection.prepareStatement(deleteEmployeeSQL)) {
                empStmt.setLong(1, companyId);
                empStmt.executeUpdate();
            }

            try (PreparedStatement locStmt = connection.prepareStatement(deleteLocationSQL)) {
                locStmt.setLong(1, companyId);
                locStmt.executeUpdate();
            }

            try (PreparedStatement companyStmt = connection.prepareStatement(deleteCompanySQL)) {
                companyStmt.setLong(1, companyId);
                int rows = companyStmt.executeUpdate();

                if (rows > 0) {
                    connection.commit();
                    return "Courier Company and all associated data deleted successfully.";
                } else {
                    connection.rollback();
                    return "Courier Company not found.";
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                return "Rollback failed: " + rollbackEx.getMessage();
            }
            return "Error during deletion: " + e.getMessage();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }


}
