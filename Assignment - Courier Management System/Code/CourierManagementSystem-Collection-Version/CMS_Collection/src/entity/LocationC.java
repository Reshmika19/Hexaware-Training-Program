package entity;

public class LocationC {
    private int locationID;
    private String locationName;
    private String address;

    public LocationC(int locationID, String locationName, String address) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.address = address;
    }

    public int getLocationID() { return locationID; }
    public void setLocationID(int locationID) { this.locationID = locationID; }
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "LocationC ID: " + locationID + ", Name: " + locationName + ", Address: " + address;
    }
}
