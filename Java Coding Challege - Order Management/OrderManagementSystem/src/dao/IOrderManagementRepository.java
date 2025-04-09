package dao;

import entity.Order;
import entity.User;
import exception.OrderNotFoundException;
import exception.UserNotFoundException;

import java.util.List;
import java.util.Scanner;

public interface IOrderManagementRepository {
    void createUser(Scanner scanner);
    User loginUser(Scanner scanner);
    void createOrder(User user);
    public List<String> cancelOrder(int orderId, int userId) throws UserNotFoundException, OrderNotFoundException;    void viewHistory(User user);
    void updateProfile(User user);
    void deleteAccount(User user);
}
