package test.dao;

import dao.FinanceRepositoryImpl;
import dao.AdminRepositoryImpl;
import exception.ExpenseNotFoundException;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionHandlingTest {

    @Test
    public void testGetUserById_UserNotFound_ShouldThrowException() {
        AdminRepositoryImpl adminRepo = new AdminRepositoryImpl();
        int invalidUserId = -999;

        try {
            Assertions.assertThrows(UserNotFoundException.class, () -> {
                adminRepo.getUserById(invalidUserId);
            });
            System.out.println("Test Case 1 Passed: UserNotFoundException thrown as expected.");
        } catch (AssertionError e) {
            System.out.println("Test Case 1 Failed: UserNotFoundException was NOT thrown.");
            throw e;
        }
    }

    @Test
    public void testGetExpenseById_ExpenseNotFound_ShouldThrowException() {
        FinanceRepositoryImpl financeRepo = new FinanceRepositoryImpl();
        int invalidExpenseId = -999;
        int userId = -999;

        try {
            Assertions.assertThrows(ExpenseNotFoundException.class, () -> {
                financeRepo.getExpenseById(invalidExpenseId, userId);
            });
            System.out.println("Test Case 2 Passed: ExpenseNotFoundException thrown as expected.");
        } catch (AssertionError e) {
            System.out.println("Test Case 2 Failed: ExpenseNotFoundException was NOT thrown.");
            throw e;
        }
    }
}
