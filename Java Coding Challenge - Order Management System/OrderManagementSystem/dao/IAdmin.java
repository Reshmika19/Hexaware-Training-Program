package dao;

import entity.User;

import java.util.Scanner;

public interface IAdmin{
    void createAdmin(Scanner scanner);
    User loginAdmin(Scanner scanner);
    void createProduct(Scanner scanner);
    void getAllProducts();
    void viewAllUsers();
    void viewAllElectronicProducts();
    void viewAllClothingProducts();
    void viewAllOrders();
    void viewOrdersByUsername(String username);
    void updateProductDetails(int id, String name, String desc, double price, int qty);

}
