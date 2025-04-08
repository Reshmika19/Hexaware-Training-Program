package entity;
import java.util.List;

public class CourierCompany {
    private String id;
    private String name;
    private List<Employee> employees;
    private List<Location> locations;

    public CourierCompany(String id, String name, List<Employee> employees, List<Location> locations) {
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.locations = locations;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
