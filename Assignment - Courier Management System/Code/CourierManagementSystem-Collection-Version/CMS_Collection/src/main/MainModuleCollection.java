package main;

import dao.CourierAdminServiceImpCollection;
import dao.CourierUserServiceImpCollection;
import dao.ICourierAdminServiceCollection;
import dao.ICourierUserServiceCollection;
import entity.*;

import java.util.*;

public class MainModuleCollection {

    static ArrayList<UserC> userCList = new ArrayList<>();
    static Map<Integer, List<CourierC>> courierMap = new HashMap<>();
    static ArrayList<PaymentC> paymentCList = new ArrayList<>();
    static ICourierUserServiceCollection userService = new CourierUserServiceImpCollection();
    static ICourierAdminServiceCollection adminService = new CourierAdminServiceImpCollection();
    static ArrayList<EmployeeC> employeeCList = new ArrayList<>();
    static List<CourierCompanyC> courierCompanyCList = new ArrayList<>();
    static List<LocationC> locationCList = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("                                 CourierC Management System                              ");
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
                    System.out.println("                     Thank you for using CourierC Management System                      ");
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
            System.out.println("\n------------------------------------------ UserC Menu ---------------------------------------");
            System.out.println("1. Create User");
            System.out.println("2. Login");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userService.createUser(scanner, userCList);
                    break;
                case 2:
                    UserC loggedInUserC = userService.loginUser(scanner, userCList);
                    if (loggedInUserC != null) {
                        userActions(loggedInUserC);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void adminMenu(){
        while (true) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------- ADMIN -------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("1. View All Orders");
            System.out.println("2. Update CourierC Status");
            System.out.println("3. View All Payments");
            System.out.println("4. Assign EmployeeC & LocationC to CourierC");
            System.out.println("5. Add EmployeeC");
            System.out.println("6. Add LocationC");
            System.out.println("7. Add CourierC Service");
            System.out.println("8. View CourierC Service Report");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n------------------------------- View All Orders -----------------------------------");

                    adminService.viewAllOrders(courierMap);
                    break;
                case 2:
                    System.out.println("\n------------------------------- Update Status  -----------------------------------");

                    System.out.print("Enter Tracking Number: ");
                    String trackingNumber = scanner.nextLine();
                    System.out.print("Enter New Status: ");
                    String newStatus = scanner.nextLine();
                    adminService.updateCourierStatus(trackingNumber, newStatus, courierMap);
                    break;
                case 3:
                    System.out.println("\n------------------------------- View All Payments -----------------------------------");

                    adminService.viewAllPayments(paymentCList);
                    break;
                case 4:
                    System.out.println("\n----------------------- Assign EmployeeC , location and Service ---------------------");

                    adminService.assignEmployeeLocationCompanyToCourier(scanner, courierMap, courierCompanyCList);
                    break;
                case 5:
                    System.out.println("\n------------------------------- Add EmployeeC  -----------------------------------");

                    adminService.createEmployee(scanner, employeeCList);
                    break;
                case 6:
                    System.out.println("\n------------------------------- Add LocationC -----------------------------------");

                    adminService.createLocation(scanner, locationCList);
                    break;
                case 7:
                    System.out.println("\n------------------------------- Add CourierC Company -----------------------------------");

                    adminService.createCourierCompany(scanner, courierCompanyCList, employeeCList, locationCList);
                    break;
                case 8:
                    System.out.println("\n-------------------------- CourierC Company Report -----------------------------------");

                    adminService.viewCourierCompanyReport(courierCompanyCList);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void userActions(UserC userC) {
        while (true) {
            System.out.println("\nWelcome, " + userC.getUserName() + "!");
            System.out.println("1. Place CourierC");
            System.out.println("2. Track CourierC");
            System.out.println("3. Cancel CourierC");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    placeCourier(userC);
                    break;
                case 2:
                   trackCourier(courierMap , scanner);
                    break;
                case 3:
                    cancelCourier(courierMap , scanner);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return; // log out and go back to the main menu
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Place a courier order
    public static void placeCourier(UserC userC) {
        System.out.println("\n---------------------------------- Place CourierC ------------------------------------");

        String trackingNumber = userService.placeCourier(userC, courierMap, paymentCList, scanner);

        if (trackingNumber != null) {
            System.out.println("CourierC placed successfully! Your tracking number is: " + trackingNumber);
        } else {
            System.out.println("CourierC was not placed due to non-payment.");
        }
    }

    // Track an order
    public static void trackCourier(Map<Integer, List<CourierC>> courierMap, Scanner scanner) {
        System.out.println("\n---------------------------------- Track CourierC ------------------------------------");

        System.out.print("Enter Tracking Number: ");
        String trackingNumber = scanner.nextLine();

        String status = userService.getOrderStatus(trackingNumber, courierMap);
        System.out.println(status);
    }

    // Cancel a courier
    public static void cancelCourier(Map<Integer, List<CourierC>> courierMap, Scanner scanner) {
        System.out.println("\n---------------------------------- Cancel CourierC ------------------------------------");

        System.out.print("Enter Tracking Number to cancel: ");
        String trackingNumber = scanner.nextLine();

        String result = userService.cancelOrder(trackingNumber, courierMap);
        System.out.println(result);
    }

}
