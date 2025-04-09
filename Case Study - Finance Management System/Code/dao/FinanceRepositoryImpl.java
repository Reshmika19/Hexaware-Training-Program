package dao;

import entity.Expense;
import entity.Suggestion;
import entity.User;
import exception.ExpenseNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinanceRepositoryImpl implements IFinanceRepository {

    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    user.setUserId(userId);
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error in createUser: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Error in loginUser: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Expense addExpense(Expense expense) {
        String query = "INSERT INTO expenses (user_id, category_id, amount, date, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, expense.getUserId());
            pst.setInt(2, expense.getCategoryId());
            pst.setDouble(3, expense.getAmount());
            pst.setDate(4, expense.getDate());
            pst.setString(5, expense.getDescription());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    expense.setExpenseId(rs.getInt(1));
                }
                return expense;
            }

        } catch (SQLException e) {
            System.out.println("Error in addExpense: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Expense> getAllExpensesByUserId(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT e.*, c.category_name FROM expenses e " +
                "JOIN expenseCategories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getDate("date"));
                expense.setDescription(rs.getString("description"));
                expense.setCategoryName(rs.getString("category_name"));

                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllExpensesByUserId: " + e.getMessage());
        }

        return expenses;
    }

    @Override
    public List<Expense> getExpensesByDateRange(int userId, Date fromDate, Date toDate) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT e.*, c.category_name FROM expenses e " +
                "JOIN expenseCategories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = ? AND e.date BETWEEN ? AND ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setDate(2, fromDate);
            pst.setDate(3, toDate);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getDate("date"));
                expense.setDescription(rs.getString("description"));
                expense.setCategoryName(rs.getString("category_name"));
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error in getExpensesByDateRange: " + e.getMessage());
        }

        return expenses;
    }

    @Override
    public List<Expense> getExpensesByCategory(int userId, int categoryId) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT e.*, c.category_name FROM expenses e " +
                "JOIN expenseCategories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = ? AND e.category_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setInt(2, categoryId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getDate("date"));
                expense.setDescription(rs.getString("description"));
                expense.setCategoryName(rs.getString("category_name"));
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error in getExpensesByCategory: " + e.getMessage());
        }
        return expenses;
    }

    @Override
    public Expense getExpenseById(int expenseId, int userId) throws ExpenseNotFoundException {
        String query = "SELECT * FROM expenses WHERE expense_id = ? AND user_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, expenseId);
            pst.setInt(2, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Expense e = new Expense();
                e.setExpenseId(rs.getInt("expense_id"));
                e.setUserId(rs.getInt("user_id"));
                e.setCategoryId(rs.getInt("category_id"));
                e.setAmount(rs.getDouble("amount"));
                e.setDate(rs.getDate("date"));
                e.setDescription(rs.getString("description"));
                return e;
            } else {
                throw new ExpenseNotFoundException("Expense with ID " + expenseId + " not found for user ID " + userId);
            }

        } catch (SQLException e) {
            System.out.println("Error in getExpenseById: " + e.getMessage());
            throw new RuntimeException("Database error occurred while fetching expense.");
        }
    }

    @Override
    public boolean updateExpense(Expense expense) {
        String query = "UPDATE expenses SET category_id = ?, amount = ?, date = ?, description = ? WHERE expense_id = ? AND user_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, expense.getCategoryId());
            pst.setDouble(2, expense.getAmount());
            pst.setDate(3, expense.getDate());
            pst.setString(4, expense.getDescription());
            pst.setInt(5, expense.getExpenseId());
            pst.setInt(6, expense.getUserId());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error in updateExpense: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteExpense(int expenseId, int userId) {
        String query = "DELETE FROM expenses WHERE expense_id = ? AND user_id = ?";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, expenseId);
            pst.setInt(2, userId);

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error in deleteExpense: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Map<String, Double> getCategoryWiseReport(int userId, Date start, Date end) {
        Map<String, Double> report = new LinkedHashMap<>();
        String query = "SELECT c.category_name, SUM(e.amount) AS total_amount " +
                "FROM expenses e JOIN expensecategories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = ? AND e.date BETWEEN ? AND ? " +
                "GROUP BY c.category_name ORDER BY total_amount DESC";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setDate(2, start);
            pst.setDate(3, end);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                double total = rs.getDouble("total_amount");
                report.put(categoryName, total);
            }

        } catch (SQLException e) {
            System.out.println("Error in getCategoryWiseReport: " + e.getMessage());
        }

        return report;
    }

    @Override
    public Map<String, Double> getMonthlySummary(int userId, Date start, Date end) {
        Map<String, Double> summary = new LinkedHashMap<>();
        String query = "SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) AS total " +
                "FROM expenses WHERE user_id = ? AND date BETWEEN ? AND ? " +
                "GROUP BY month ORDER BY month";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setDate(2, start);
            pst.setDate(3, end);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                double total = rs.getDouble("total");
                summary.put(month, total);
            }

        } catch (SQLException e) {
            System.out.println("Error in getMonthlySummary: " + e.getMessage());
        }

        return summary;
    }

    @Override
    public boolean addSuggestion(Suggestion suggestion) {
        String query = "INSERT INTO suggestions (user_id, suggestion_text) VALUES (?, ?)";

        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, suggestion.getUserId());
            pst.setString(2, suggestion.getSuggestionText());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error in addSuggestion: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUserAndExpenses(int userId) {
        String deleteExpenses = "DELETE FROM expenses WHERE user_id = ?";
        String deleteUser = "DELETE FROM users WHERE user_id = ?";

        try (Connection con = DBConnUtil.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement pst1 = con.prepareStatement(deleteExpenses);
                 PreparedStatement pst2 = con.prepareStatement(deleteUser)) {

                pst1.setInt(1, userId);
                pst1.executeUpdate();

                pst2.setInt(1, userId);
                int rows = pst2.executeUpdate();

                if (rows > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error during deletion: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Connection error in deleteUserAndExpenses: " + e.getMessage());
        }

        return false;
    }



}
