package dao;

import java.util.Scanner;

public interface ICourierAdminService {
    void viewAllOrders();
    boolean updateCourierStatus(Scanner scanner, String courierId);
    void viewAllPayments();
    void createEmployee(Scanner scanner);
    void createLocation(Scanner scanner);
    void createCourierCompany(Scanner scanner);
    void viewAllUsers();
    void viewAllLocations();
    void viewAllEmployees();
    void viewAllCourierCompanies();
    void assignEmployeeLocationCompanyToCourier(Scanner scanner);
    void viewCourierCompanyReport(Scanner scanner);
    String deleteEmployee(long empId);
    String deleteLocation(long locationId);
    String deleteCourierCompanyCascade(long companyId);

}

