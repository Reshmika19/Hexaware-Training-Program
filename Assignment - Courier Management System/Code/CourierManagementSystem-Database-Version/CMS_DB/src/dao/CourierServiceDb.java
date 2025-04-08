package dao;

import util.DBConnection;

import java.sql.Connection;

public class CourierServiceDb {
    private static Connection connection;

    public CourierServiceDb() {
        connection = DBConnection.getConnection();
    }
    public void testConnection() {
        if (connection != null) {
            System.out.println("Connection is available in CourierServiceDb");
        } else {
            System.out.println("No connection available");
        }
    }
}
