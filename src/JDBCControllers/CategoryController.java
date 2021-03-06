package JDBCControllers;

import model.Category;
import utils.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    public static boolean categoryExist(String name) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean categoriesExistInSystem(int systemId) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE systemId = ?");
        ps.setInt(1, systemId);
        ResultSet user = ps.executeQuery();
        return user.next();
    }

    public static List<Category> getAllCategories(int systemId) throws ClassNotFoundException, SQLException {
        List<Category> allCategories = new ArrayList<>();
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE systemId = ?");
        ps.setInt(1, systemId);
        ResultSet categories = ps.executeQuery();

        while (categories.next()) {
            allCategories.add(new Category(categories.getInt("id"),
                    categories.getString("name"),
                    categories.getString("description")));
        }
        return allCategories;
    }

    public static Category findCategory(int categoryId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE id=?");
        ps.setInt(1, categoryId);
        ResultSet category = ps.executeQuery();
        if (!category.next()) {
            connection.close();
            return null;
        }

        Category resultCategory = new Category(category.getInt("id"),
                category.getString("name"),
                category.getString("description"),
                category.getDate("dateCreated"),
                category.getDate("dateModified"));
        connection.close();
        return resultCategory;
    }

    public static Category findCategory(String categoryName, int id) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE name=? and systemId=?");
        ps.setString(1, categoryName);
        ps.setInt(2, id);
        ResultSet category = ps.executeQuery();
        if (!category.next()) {
            connection.close();
            return null;
        }
        Category resultCategory = new Category(category.getInt("id"),
                category.getString("name"),
                category.getString("description"),
                category.getDate("dateCreated"),
                category.getDate("dateModified"));
        connection.close();
        return resultCategory;
    }

    public static boolean subcategoriesExistInCategory(int parentId) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE parentCategoryId = ?");
        ps.setInt(1, parentId);
        ResultSet user = ps.executeQuery();
        return user.next();
    }

    public static void update(Category category) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("update category set name =?, description =?, dateModified=? where id=?");
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
        ps.setDate(3, new Date(category.getDateModified().getTime()));
        ps.setInt(4, category.getId());
        ps.executeUpdate();
        connection.close();
    }

    public static void updateParentCategory(Category category, int parentId) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("update category set parentCategoryId =? where id=?");
        ps.setInt(1, parentId);
        ps.setInt(2, category.getId());
        ps.executeUpdate();
        connection.close();
    }

    public static void createCategory(Category category) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO category(name, description, dateCreated, systemId) VALUES( ?,?,?, (SELECT systemId from fms_system where systemId=?))");
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
        ps.setDate(3, new Date(category.getDateCreated().getTime()));
        ps.setInt(4, category.getFms().getId());

        ps.executeUpdate();
        connection.close();
    }

    public static void addResponUser(int categoryId, int userId) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO responsible_user(userId, categoryId) VALUES(?,?)");
        ps.setInt(1, userId);
        ps.setInt(2, categoryId);
        ps.executeUpdate();
        connection.close();
    }

    public static void removeCategory(Category category) throws ClassNotFoundException, SQLException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement("delete from category where id=?");
        ps.setInt(1, category.getId());
        ps.execute();
        connection.close();
    }

    public static boolean isResponsibleUser(int categoryId, int userId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.connectToDb();
        PreparedStatement ps = connection.prepareStatement("Select * from responsible_user where userId=? and categoryId=?");
        ps.setInt(1, userId);
        ps.setInt(2, categoryId);
        ResultSet user = ps.executeQuery();
        return user.next();
    }
}
