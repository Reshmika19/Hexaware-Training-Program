package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static Properties getDBProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("resources/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
        }
        return properties;
    }
}
