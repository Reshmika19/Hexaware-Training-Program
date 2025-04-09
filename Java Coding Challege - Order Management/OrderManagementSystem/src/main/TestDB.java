package main;

import util.DBConnUtil;

import java.sql.Connection;

public class TestDB{
    public static void main(String[] args) {
        Connection conn = DBConnUtil.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
