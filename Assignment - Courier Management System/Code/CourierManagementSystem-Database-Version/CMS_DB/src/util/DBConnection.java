package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                InputStream input = new FileInputStream("resources/db.properties");

                if (input == null) {
                    throw new RuntimeException("Unable to find db.properties");
                }

                props.load(input);

                String driver = props.getProperty("db.driver");
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                Class.forName(driver);

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully!");

            } catch (Exception e) {
                System.err.println("Database connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}
