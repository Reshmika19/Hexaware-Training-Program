package main;

import dao.CourierServiceDb;
public class testDB {
    public static void main(String[] args) {
        CourierServiceDb service = new CourierServiceDb();
        service.testConnection();
    }
}

