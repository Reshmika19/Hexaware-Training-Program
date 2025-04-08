package dao;

import entity.*;

import java.util.*;

public class CourierAdminServiceImpCollection implements ICourierAdminServiceCollection {

    @Override
    public void viewAllOrders(Map<Integer, List<CourierC>> courierMap) {
        if (courierMap.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        for (List<CourierC> courierCList : courierMap.values()) {
            for (CourierC courierC : courierCList) {
                System.out.println(courierC);
            }
        }
        System.out.println();
    }

    @Override
    public boolean updateCourierStatus(String trackingNumber, String newStatus, Map<Integer, List<CourierC>> courierMap) {
        for (List<CourierC> courierCList : courierMap.values()) {
            for (CourierC courierC : courierCList) {
                if (courierC.getTrackingNumber().equals(trackingNumber)) {
                    courierC.setStatus(newStatus);
                    System.out.println("Status updated successfully for Tracking Number: " + trackingNumber);
                    return true;
                }
            }
        }
        System.out.println("Tracking Number not found!");
        return false;
    }

    @Override
    public void viewAllPayments(List<PaymentC> paymentCList) {
        if (paymentCList.isEmpty()) {
            System.out.println("No payments recorded.");
            return;
        }

        for (PaymentC paymentC : paymentCList) {
            System.out.println(paymentC);
        }
    }

    @Override
    public void createEmployee(Scanner scanner, List<EmployeeC> employeeCList) {
        System.out.print("Enter EmployeeC Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print("Enter Role: ");
        String role = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        int employeeID = employeeCList.size() + 1;

        EmployeeC newEmployeeC = new EmployeeC(employeeID, name, email, contactNumber, role, salary);
        employeeCList.add(newEmployeeC);

        System.out.println("EmployeeC added successfully with EmployeeC ID: " + employeeID);
    }

    @Override
    public void createLocation(Scanner scanner, List<LocationC> locationCList) {
        System.out.print("Enter LocationC Name: ");
        String locationName = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        int locationID = locationCList.size() + 1;

        LocationC newLocationC = new LocationC(locationID, locationName, address);
        locationCList.add(newLocationC);

        System.out.println("LocationC added successfully with LocationC ID: " + locationID);
    }

    public void createCourierCompany(Scanner scanner, List<CourierCompanyC> companyList, List<EmployeeC> employeeCList,
                                     List<LocationC> locationCList) {
        System.out.print("Enter CourierC Company Name: ");
        String companyName = scanner.nextLine();

        CourierCompanyC newCompany = new CourierCompanyC(companyName);

        while (true) {
            System.out.print("Enter EmployeeC ID to add (or -1 to stop): ");
            int empID = scanner.nextInt();
            scanner.nextLine();
            if (empID == -1) break;

            EmployeeC selectedEmployeeC = findEmployeeById(empID, employeeCList);
            if (selectedEmployeeC != null) {
                newCompany.addEmployee(selectedEmployeeC);
                System.out.println("Added EmployeeC: " + selectedEmployeeC.getEmployeeName());
            } else {
                System.out.println("Invalid EmployeeC ID! Try again.");
            }
        }

        while (true) {
            System.out.print("Enter LocationC ID to add (or -1 to stop): ");
            int locID = scanner.nextInt();
            scanner.nextLine();

            if (locID == -1) break;

            LocationC selectedLocationC = findLocationById(locID, locationCList);
            if (selectedLocationC != null) {
                newCompany.addLocation(selectedLocationC);
                System.out.println("Added LocationC: " + selectedLocationC.getLocationName());
            } else {
                System.out.println("Invalid LocationC ID! Try again.");
            }
        }

        companyList.add(newCompany);
        System.out.println("CourierC Company '" + companyName + "' created successfully!");
    }

    private EmployeeC findEmployeeById(int empID, List<EmployeeC> employeeCList) {
        for (EmployeeC emp : employeeCList) {
            if (emp.getEmployeeID() == empID) return emp;
        }
        return null;
    }

    private LocationC findLocationById(int locID, List<LocationC> locationCList) {
        for (LocationC loc : locationCList) {
            if (loc.getLocationID() == locID) return loc;
        }
        return null;
    }

    public void assignEmployeeLocationCompanyToCourier(Scanner scanner, Map<Integer, List<CourierC>> courierMap,
                                                       List<CourierCompanyC> companyList) {

        System.out.print("Enter CourierC ID to assign EmployeeC & LocationC: ");
        int courierId = scanner.nextInt();
        scanner.nextLine();

        CourierC selectedCourierC = findCourierById(courierId, courierMap);
        if (selectedCourierC == null) {
            System.out.println("Invalid CourierC ID! Try again.");
            return;
        }

        System.out.println("Available CourierC Companies:");
        for (int i = 0; i < companyList.size(); i++) {
            System.out.println((i + 1) + ". " + companyList.get(i).getCompanyName());
        }

        System.out.print("Select a CourierC Company (Enter Number): ");
        int companyChoice = scanner.nextInt();
        scanner.nextLine();
        if (companyChoice < 1 || companyChoice > companyList.size()) {
            System.out.println("Invalid choice! Try again.");
            return;
        }

        CourierCompanyC selectedCompany = companyList.get(companyChoice - 1);
        String assignedCompany = selectedCompany.getCompanyName();

        System.out.println("\nEmployees in " + selectedCompany.getCompanyName() + ":");
        for (EmployeeC emp : selectedCompany.getEmployeeDetails()) {
            System.out.println("ID: " + emp.getEmployeeID() + " | Name: " + emp.getEmployeeName());
        }

        System.out.print("Enter EmployeeC ID to assign: ");
        int empId = scanner.nextInt();
        scanner.nextLine();

        int assignedEmployee = findEmployee(empId, selectedCompany.getEmployeeDetails());
        if (assignedEmployee == -1) {
            System.out.println("Invalid EmployeeC ID! Try again.");
            return;
        }

        System.out.println("\nLocations in " + selectedCompany.getCompanyName() + ":");
        for (LocationC loc : selectedCompany.getLocationDetails()) {
            System.out.println("ID: " + loc.getLocationID() + " | Name: " + loc.getLocationName());
        }

        System.out.print("Enter LocationC ID to assign: ");
        int locId = scanner.nextInt();
        scanner.nextLine();

        int assignedLocation = findLocation(locId, selectedCompany.getLocationDetails());
        if (assignedLocation == -1) {
            System.out.println("Invalid LocationC ID! Try again.");
            return;
        }

        selectedCourierC.setEmployeeId(assignedEmployee);
        selectedCourierC.setLocationId(assignedLocation);
        selectedCourierC.setCourierCompanyId(assignedCompany);

        selectedCompany.addCourier(selectedCourierC);
        System.out.println("Successfully assigned EmployeeC and LocationC to CourierC ID: " + courierId);
    }

    private CourierC findCourierById(int courierId, Map<Integer, List<CourierC>> courierMap) {
        for (List<CourierC> courierCS : courierMap.values()) {
            for (CourierC c : courierCS) {
                if (c.getCourierID() == courierId) {
                    return c;
                }
            }
        }
        return null;
    }

    private int findEmployee(int empID, List<EmployeeC> employeeCList) {
        for (EmployeeC emp : employeeCList) {
            if (emp.getEmployeeID() == empID) return emp.getEmployeeID();
        }
        return -1;
    }

    private int findLocation(int locID, List<LocationC> locationCList) {
        for (LocationC loc : locationCList) {
            if (loc.getLocationID() == locID) return loc.getLocationID();
        }
        return -1;
    }

    public void viewCourierCompanyReport(List<CourierCompanyC> courierCompanyCList) {
        if (courierCompanyCList.isEmpty()) {
            System.out.println("No courier companies available.");
            return;
        }
        System.out.println("\nCourierC Company Report : ");
        for (CourierCompanyC company : courierCompanyCList) {
            System.out.println("\nCompany: " + company.getCompanyName());
            System.out.println("Total Couriers: " + company.getCourierDetails().size());
            System.out.println("Total Employees: " + company.getEmployeeDetails().size());
            System.out.println("Total Locations: " + company.getLocationDetails().size());

            if (company.getCourierDetails().isEmpty()) {
                System.out.println("No couriers assigned yet.");
            } else {
                System.out.println("\nCourierC Details:");
                for (CourierC courierC : company.getCourierDetails()) {
                    System.out.println(courierC);
                }
            }
        }
    }

}

