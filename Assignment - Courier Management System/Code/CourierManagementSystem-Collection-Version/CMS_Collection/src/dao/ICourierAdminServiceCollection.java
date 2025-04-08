package dao;

import entity.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface ICourierAdminServiceCollection {
    void viewAllOrders(Map<Integer, List<CourierC>> courierMap);
    boolean updateCourierStatus(String trackingNumber, String newStatus, Map<Integer, List<CourierC>> courierMap);
    void viewAllPayments(List<PaymentC> paymentCList);
    void createEmployee(Scanner scanner, List<EmployeeC> employeeCList);
    void createLocation(Scanner scanner, List<LocationC> locationCList);
    void createCourierCompany(Scanner scanner, List<CourierCompanyC>  companyList, List<EmployeeC> employeeCList,
                              List<LocationC> locationCList);
    void assignEmployeeLocationCompanyToCourier(Scanner scanner,
                                                Map<Integer, List<CourierC>> courierMap, List<CourierCompanyC> companyList);
    void viewCourierCompanyReport(List<CourierCompanyC> courierCompanyCList);
}



