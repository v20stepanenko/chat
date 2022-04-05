package chat.org.util;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties dbProp;

    static {
        dbProp = loadProperties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find SQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(dbProp.getProperty("url"), dbProp);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }

    private static Properties loadProperties() {
        Properties dbProp = new Properties();
        try (InputStream input = ConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find database.properties");
            }
            dbProp.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbProp;
    }
}