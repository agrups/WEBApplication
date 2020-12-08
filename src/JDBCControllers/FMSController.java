package JDBCControllers;

import model.FinanceManagementSystem;
import utils.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FMSController {

    public static FinanceManagementSystem findSystem(int id) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM fms_system WHERE systemId = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return null;
        }

        String companyName = rs.getString(2);
        String version = rs.getString(3);
        Date date = rs.getDate(4);
        FinanceManagementSystem financeManagementSystem = new FinanceManagementSystem(id, companyName, version, date);
        connection.close();
        return financeManagementSystem;
    }

    public static List<FinanceManagementSystem> getAllFMS() throws ClassNotFoundException, SQLException {
        List<FinanceManagementSystem> fms = new ArrayList<>();
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM fms_system");
        ResultSet userQuery = ps.executeQuery();

        while (userQuery.next()) {

            int id = userQuery.getInt("systemId");
            String name = userQuery.getString("companyName");
            String version = userQuery.getString("systemVersion");
            Date date = userQuery.getDate("dateCreated");

            fms.add(new FinanceManagementSystem(id, name, version, date));
        }
        return fms;
    }

    public static FinanceManagementSystem getFinanceSystem(String companyName) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        Statement statement = connection.createStatement();

        ResultSet selectedSystem = statement.executeQuery("SELECT * FROM fms_system WHERE fms_system.companyName='" + companyName + "'");
        selectedSystem.next();
        companyName = selectedSystem.getString(2);
        int id = selectedSystem.getInt(1);
        String version = selectedSystem.getString(3);
        Date date = selectedSystem.getDate(4);
        FinanceManagementSystem gg = new FinanceManagementSystem(id, companyName, version, date);
        connection.close();
        return gg;
    }

    public static List<String> getAllChoices() throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        Statement statement = connection.createStatement();
        ResultSet allSystems = statement.executeQuery("SELECT companyName FROM fms_system");
        List<String> choices = new ArrayList<>();
        while (allSystems.next()) {
            choices.add(allSystems.getString(1));
        }
        return choices;
    }
}
