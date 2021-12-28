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

    private DataBaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library",
                "admin",
                "1");
    }

    public static Connection getConn() {
        return connection;
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            try {
                instance = new DataBaseConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (DataBaseConnection.getConn().isClosed()) {
            try {
                instance = new DataBaseConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}

