package main;

import dao.AdminProcessor;
import dao.OrderProcessor;
import entity.Product;
import entity.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {

    static Scanner scanner = new Scanner(System.in);
    static OrderProcessor orderProcessor = new OrderProcessor();
    static AdminProcessor adminProcessor = new AdminProcessor();


    public static void main(String[] args) {


        System.out.println("===== Welcome to Order Management System =====");
        System.out.println("\"Great service begins with great order!\"\n");

        int choice;

        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userActions(scanner, orderProcessor);
                    break;

                case 2:
                    adminActions(scanner , adminProcessor);
                    break;

                case 3:
                    System.out.println("Thank you for using Order Management System!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userActions(Scanner scanner, OrderProcessor orderProcessor) {
        int choice;

        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    orderProcessor.createUser(scanner);
                    break;

                case 2:
                    User user = orderProcessor.loginUser(scanner);
                    if (user != null) {
                        userMenu(scanner, user);
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminActions(Scanner scanner, AdminProcessor adminProcessor) {
        int choice;

        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminProcessor.createAdmin(scanner);
                    break;

                case 2:
                    User user = adminProcessor.loginAdmin(scanner);
                    if (user != null) {
                        adminMenu(scanner);
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, User user) {
        int choice;
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Create Product Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Order History");
            System.out.println("4. Update Profile");
            System.out.println("5. Delete Account");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    orderProcessor.createOrder(user);
                    break;

                case 2:
                    orderProcessor.viewHistory(user);
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelId = scanner.nextInt();

                    try {
                        List<String> resultMessages = orderProcessor.cancelOrder(cancelId, user.getUserId());
                        for (String msg : resultMessages) {
                            System.out.println(msg);
                        }
                    } catch (UserNotFoundException | OrderNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;



                case 3:
                    orderProcessor.viewHistory(user);
                    break;

                case 4:
                    orderProcessor.updateProfile(user);
                    break;
                case 5:
                    orderProcessor.deleteAccount(user);
                    break;
                case 6:
                    System.out.println("You have been logged out. Have a great day!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View All Orders");
            System.out.println("2. View All Electronic Products");
            System.out.println("3. View All Clothing Products");
            System.out.println("4. View Orders by User");
            System.out.println("5. View All Users");
            System.out.println("6. Create Product");
            System.out.println("7. Update Product");
            System.out.println("8. View All products");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    adminProcessor.viewAllOrders();
                    break;


                case 2:
                    adminProcessor.viewAllElectronicProducts();
                    break;

                case 3:
                   adminProcessor.viewAllClothingProducts();
                    break;

                case 4:
                    System.out.print("Enter username to view their orders: ");
                    String uname = scanner.nextLine().trim();
                    adminProcessor.viewOrdersByUsername(uname);
                    break;


                case 5:
                    adminProcessor.viewAllUsers();
                    break;


                case 6:
                    adminProcessor.createProduct(scanner);
                    break;


                case 7:
                    adminProcessor.viewAllProducts();
                    scanner.nextLine();

                    System.out.print("Enter Product ID to update: ");
                    int pid = Integer.parseInt(scanner.nextLine().trim());
                    Product existing = adminProcessor.getProductById(pid);
                    if (existing == null) {
                        System.out.println("❌ Product not found with ID: " + pid);
                        break;
                    }

                    System.out.println("Enter 'null' to skip updating that field.");

                    System.out.print("Enter new product name (Current: " + existing.getProductName() + "): ");
                    String pname = scanner.nextLine();
                    if (pname.equalsIgnoreCase("null") || pname.isBlank()) {
                        pname = existing.getProductName();
                    }

                    System.out.print("Enter new description (Current: " + existing.getDescription() + "): ");
                    String description = scanner.nextLine();
                    if (description.equalsIgnoreCase("null") || description.isBlank()) {
                        description = existing.getDescription();
                    }

                    System.out.print("Enter new price (Current: " + existing.getPrice() + "): ");
                    String priceInput = scanner.nextLine();
                    double price = priceInput.equalsIgnoreCase("null") || priceInput.isBlank()
                            ? existing.getPrice()
                            : Double.parseDouble(priceInput);

                    System.out.print("Enter new stock quantity (Current: " + existing.getQuantityInStock() + "): ");
                    String stockInput = scanner.nextLine();
                    int stock = stockInput.equalsIgnoreCase("null") || stockInput.isBlank()
                            ? existing.getQuantityInStock()
                            : Integer.parseInt(stockInput);

                    adminProcessor.updateProductDetails(pid, pname, description, price, stock);
                    break;
                case 8:
                    adminProcessor.viewAllProducts();
                    break;

                case 9:
                    System.out.println("Logging out of admin panel. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


}


//    before doing , IOrderManagementRepository
//
//
//    lets set mainmodule 1st
//        so it will easy for us to do
//
//
//        so i want my output like
//
//        Order managemet system
//        one simple quote
//
//        1. user
//        2. admin
//        3. Exit
//
//        case 1 : useractions
//        case 2 : admin acrions
//        case 3 : return
//
//        useractions
//        1. create account
//        2. login
//        2. exit
//
//        if 1 means --> orderProcessor.createUser();
//        case 2 : so we know the role right (its not admin , and it is user)
//        orderProcessor.login(scanner)
//        ask username and passowrd , we know role user
//        check with table user , if creditanls correct means login , else dispaly invalid
//        return user object
//
//        adminactions :
//        adminProcessor.login(scanner)
//        ask username and passowrd , we know role admin
//        check with table user , if creditanls correct means login , else dispaly invalid
//        return user object
//        then if crt measns , call adminMenu()
//
//        in usermenu
//        case 1 : updating product create
//        case2 : cancel order
//        case 3 : history
//        case 4 : update
//        case 5 ; delete account
//        case 6 : exit
//
//        in adminmenu
//        case 1 : get all orders
//        case 2 : get all electronis
//        case 3 : get all cloths
//        case 4 : getorder by user
//        case 5 : all user
//        case 5 : ccreate prducr
//        case 6 : update product
//        case 7: exit
//
//
//
//        like this give ull mainmodule
//
//        as of now we dont have any code inside order and admin processor , so just give place holder , we update later
//
//        gvie main module code