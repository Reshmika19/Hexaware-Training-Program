package dao;

import entity.User;
import exception.UserNotFoundException;

public interface IAdminRepository {
    boolean adminLogin(String adminName, String password);
    void viewSuggestions();
    User getUserById(int userId) throws UserNotFoundException;
    boolean deleteSuggestion(int suggestionId);
    void addExpenseCategory(String categoryName);
    boolean isCategoryUsed(int categoryId);
    boolean deleteCategory(int categoryId);
    void viewAllCategories();
    void viewAllUsers();
    String getCategoryNameById(int categoryId);
}
