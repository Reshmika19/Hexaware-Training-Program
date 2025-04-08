package entity;
public class Location {
    private String id;
    private String name;
    private String courierCompanyId;

    public Location(String id, String name, String courierCompanyId) {
        this.id = id;
        this.name = name;
        this.courierCompanyId = courierCompanyId;
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

    public String getCourierCompanyId() {
        return courierCompanyId;
    }

    public void setCourierCompanyId(String courierCompanyId) {
        this.courierCompanyId = courierCompanyId;
    }
}
