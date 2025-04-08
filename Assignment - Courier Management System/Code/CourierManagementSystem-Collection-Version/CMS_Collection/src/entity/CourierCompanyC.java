package entity;

import java.util.ArrayList;
import java.util.List;

public class CourierCompanyC {
    private String companyName;
    private List<CourierC> courierCDetails;
    private List<EmployeeC> employeeCDetails;
    private List<LocationC> locationCDetails;

    public CourierCompanyC(String companyName) {
        this.companyName = companyName;
        this.courierCDetails = new ArrayList<>();
        this.employeeCDetails = new ArrayList<>();
        this.locationCDetails = new ArrayList<>();
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public List<CourierC> getCourierDetails() { return courierCDetails; }
    public List<EmployeeC> getEmployeeDetails() { return employeeCDetails; }
    public List<LocationC> getLocationDetails() { return locationCDetails; }

    public void addCourier(CourierC courierC) { courierCDetails.add(courierC); }
    public void addEmployee(EmployeeC employeeC) { employeeCDetails.add(employeeC); }
    public void addLocation(LocationC locationC) { locationCDetails.add(locationC); }

    @Override
    public String toString() {
        return "Company: " + companyName + ", Employees: " + employeeCDetails.size() + ", Locations: " +
                locationCDetails.size() + ", Couriers: " + courierCDetails.size();
    }
}
