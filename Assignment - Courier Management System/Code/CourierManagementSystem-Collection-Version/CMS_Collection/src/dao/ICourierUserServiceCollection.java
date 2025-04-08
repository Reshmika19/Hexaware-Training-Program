package dao;

import entity.CourierC;

import entity.PaymentC;
import entity.UserC;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface ICourierUserServiceCollection {
    void createUser(Scanner scanner, ArrayList<UserC> userCList);
    UserC loginUser(Scanner scanner, ArrayList<UserC> userCList);
    String placeCourier(UserC userC, Map<Integer, List<CourierC>> courierMap, ArrayList<PaymentC> paymentCList, Scanner scanner);
    String getOrderStatus(String trackingNumber, Map<Integer, List<CourierC>>  courierMap);
    String cancelOrder(String trackingNumber, Map<Integer, List<CourierC>> courierMap);
}

