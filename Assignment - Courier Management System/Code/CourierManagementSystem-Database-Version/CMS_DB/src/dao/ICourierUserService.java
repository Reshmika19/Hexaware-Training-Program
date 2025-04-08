package dao;

import entity.User;
import java.util.Scanner;

public interface ICourierUserService {
    void createUser(Scanner scanner);
    User loginUser(Scanner scanner);
    String placeCourier(User user);
    String getOrderStatus(String trackingNumber);
    String cancelOrder(long courierId);
    void viewOrderHistory(int userId);
}
