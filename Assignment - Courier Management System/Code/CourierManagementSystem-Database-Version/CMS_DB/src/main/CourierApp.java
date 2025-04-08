package main;

import dao.CourierAdminServiceImp;
import dao.CourierUserServiceImp;
import dao.ICourierAdminService;
import entity.User;

import java.util.Scanner;

public class CourierApp {

    static ICourierAdminService adminService = new CourierAdminServiceImp();
    static CourierUserServiceImp userService = new CourierUserServiceImp();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("                                 Courier Management System                              ");
            System.out.println("                            \"Delivering Trust, One Parcel at a Time\"                  ");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    adminMenu();
                    break;
                case 3:
                    System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("                     Thank you for using Courier Management System                      ");
                    System.out.println("                           Have a great day! Visit us again!                            ");
                    System.out.println("----------------------------------------------------------------------------------------");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void userMenu() {
        while (true) {
            System.out.println("\n------------------------------------------ User Menu ---------------------------------------");
            System.out.println("1. Create User");
            System.out.println("2. Login");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userService.createUser(scanner);
                    break;
                case 2:
                    User user = userService.loginUser(scanner);
                    if (user != null) {
                        userActions(user);
                    }
                    break;

                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------- ADMIN -------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("1. View Details");
            System.out.println("2. Update Information");
            System.out.println("3. Add New Entries");
            System.out.println("4. Delete Entries");
            System.out.println("5. Reports");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    viewMenu();
                    break;

                case 2:
                    updateMenu();
                    break;

                case 3:
                    AddMenu();
                    break;

                case 4:
                    deleteMenu();
                    break;

                case 5:
                    System.out.println("\n---------------------- Courier Company Report ----------------------------------");
                    adminService.viewCourierCompanyReport(scanner);
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid main choice! Please try again.");
            }
        }
    }

    public static void viewMenu() {
        while (true) {
            System.out.println("\n------------------------------------- View Menu ---------------------------------------");
            System.out.println("1. View All Orders");
            System.out.println("2. View All Users");
            System.out.println("3. View All Courier Services");
            System.out.println("4. View All Employees");
            System.out.println("5. View All Locations");
            System.out.println("6. View All Payments");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int viewChoice = scanner.nextInt();
            scanner.nextLine();

            switch (viewChoice) {
                case 1:
                    adminService.viewAllOrders();
                    break;
                case 2:
                    adminService.viewAllUsers();
                    break;
                case 3:
                    adminService.viewAllCourierCompanies();
                    break;
                case 4:
                    adminService.viewAllEmployees();
                    break;
                case 5:
                    adminService.viewAllLocations();
                    break;
                case 6:
                    adminService.viewAllPayments();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void updateMenu() {
        while (true) {
            System.out.println("\n----------------------------------- Update Menu ---------------------------------------");
            System.out.println("1. Update Courier Status");
            System.out.println("2. Assign Employee & Location to Courier");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int updateChoice = scanner.nextInt();
            scanner.nextLine();

            switch (updateChoice) {
                case 1:
                    System.out.println("\n------------------------ Update Order Status ----------------------------------");
                    System.out.print("Enter the Courier ID: ");
                    String courierId = scanner.nextLine().trim();
                    adminService.updateCourierStatus(scanner, courierId);
                    break;
                case 2:
                    System.out.println("\n----------------- Assign Employee , Location , Company -------------------------");
                    adminService.assignEmployeeLocationCompanyToCourier(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void AddMenu() {
        while (true) {
            System.out.println("\n------------------------------------- Add Menu ---------------------------------------");
            System.out.println("1. Add Courier Service");
            System.out.println("2. Add Employee");
            System.out.println("3. Add Location");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int addChoice = scanner.nextInt();
            scanner.nextLine();

            switch (addChoice) {
                case 1:
                    adminService.createCourierCompany(scanner);
                    break;
                case 2:
                    adminService.createEmployee(scanner);
                    break;
                case 3:
                    adminService.createLocation(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void deleteMenu() {
        while (true) {
            System.out.println("\n----------------------------------- Delete Menu ---------------------------------------");
            System.out.println("1. Delete Employee");
            System.out.println("2. Delete Location");
            System.out.println("3. Delete Courier Company");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminService.viewAllEmployees();
                    System.out.print("Enter the Employee ID to delete: ");
                    long empId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println(adminService.deleteEmployee(empId));
                    break;

                case 2:
                    adminService.viewAllLocations();
                    System.out.print("Enter the Location ID to delete: ");
                    long locId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println(adminService.deleteLocation(locId));
                    break;

                case 3:
                    adminService.viewAllCourierCompanies();
                    System.out.print("Enter the Courier Company ID to delete: ");
                    long companyId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println(adminService.deleteCourierCompanyCascade(companyId));
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }



    public static void userActions(User user) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------- USER -------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\nWelcome to Courier Services, " + user.getUserName() + "!");
        while (true) {
            System.out.println("\n---------------------------------- User Actions Menu ------------------------------------");
            System.out.println("1. Place Courier");
            System.out.println("2. Track Courier");
            System.out.println("3. Cancel Courier");
            System.out.println("4. View Order History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n---------------------------------- Place Courier ------------------------------------");
                    String trackingId = userService.placeCourier(user);
                    if (trackingId != null) {
                        System.out.println("Your courier has been placed with Tracking ID: " + trackingId);
                    } else {
                        System.out.println("Failed to place courier.");
                    }
                    break;

                case 2:
                    System.out.println("\n---------------------------------- Track Courier ------------------------------------");
                    System.out.print("Enter Tracking Number: ");
                    String trackingNumber = scanner.nextLine();
                    userService.getOrderStatus(trackingNumber);
                    break;
                case 3:
                    System.out.println("\n---------------------------------- Cancel Courier ------------------------------------");
                    System.out.print("Enter Courier ID to cancel: ");
                    long idToCancel = Long.parseLong(scanner.nextLine());
                    String result = userService.cancelOrder(idToCancel);
                    System.out.println(result);
                    break;
                case 4:
                    System.out.println("\n------------------------------- View Order History -----------------------------------");
                    userService.viewOrderHistory(user.getUserID());
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
