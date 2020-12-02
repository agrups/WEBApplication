package JDBCControllers;

import model.FinanceManagementSystem;
import model.User;
import utils.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    public static List<User> getAllSysUsers(int sysId) throws ClassNotFoundException, SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE systemId = ?");
        ps.setInt(1, sysId);
        ResultSet userQuery = ps.executeQuery();

        while (userQuery.next()) {

            int id = userQuery.getInt("userId");
            String login = userQuery.getString("loginName");
            String password = userQuery.getString("password");
            String name = userQuery.getString("name");
            String surname = userQuery.getString("surname");
            String phoneNumber = userQuery.getString("phoneNumber");
            String email = userQuery.getString("email");
            Boolean userType = userQuery.getBoolean(6);
            FinanceManagementSystem fms = FMSController.findSystem(userQuery.getInt("systemId"));

            users.add(new User(id, name, surname, email, phoneNumber, userType, login, password, fms));
        }
        return users;
    }

    public static User findUser(String name) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE name=?");
        ps.setString(1, name);
        ResultSet user = ps.executeQuery();
        if (!user.next()) {
            connection.close();
            return null;
        }
        User resultUser = new User(user.getInt("userId"), user.getString("name"));
        connection.close();
        return resultUser;
    }

    public static User findUser(String n, int systemId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE name = ? and systemId = ?");
        ps.setString(1, n);
        ps.setInt(2, systemId);
        ResultSet loggedUser = ps.executeQuery();
        if (!loggedUser.next()) {
            connection.close();
            return null;
        }
        int id = loggedUser.getInt("userId");
        String login = loggedUser.getString("loginName");
        String password = loggedUser.getString("password");
        String name = loggedUser.getString("name");
        String surname = loggedUser.getString("surname");
        String phoneNumber = loggedUser.getString("phoneNumber");
        String email = loggedUser.getString("email");
        Boolean userType = loggedUser.getBoolean(6);
        FinanceManagementSystem fms = FMSController.findSystem(loggedUser.getInt("systemId"));
        connection.close();
        return new User(id, name, surname, email, phoneNumber, userType, login, password, fms);
    }

    public static User findUser(String loginName, String psw) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE loginName = ? and password = ?");
        ps.setString(1, loginName);
        ps.setString(2, psw);
        ResultSet loggedUser = ps.executeQuery();
        if (!loggedUser.next()) {
            connection.close();
            return null;
        }
        int id = loggedUser.getInt("userId");
        String login = loggedUser.getString("loginName");
        String password = loggedUser.getString("password");
        String name = loggedUser.getString("name");
        String surname = loggedUser.getString("surname");
        String phoneNumber = loggedUser.getString("phoneNumber");
        String email = loggedUser.getString("email");
        Boolean userType = loggedUser.getBoolean(6);
        FinanceManagementSystem fms = FMSController.findSystem(loggedUser.getInt("systemId"));
        connection.close();
        return new User(id, name, surname, email, phoneNumber, userType, login, password, fms);
    }


    public static User findUser(int userId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE userId = ?");
        ps.setInt(1, userId);
        ResultSet loggedUser = ps.executeQuery();
        if (!loggedUser.next()) {
            connection.close();
            return null;
        }
        int id = loggedUser.getInt("userId");
        String login = loggedUser.getString("loginName");
        String password = loggedUser.getString("password");
        String name = loggedUser.getString("name");
        String surname = loggedUser.getString("surname");
        String phoneNumber = loggedUser.getString("phoneNumber");
        String email = loggedUser.getString("email");
        Boolean userType = loggedUser.getBoolean(6);
        FinanceManagementSystem fms = FMSController.findSystem(loggedUser.getInt("systemId"));
        connection.close();
        return new User(id, name, surname, email, phoneNumber, userType, login, password, fms);
    }

    public static List<User> getUsers(int categoryId) throws ClassNotFoundException, SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT userId FROM responsible_user WHERE categoryId = ?");
        ps.setInt(1, categoryId);
        ResultSet userQuery = ps.executeQuery();

        while (userQuery.next()) {
            User user = findUser(userQuery.getInt("userId"));
            users.add(user);
        }
        return users;
    }

    public static void create(User user) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        Statement statement = connection.createStatement();
        String userCreate = "INSERT INTO user(loginName, password, name, surname, email, phoneNumber, individual, systemId) " +
                "VALUES('" + user.getLoginName() + "', '" + user.getPassword() + "', '" + user.getName() + "', '" + user.getSurname() + "', '" + user.getEmail() + "', '" + user.getPhoneNumber() + "', '" + 1 + "', " +
                "(SELECT systemId from fms_system where systemId='" + user.getFms().getId() + "'))";
        statement.executeUpdate(userCreate);
        connection.close();
    }

    public static void remove(User user) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement("delete from user where userId=?");
        ps.setInt(1, user.getId());
        ps.execute();
        connection.close();
    }

    public static boolean userExists(String login) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE loginName = ?");
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
