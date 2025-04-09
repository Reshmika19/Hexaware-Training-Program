package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Properties props = DBPropertyUtil.getDBProperties();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return connection;
    }
}
