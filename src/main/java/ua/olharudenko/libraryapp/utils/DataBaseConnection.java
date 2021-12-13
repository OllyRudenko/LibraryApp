package ua.olharudenko.libraryapp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static Connection connection;
    private static DataBaseConnection instance;

    private static FileInputStream fis;
    private static Properties property = new Properties();

    private DataBaseConnection() throws SQLException {
        try {
            fis = new FileInputStream("src/main/resources/jdbc.properties");
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(property.getProperty("database.url"),
                property.getProperty("database.user"),
                property.getProperty("database.password"));
    }

    public static Connection getConn() {
        return connection;
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBaseConnection();
        } else if (DataBaseConnection.getConn().isClosed()) {
            instance = new DataBaseConnection();
        }
        return instance;
    }
}

