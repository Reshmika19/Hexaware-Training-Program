package entity;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int userId;
    private int productId;
    private int quantity;
    private double totalAmt;
    private String paymentMode;
    private String status;
    private LocalDateTime orderDate;

    public Order(int orderId, int userId, int productId, int quantity, double totalAmt, String paymentMode, String status, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmt = totalAmt;
        this.paymentMode = paymentMode;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return
                "orderId : " + orderId +
                        ", userId : " + userId +
                        ", productId : " + productId +
                        ", quantity : " + quantity +
                        ", totalAmt : " + totalAmt +
                        ", paymentMode : '" + paymentMode + '\'' +
                        ", status : '" + status + '\'' +
                        ", orderDate : " + orderDate;
    }
}
