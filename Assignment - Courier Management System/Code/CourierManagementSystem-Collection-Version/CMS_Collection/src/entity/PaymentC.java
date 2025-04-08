package entity;

import java.util.Date;

public class PaymentC {
    private long paymentID;
    private long courierID;
    private double amount;
    private Date paymentDate;

    public PaymentC(long paymentID, long courierID, double amount, Date paymentDate) {
        this.paymentID = paymentID;
        this.courierID = courierID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public long getPaymentID() { return paymentID; }
    public void setPaymentID(long paymentID) { this.paymentID = paymentID; }
    public long getCourierID() { return courierID; }
    public void setCourierID(long courierID) { this.courierID = courierID; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "PaymentC ID: " + paymentID + ", CourierC ID: " + courierID + ", Amount: " + amount + ", Date: " + paymentDate;
    }
}
