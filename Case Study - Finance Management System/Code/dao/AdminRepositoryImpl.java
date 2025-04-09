package dao;

import entity.User;
import exception.UserNotFoundException;
import util.DBConnUtil;

import java.sql.*;

public class AdminRepositoryImpl implements IAdminRepository {

    @Override
    public boolean adminLogin(String adminName, String password) {
        String query = "SELECT * FROM admin WHERE admin_name = ? AND password = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, adminName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error in adminLogin: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void viewSuggestions() {
        String query = "SELECT suggestion_id, user_id, suggestion_text FROM suggestions";

        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-15s %-10s %s%n", "Suggestion ID", "User ID", "Suggestion");

            while (rs.next()) {
                System.out.printf("%-15d %-10d %s%n",
                        rs.getInt("suggestion_id"),
                        rs.getInt("user_id"),
                        rs.getString("suggestion_text"));
            }

        } catch (SQLException e) {
            System.out.println("Error in viewSuggestions: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteSuggestion(int suggestionId) {
        String query = "DELETE FROM suggestions WHERE suggestion_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, suggestionId);
            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error in deleteSuggestion: " + e.getMessage());
        }

        return false;
    }


    @Override
    public void addExpenseCategory(String categoryName) {
        String query = "INSERT INTO expensecategories (category_name) VALUES (?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoryName);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Category added with ID: " + rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in addExpenseCategory: " + e.getMessage());
        }
    }
    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error in getUserById: " + e.getMessage());
        }

        throw new UserNotFoundException("User with ID " + userId + " not found.");
    }

    @Override
    public void viewAllCategories() {
        String query = "SELECT * FROM expensecategories order by category_id";
        try (Connection con = DBConnUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("All Expense Categories:");
            System.out.printf("%-15s %-30s\n", "Category ID", "Category Name");
            while (rs.next()) {
                System.out.printf("%-15d %-30s\n", rs.getInt("category_id"), rs.getString("category_name"));
            }

        } catch (SQLException e) {
            System.out.println("Error in viewAllCategories: " + e.getMessage());
        }
    }

    @Override
    public void viewAllUsers() {
        String query = "SELECT user_id, username, email FROM users";
        try (Connection con = DBConnUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("All Users:");
            System.out.printf("%-10s %-20s %-30s\n", "User ID", "Username", "Email");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-30s\n",
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error in viewAllUsers: " + e.getMessage());
        }
    }

    @Override
    public String getCategoryNameById(int categoryId) {
        String query = "SELECT category_name FROM expensecategories WHERE category_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, categoryId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("category_name");
            }

        } catch (SQLException e) {
            System.out.println("Error in getCategoryNameById: " + e.getMessage());
        }
        return "Unknown";
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        String query = "DELETE FROM expensecategories WHERE category_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, categoryId);
            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error in deleteCategory: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean isCategoryUsed(int categoryId) {
        String query = "SELECT COUNT(*) FROM expenses WHERE category_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, categoryId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error in isCategoryUsed: " + e.getMessage());
        }

        return false;
    }


}
