package dao;

import entity.Expense;
import entity.Suggestion;
import entity.User;
import exception.ExpenseNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface IFinanceRepository {

    boolean createUser(User user);
    Expense addExpense(Expense expense);
    boolean deleteUserAndExpenses(int userId);
    boolean deleteExpense(int expenseId, int userId);
    List<Expense> getAllExpensesByUserId(int userId);
    boolean updateExpense(Expense expense);

    User loginUser(String email, String password);
    List<Expense> getExpensesByDateRange(int userId, Date fromDate, Date toDate);
    List<Expense> getExpensesByCategory(int userId, int categoryId);
    Expense getExpenseById(int expenseId, int userId) throws ExpenseNotFoundException;
    Map<String, Double> getCategoryWiseReport(int userId, Date start, Date end);
    Map<String, Double> getMonthlySummary(int userId, Date start, Date end);
    boolean addSuggestion(Suggestion suggestion);

}
