package configuration;

import java.sql.Connection;

public class DBConnection {
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private final String URL = "jdbc:mysql://localhost:3306/andiamoateatro";

    public static Connection getConnection() {
        return null;
    }
}
