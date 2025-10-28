package lms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "java"; // <-- enter your MySQL username (e.g., "root")
    private static final String DB_PASSWORD = "java123#"; // <-- enter your MySQL password (often empty on XAMPP)

    private Database() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}


