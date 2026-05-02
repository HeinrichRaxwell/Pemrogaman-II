package Pertemuan7;

import java.sql.*;

public class Koneksi {
    private static Connection connection = null;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/db_belajar";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException error) {
                System.exit(0);
            }
        }
        return connection;
    }
}
