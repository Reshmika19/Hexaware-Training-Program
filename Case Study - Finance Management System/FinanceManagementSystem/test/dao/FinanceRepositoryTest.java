package test.dao;

import dao.FinanceRepositoryImpl;
import entity.Expense;
import entity.User;
import exception.ExpenseNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DBConnUtil;

import java.sql.*;

public class FinanceRepositoryTest {

    @Test
    public void testCreateUserAndAddExpenseSuccessfully() throws ExpenseNotFoundException {
        FinanceRepositoryImpl financeRepo = new FinanceRepositoryImpl();
        int userId = -1, expenseId = -1, categoryId = -1;

        try {
            // Test Case 1: Create User
            User testUser = new User();
            testUser.setUsername("TestCaseUser");
            testUser.setEmail("testcaseuser@example.com");
            testUser.setPassword("testpass");

            boolean userCreated = financeRepo.createUser(testUser);
            userId = testUser.getUserId();

            System.out.println("Test Case 1:");
            if (userCreated && userId > 0) {
                System.out.println("User created with ID: " + userId);
            } else {
                System.out.println("User creation failed.");
                return;
            }

            // Test Case 2: Add Expense
            categoryId = createTestCategory("TestCategory");

            Expense testExpense = new Expense();
            testExpense.setUserId(userId);
            testExpense.setCategoryId(categoryId);
            testExpense.setAmount(500.00);
            testExpense.setDate(java.sql.Date.valueOf("2024-04-08"));
            testExpense.setDescription("Project demo lunch");

            Expense createdExpense = financeRepo.addExpense(testExpense);
            System.out.println("\nTest Case 2:");
            if (createdExpense != null && createdExpense.getExpenseId() > 0) {
                expenseId = createdExpense.getExpenseId();
                System.out.println("Expense created with ID: " + expenseId);
            } else {
                System.out.println("Expense creation failed.");
                return;
            }

            // Test Case 3: Search Expense by ID
            System.out.println("\nTest Case 3:");
            Expense fetchedExpense = financeRepo.getExpenseById(expenseId, userId);
            if (fetchedExpense != null) {
                System.out.println("Expense Found:");
                System.out.println("Category id: " + fetchedExpense.getCategoryId());
                System.out.println("Description: " + fetchedExpense.getDescription());
                System.out.println("Amount: " + fetchedExpense.getAmount());
                System.out.println("Date: " + fetchedExpense.getDate());
            } else {
                System.out.println("Expense not found.");
            }

        } finally {
            //Cleanup
            System.out.println("\nCleanup:");
            if (expenseId > 0 && deleteTestExpense(expenseId)) {
                System.out.println("Expense with ID " + expenseId + " deleted.");
            }
            if (categoryId > 0 && deleteTestCategory(categoryId)) {
                System.out.println("Category with ID " + categoryId + " deleted.");
            }
            if (userId > 0 && deleteTestUser(userId)) {
                System.out.println("User with ID " + userId + " deleted.");
            }
        }
    }

    private int createTestCategory(String name) {
        String query = "INSERT INTO expensecategories (category_name) VALUES (?)";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, name);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in createTestCategory: " + e.getMessage());
        }
        return -1;
    }

    private boolean deleteTestExpense(int expenseId) {
        String query = "DELETE FROM expenses WHERE expense_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, expenseId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting test expense: " + e.getMessage());
        }
        return false;
    }

    private boolean deleteTestCategory(int categoryId) {
        String query = "DELETE FROM expensecategories WHERE category_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, categoryId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting test category: " + e.getMessage());
        }
        return false;
    }

    private boolean deleteTestUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection con = DBConnUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting test user: " + e.getMessage());
        }
        return false;
    }

}
