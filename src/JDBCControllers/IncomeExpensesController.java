package JDBCControllers;

import model.Expenses;
import model.Income;
import utils.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeExpensesController {

    public static List<Income> getIncome(int categoryId) throws ClassNotFoundException, SQLException {
            List<Income> income = new ArrayList<>();
            Connection connection = JDBCConnection.connectToDb();
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT * FROM income WHERE categoryId = ?");
            ps.setInt(1, categoryId);
            ResultSet incomeQ = ps.executeQuery();

            while (incomeQ.next()) {
                income.add(new Income(incomeQ.getInt("incomeId"),
                        incomeQ.getString("name"),
                        incomeQ.getInt("amount"),
                        incomeQ.getDate("date"),
                        CategoryController.findCategory(incomeQ.getInt("categoryId"))));
            }
            return income;
    }

    public static void create(Income income) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        Statement statement = connection.createStatement();
        String incomeInsert = "INSERT INTO income(name, amount, date, categoryId) " +
                "VALUES('" + income.getName() +
                "', '" + income.getPrice() + "', '" + new Date(income.getDateCreated().getTime()) + "', '" + income.getCategory().getId() + "')";
        statement.executeUpdate(incomeInsert);
        connection.close();
    }

    public static void createExpenses(Expenses ex) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        Statement statement = connection.createStatement();
        String incomeInsert = "INSERT INTO expenses (name, amount, date, categoryId) " +
                "VALUES('" + ex.getName() +
                "', '" + ex.getPrice() + "', '" + new Date(ex.getDateCreated().getTime()) + "', '" + ex.getCategory().getId() + "')";
        statement.executeUpdate(incomeInsert);
        connection.close();
    }

    public static List<Expenses> getExpenses(int categoryId) throws ClassNotFoundException, SQLException {
        List<Expenses> expenses = new ArrayList<>();
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM expenses WHERE categoryId = ?");
        ps.setInt(1, categoryId);
        ResultSet expenseQuery = ps.executeQuery();

        while (expenseQuery.next()) {
            expenses.add(new Expenses(expenseQuery.getInt("expenseId"),
                    expenseQuery.getInt("amount"),
                    expenseQuery.getString("name"),
                    expenseQuery.getDate("date"),
                    CategoryController.findCategory(expenseQuery.getInt("categoryId"))));
        }
        return expenses;
    }
}
