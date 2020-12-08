package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    public static Connection connectToDb() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_URL = "jdbc:mysql://127.0.0.1:3307/systemdata";
        String USER = "root";
        String PASS = "";
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        return conn;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) {
        if (connection != null && statement != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
