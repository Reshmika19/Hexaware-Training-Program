package entity;

import java.util.*;

public class CourierC {
    private long courierID;
    private String senderName;
    private String senderAddress;
    private String receiverName;
    private String receiverAddress;
    private double weight;
    private String status;
    private String trackingNumber;
    private Date deliveryDate;
    private int userId;
    private int employeeId;
    private int locationId;
    private String courierCompany;
    private static int trackingNumberCounter = 1000;


    public CourierC(String senderName, String senderAddress, String receiverName, String receiverAddress,
                    double weight, int userId) {
        this.courierID = trackingNumberCounter++;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.weight = weight;
        this.status = "Yet to Transit";
        this.trackingNumber = generateTrackingNumber();
        this.deliveryDate = null;
        this.userId = userId;
        this.employeeId = -1;
        this.locationId = -1;
        this.courierCompany = "";
    }

    // Getters and Setters
    public long getCourierID() { return courierID; }
    public void setCourierID(int courierID) { this.courierID = courierID; }
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getSenderAddress() { return senderAddress; }
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingNumber() { return trackingNumber; }
    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }
    public long getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }

    public String getCourierCompanyId() { return courierCompany; }
    public void setCourierCompanyId(String courierCompanyId) { this.courierCompany = courierCompanyId; }

    @Override
    public String toString() {
        return "courierID=" + courierID +
                ", senderName='" + senderName +
                "', receiverName='" + receiverName +
                "', status='" + status +
                "', trackingNumber='" + trackingNumber +
                "', employeeId=" + (employeeId == -1 ? "Not Assigned" : employeeId) +
                ", locationId=" + (locationId == -1 ? "Not Assigned" : locationId) +
                ", courierCompany=" + (courierCompany == "" ? "Not Assigned" : courierCompany);
    }

    private String generateTrackingNumber() {
        return "TN" + this.courierID;
    }
}
