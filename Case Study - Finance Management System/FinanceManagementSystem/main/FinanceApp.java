package main;

import dao.FinanceRepositoryImpl;
import dao.IFinanceRepository;
import dao.AdminRepositoryImpl;
import dao.IAdminRepository;
import entity.Expense;
import entity.Suggestion;
import entity.User;
import exception.ExpenseNotFoundException;
import exception.UserNotFoundException;

import java.sql.Date;

import java.util.*;

public class FinanceApp {

    static Scanner sc = new Scanner(System.in);
    static IFinanceRepository repo = new FinanceRepositoryImpl();
    static IAdminRepository adminRepo = new AdminRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                         Finance Management System");
        System.out.println("                        \"Manage your money wisely!\"");
        System.out.println("----------------------------------------------------------------------------");


        while (true) {
            System.out.println("\n1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int mainChoice = sc.nextInt();

            switch (mainChoice) {
                case 1:
                    handleUser();
                    break;
                case 2:
                    handleAdmin(sc);
                    break;
                case 3:
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("Thank you for using Finance Management System!");
                    System.out.println("----------------------------------------------------------------------------");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUser() {
        while (true) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("\n1. Login");
            System.out.println("2. Create User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int userChoice = sc.nextInt();
            sc.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.println("-------------------------------- LOGIN -----------------------------------\n");
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    User loggedInUser = repo.loginUser(email, password);

                    if (loggedInUser != null) {
                        System.out.println("\n----------------------------------------------------------------------------");
                        System.out.println("Welcome " + loggedInUser.getUsername() + "!");
                        userActions(loggedInUser);
                    } else {
                        System.out.println("Invalid credentials. Try again.");
                    }
                    System.out.println("-------------------------------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("--------------------------- CREATE USER --------------------------------\n");
                    System.out.print("Username: ");
                    String username = sc.nextLine();
                    System.out.print("Email: ");
                    String newEmail = sc.nextLine();
                    System.out.print("Password: ");
                    String newPassword = sc.nextLine();

                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setEmail(newEmail);
                    newUser.setPassword(newPassword);

                    if (repo.createUser(newUser)) {
                        System.out.println("Account created successfully! Please login to continue.");
                        System.out.println(newUser);
                        System.out.println("----------------------------------------------------------------------------");
                    } else {
                        System.out.println("Account creation failed. Try again.");
                        System.out.println("----------------------------------------------------------------------------");
                    }
                    break;

                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userActions(User user) {
        while (true) {
            System.out.println("\n------------------------------- USER MENU ----------------------------------");
            System.out.println("1. Create Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Filter Expenses");
            System.out.println("4. Update Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Generate Report");
            System.out.println("7. Suggestion");
            System.out.println("8. Delete Account");
            System.out.println("9. Logout");

            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("-------------------------- Add New Expense ------------------------------");
                    adminRepo.viewAllCategories();

                    System.out.print("Enter Category ID: ");
                    int categoryId = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    System.out.print("Enter Date (yyyy-mm-dd): ");
                    String dateStr = sc.next();
                    Date date = Date.valueOf(dateStr);

                    sc.nextLine();
                    System.out.print("Enter Description/Note: ");
                    String note = sc.nextLine();

                    Expense expense = new Expense(user.getUserId(), categoryId, amount, date, note);
                    Expense savedExpense = repo.addExpense(expense);

                    if (savedExpense != null) {
                        System.out.println("\nExpense Added!");
                        System.out.println("Expense ID: " + savedExpense.getExpenseId());
                        System.out.println("Amount: " + savedExpense.getAmount());
                        System.out.println("Description: " + savedExpense.getDescription());
                    } else {
                        System.out.println("Failed to add expense.");
                    }
                    System.out.println("----------------------------------------------------------------------------");
                    break;

                case 2:
                    System.out.println("-------------------------- View All Expenses -------------------------------\n");
                    viewExpense(user);
                    System.out.println("----------------------------------------------------------------------------");
                    break;

                case 3:
                    System.out.println("-------------------------- Filter Expenses -------------------------------");
                    System.out.println("1. Filter by Date");
                    System.out.println("2. Filter by Category");
                    System.out.print("Enter choice: ");
                    int filterChoice = sc.nextInt();

                    switch (filterChoice) {
                        case 1:
                            System.out.print("Enter From Date (yyyy-mm-dd): ");
                            Date fromDate = Date.valueOf(sc.next());

                            System.out.print("Enter To Date (yyyy-mm-dd): ");
                            Date toDate = Date.valueOf(sc.next());

                            List<Expense> dateFiltered = repo.getExpensesByDateRange(user.getUserId(), fromDate, toDate);

                            if (dateFiltered.isEmpty()) {
                                System.out.println("No expenses found in this date range.");
                            } else {
                                System.out.printf("%-12s %-15s %-10s %-15s %-25s\n", "Expense ID", "Category", "Amount", "Date", "Description");
                                System.out.println("---------------------------------------------------------------------------");
                                for (Expense exp : dateFiltered) {
                                    System.out.printf("%-12d %-15s %-10.2f %-15s %-25s\n",
                                            exp.getExpenseId(), exp.getCategoryName(), exp.getAmount(),
                                            exp.getDate().toString(), exp.getDescription());
                                }
                            }
                            System.out.println("----------------------------------------------------------------------------");
                            break;

                        case 2:
                            adminRepo.viewAllCategories();

                            System.out.print("Enter Category ID to filter: ");
                            int catId = sc.nextInt();

                            List<Expense> categoryFiltered = repo.getExpensesByCategory(user.getUserId(), catId);

                            if (categoryFiltered.isEmpty()) {
                                System.out.println("No expenses found in this category.");
                            } else {
                                System.out.printf("%-12s %-15s %-10s %-15s %-25s\n", "Expense ID", "Category", "Amount", "Date", "Description");
                                System.out.println("---------------------------------------------------------------------------");
                                for (Expense exp : categoryFiltered) {
                                    System.out.printf("%-12d %-15s %-10.2f %-15s %-25s\n",
                                            exp.getExpenseId(), exp.getCategoryName(), exp.getAmount(),
                                            exp.getDate().toString(), exp.getDescription());
                                }
                            }
                            System.out.println("----------------------------------------------------------------------------");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case 4:
                    System.out.println("-------------------------- Update Expense -------------------------------\n");
                    System.out.print("Enter Expense ID to update: ");
                    int expenseId = sc.nextInt();
                    sc.nextLine();

                    try {
                        Expense existing = repo.getExpenseById(expenseId, user.getUserId());

                        adminRepo.viewAllCategories();

                        System.out.print("Enter new Category ID (-1 to skip): ");
                        int catId = sc.nextInt();
                        sc.nextLine();
                        if (catId != -1) {
                            existing.setCategoryId(catId);
                        }

                        System.out.print("Enter new Amount (-1 to skip): ");
                        double newAmt = sc.nextDouble();
                        sc.nextLine();
                        if (newAmt != -1) {
                            existing.setAmount(newAmt);
                        }

                        System.out.print("Enter new Date (yyyy-mm-dd or 'na' to skip): ");
                        String newDateStr = sc.next();
                        if (!newDateStr.equalsIgnoreCase("na")) {
                            try {
                                existing.setDate(Date.valueOf(newDateStr));
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Skipping date update.");
                            }
                        }
                        sc.nextLine();

                        System.out.print("Enter new Description ('na' to skip): ");
                        String newDesc = sc.nextLine();
                        if (!newDesc.equalsIgnoreCase("na")) {
                            existing.setDescription(newDesc);
                        }

                        boolean isUpdated = repo.updateExpense(existing);
                        if (isUpdated) {
                            Expense updatedExpense = repo.getExpenseById(expenseId, user.getUserId());
                            String categoryName = adminRepo.getCategoryNameById(updatedExpense.getCategoryId());

                            System.out.println("Expense updated successfully!");
                            System.out.println("ExpenseId : " + updatedExpense.getExpenseId());
                            System.out.println("Category : " + categoryName + " (ID: " + updatedExpense.getCategoryId() + ")");
                            System.out.println("Amount : " + updatedExpense.getAmount());
                            System.out.println("Date : " + updatedExpense.getDate());
                            System.out.println("Description : '" + updatedExpense.getDescription() + "'");
                        } else {
                            System.out.println("Failed to update expense.");
                        }

                    } catch (ExpenseNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("----------------------------------------------------------------------------");
                    break;


                case 5:
                    System.out.println("-------------------------- Delete Expense -------------------------------\n");
                    repo.getAllExpensesByUserId(user.getUserId());

                    System.out.print("Enter Expense ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();

                    try {
                        Expense expToDelete = repo.getExpenseById(deleteId, user.getUserId());

                        System.out.print("Are you sure you want to delete this expense? (yes/no): ");
                        String confirm = sc.nextLine();

                        if (confirm.equalsIgnoreCase("yes")) {
                            boolean deleted = repo.deleteExpense(deleteId, user.getUserId());
                            if (deleted) {
                                System.out.println("Expense deleted successfully!");
                            } else {
                                System.out.println("Failed to delete expense.");
                            }
                        } else {
                            System.out.println("Deletion cancelled.");
                        }

                    } catch (ExpenseNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("----------------------------------------------------------------------------");
                    break;

                case 6:
                    System.out.println("-------------------------- Generate Expense Report -----------------------\n");
                    System.out.println("1. Report by Date Range");
                    System.out.println("2. Report by Category");
                    System.out.println("3. Report by Monthly Summary");
                    System.out.println("4. Back");

                    System.out.print("Choose an option: ");
                    int reportOption = sc.nextInt();
                    sc.nextLine();

                    switch (reportOption) {
                        case 1:
                            System.out.print("Enter start date (yyyy-mm-dd): ");
                            String startStr = sc.nextLine();

                            System.out.print("Enter end date (yyyy-mm-dd): ");
                            String endStr = sc.nextLine();

                            try {
                                Date start = Date.valueOf(startStr);
                                Date end = Date.valueOf(endStr);

                                List<Expense> rangeList = repo.getExpensesByDateRange(user.getUserId(), start, end);

                                if (rangeList.isEmpty()) {
                                    System.out.println("No expenses found in this date range.");
                                } else {
                                    System.out.println("\nExpenses from " + start + " to " + end + ":");
                                    for (Expense e : rangeList) {
                                        System.out.println(e);
                                    }
                                }

                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please enter in yyyy-mm-dd.");
                            }
                            System.out.println("----------------------------------------------------------------------------");

                            break;
                        case 2:
                            System.out.print("Enter start date (yyyy-mm-dd): ");
                            startStr = sc.nextLine();

                            System.out.print("Enter end date (yyyy-mm-dd): ");
                            endStr = sc.nextLine();

                            try {
                                Date start = Date.valueOf(startStr);
                                Date end = Date.valueOf(endStr);

                                Map<String, Double> report = repo.getCategoryWiseReport(user.getUserId(), start, end);

                                if (report.isEmpty()) {
                                    System.out.println("No expenses found for this date range.");
                                } else {
                                    System.out.println("\nCategory-wise Expense Report:");
                                    System.out.printf("%-30s %-15s\n", "Category", "Total");
                                    System.out.println("----------------------------------------------------");

                                    for (Map.Entry<String, Double> entry : report.entrySet()) {
                                        System.out.printf("%-30s ₹%-15.2f\n", entry.getKey(), entry.getValue());
                                    }

                                }

                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please enter in yyyy-mm-dd.");
                            }
                            System.out.println("----------------------------------------------------------------------------");

                            break;
                        case 3:
                            System.out.print("Enter start date (yyyy-mm-dd): ");
                            startStr = sc.nextLine();

                            System.out.print("Enter end date (yyyy-mm-dd): ");
                            endStr = sc.nextLine();

                            try {
                                Date start = Date.valueOf(startStr);
                                Date end = Date.valueOf(endStr);

                                Map<String, Double> summary = repo.getMonthlySummary(user.getUserId(), start, end);

                                if (summary.isEmpty()) {
                                    System.out.println("No expenses found for this range.");
                                } else {
                                    System.out.println("\nMonthly Expense Summary:");
                                    System.out.printf("%-20s %-15s\n", "Month", "Total");
                                    System.out.println("------------------------------------------------");

                                    for (Map.Entry<String, Double> entry : summary.entrySet()) {
                                        System.out.printf("%-20s ₹%-15.2f\n", entry.getKey(), entry.getValue());
                                    }

                                }

                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please enter in yyyy-mm-dd.");
                            }
                            System.out.println("----------------------------------------------------------------------------");

                            break;
                        case 4:
                            System.out.println("Returning to main menu...");
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                    break;

                case 7:
                    sc.nextLine();
                    System.out.println("-------------------------- Write a Suggestion -----------------------\n");
                    System.out.print("Enter your suggestion: ");
                    String suggestionText = sc.nextLine();

                    Suggestion suggestion = new Suggestion();
                    suggestion.setUserId(user.getUserId());
                    suggestion.setSuggestionText(suggestionText);

                    boolean added = repo.addSuggestion(suggestion);
                    if (added) {
                        System.out.println("Thank you! Your suggestion has been submitted.");
                    } else {
                        System.out.println("Failed to submit suggestion. Please try again.");
                    }
                    System.out.println("----------------------------------------------------------------------------");
                    break;
                case 8:
                    sc.nextLine();
                    System.out.println("\n--- Delete Account ---");
                    System.out.print("Are you sure you want to delete your account? (yes/no): ");
                    String confirmation = sc.nextLine();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        System.out.print("Please enter your password to confirm: ");
                        String passwordInput = sc.nextLine();

                        if (user.getPassword().equals(passwordInput)) {
                            boolean deleted = repo.deleteUserAndExpenses(user.getUserId());
                            if (deleted) {
                                System.out.println("Your account and all related data have been deleted. Goodbye!");
                                System.exit(0);
                            } else {
                                System.out.println("Failed to delete account. Please try again later.");
                            }
                        } else {
                            System.out.println("Incorrect password. Account deletion cancelled.");
                        }
                    } else {
                        System.out.println("Account deletion cancelled.");
                    }
                    System.out.println("----------------------------------------------------------------------------");
                    break;

                case 9:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    System.out.println("----------------------------------------------------------------------------");
            }
        }
    }

    private static void handleAdmin(Scanner sc) {
        sc.nextLine();
        System.out.println("\n------------------------------------ ADMIN ---------------------------------");

        System.out.print("Enter admin name: ");
        String adminName = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (adminRepo.adminLogin(adminName, password)) {
            System.out.println("Admin login successful!");
            System.out.println("----------------------------------------------------------------------------");
            adminActions(sc);

        }
        else {
            System.out.println("Invalid admin credentials!");
            System.out.println("----------------------------------------------------------------------------");
        }
    }

    private static void adminActions(Scanner sc) {
        while (true) {
            System.out.println("------------------------------- ADMIN MENU ---------------------------------");
            System.out.println("1. View Suggestions");
            System.out.println("2. Add Expense Category");
            System.out.println("3. View All Categories");
            System.out.println("4. View All Users");
            System.out.println("5. View Specific User Details");
            System.out.println("6. Delete Suggestion");
            System.out.println("7. Delete Category ");
            System.out.println("8. Log Out");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("---------------------------- All Suggestions -------------------------------");
                    adminRepo.viewSuggestions();
                    System.out.println("----------------------------------------------------------------------------\n");
                    break;
                case 2:
                    System.out.println("-------------------------------- Add Category -------------------------------");
                    System.out.print("Enter new category name: ");
                    String categoryName = sc.nextLine();
                    adminRepo.addExpenseCategory(categoryName);
                    System.out.println("----------------------------------------------------------------------------\n");
                    break;
                case 3:
                    System.out.println("------------------------------ All Categories --------------------------------");
                    adminRepo.viewAllCategories();
                    System.out.println("----------------------------------------------------------------------------\n");
                    break;
                case 4:
                    System.out.println("------------------------------ All Users -----------------------------------");
                    adminRepo.viewAllUsers();
                    System.out.println("----------------------------------------------------------------------------\n");
                    break;
                case 6:
                    System.out.println("---------------------------- Delete Suggestion ------------------------------");
                    adminRepo.viewSuggestions();
                    System.out.print("Enter Suggestion ID to delete: ");
                    int sugId = sc.nextInt();
                    sc.nextLine();

                    boolean deleted = adminRepo.deleteSuggestion(sugId);
                    if (deleted) {
                        System.out.println("Suggestion deleted successfully.");
                    } else {
                        System.out.println("Failed to delete suggestion. Please check the ID and try again.");
                    }
                    System.out.println("----------------------------------------------------------------------------");
                    break;
                case 5:
                    System.out.println("-------------------------- Get User Details -------------------------------\n");
                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        User fetchedUser = adminRepo.getUserById(id);
                        System.out.println("User Details:");
                        System.out.println("User ID   : " + fetchedUser.getUserId());
                        System.out.println("Name      : " + fetchedUser.getUsername());
                        System.out.println("Email     : " + fetchedUser.getEmail());
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("----------------------------------------------------------------------------");
                    break;
                case 7:
                    System.out.println("---------------------------- Delete Category ------------------------------");
                    adminRepo.viewAllCategories();
                    System.out.print("Enter Category ID to delete: ");
                    int catIdToDelete = sc.nextInt();
                    sc.nextLine();

                    if (adminRepo.isCategoryUsed(catIdToDelete)) {
                        System.out.println("Cannot delete this category. It is already used in some expenses.");
                    } else {
                        deleted = adminRepo.deleteCategory(catIdToDelete);
                        if (deleted) {
                            System.out.println("Category deleted successfully.");
                        } else {
                            System.out.println("Failed to delete category. Please try again.");
                        }
                    }
                    System.out.println("----------------------------------------------------------------------------");
                    break;

                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    System.out.println("----------------------------------------------------------------------------");
            }
        }
    }

    private static void viewExpense(User user){
        List<Expense> expenseList = repo.getAllExpensesByUserId(user.getUserId());

        if (expenseList.isEmpty()) {
            System.out.println("No expenses found!");
        } else {
            System.out.printf("%-12s %-15s %-10s %-15s %-25s\n", "Expense ID", "Category", "Amount", "Date", "Description");
            System.out.println("---------------------------------------------------------------------------");
            for (Expense exp : expenseList) {
                System.out.printf("%-12d %-15s %-10.2f %-15s %-25s\n",
                        exp.getExpenseId(),
                        exp.getCategoryName(), // coming in step 2
                        exp.getAmount(),
                        exp.getDate().toString(),
                        exp.getDescription());
            }
        }
    }

}
