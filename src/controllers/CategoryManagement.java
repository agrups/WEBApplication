package controllers;

import JDBCControllers.CategoryController;
import JDBCControllers.FMSController;
import JDBCControllers.IncomeExpensesController;
import JDBCControllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryManagement implements Initializable {
    @FXML
    public Button addCategoryBtn;
    @FXML
    public Button updateCategoryBtn;
    @FXML
    public Button printCategoryInfoBtn;
    @FXML
    public Button addResponsiblePersonBtn;
    @FXML
    public Button deleteCategoryBtn;
    @FXML
    public Button exitBtn;
    @FXML
    public ListView categoryList;
    @FXML
    public Button addIncomeBtn;
    @FXML
    public Button addExpenseBtn;
    public Button addRootCategoryBtn;

    private FinanceManagementSystem fms;

    private User user;
    private Category category;

    public void setFms(FinanceManagementSystem fms, User user) throws SQLException, ClassNotFoundException {
        this.fms = fms;
        this.user = user;
        fillCategoriesWithData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void fillCategoriesWithData() throws SQLException, ClassNotFoundException {
        categoryList.getItems().clear();
        if (CategoryController.categoriesExistInSystem(fms.getId())) {
            List<Category> allCategories = CategoryController.getAllCategories(fms.getId());
            allCategories.forEach(category -> categoryList.getItems().add(category.getName()));
        }
    }

    public void addCategoryForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (!CategoryController.isResponsibleUser(category.getId(), user.getId())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("Add Category Error");
            alertFirst.setContentText("You are not one of the responsible people of this category, so you can not add a subcategory");
            alertFirst.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddCategoryManagement.fxml"));
            Parent root = loader.load();

            AddCategoryManagement addCategoryManagement = loader.getController();
            addCategoryManagement.setFms(fms, user, category, categoryList);

            Stage stage = (Stage) addCategoryBtn.getScene().getWindow();
            stage.setTitle("Finance Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void updateCategoryForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (!CategoryController.isResponsibleUser(category.getId(), user.getId())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("Update Info");
            alertFirst.setContentText("You are not one of the responsible people of this category, so you can not update info");
            alertFirst.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UpdateCategoryManagement.fxml"));
            Parent root = loader.load();

            UpdateCategoryManagement updateCategoryManagement = loader.getController();
            updateCategoryManagement.setFms(fms, category, user);

            Stage stage = (Stage) addResponsiblePersonBtn.getScene().getWindow();
            stage.setTitle("Finance Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public Category getPressedCategoryName() throws SQLException, ClassNotFoundException {
        if(categoryList.getSelectionModel().getSelectedItems().isEmpty()){
            return null;
        }else{
            String categoryData = categoryList.getSelectionModel().getSelectedItem().toString();
            System.out.println(categoryData);
            return CategoryController.findCategory(categoryData, fms.getId());
        }
    }

/*    public List<Category> getMainCategories(int systemId) throws ClassNotFoundException, SQLException {
        List<Category> allCategories = new ArrayList<>();
        Connection connection = DatabaseUtils.connectToDb();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM category WHERE systemId = ? and parentCategoryId is NULL");
        ps.setInt(1, systemId);
        ResultSet categories = ps.executeQuery();

        while (categories.next()) {
            allCategories.add(new Category(categories.getInt("categoryId"),
                    categories.getString("name"),
                    categories.getString("description"),
                    categories.getDate("dateCategoryCreated"),
                    null));
        }
        return allCategories;
    }*/

    public void printCategoryInfoForm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
            if(category == null){chooseCategoryFirst();}
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Categories");
                alert.setHeaderText("Category Details");
                alert.setContentText(category.toString());
                alert.showAndWait();
            }
    }

    public void addResponsiblePersonForm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (!CategoryController.isResponsibleUser(category.getId(), user.getId())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("Add responsible person");
            alertFirst.setContentText("You are not one of the responsible people of this category, so you can not add other responsible people");
            alertFirst.showAndWait();
        } else {
            List<String> choices = UserController.getAllChoices();
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Add responsible user");
            dialog.setHeaderText("Choose user");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String userId = result.get();
                int id = Integer.parseInt(userId);
                User user1 = UserController.findUser(id);
                category.getResponsibleUsers().add(user1);
                CategoryController.addResponUser(category.getId(), id);
            }
        }
    }

    public void deleteCategoryForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (CategoryController.subcategoriesExistInCategory(category.getId())) {
            showAlertHasSubcategories();
        }else{
            int id = category.getId();
            if(IncomeExpensesController.getExpenses(id).size() != 0){
                IncomeExpensesController.removeExpense(id);
            }
            if(IncomeExpensesController.getIncome(id).size() != 0){
                IncomeExpensesController.removeIncome(id);
            }
            CategoryController.removeCategory(category);
            fillCategoriesWithData();
        }
    }

    public void showAlertHasSubcategories() {
        Alert alertFirst = new Alert(Alert.AlertType.ERROR);
        alertFirst.setTitle("Error");
        alertFirst.setHeaderText("Delete Category Error");
        alertFirst.setContentText("Sorry, this category has subcategories, please delete firstly those subcategories");
        alertFirst.showAndWait();
    }

    public void showAlertNoAuthority() {
        Alert alertFirst = new Alert(Alert.AlertType.ERROR);
        alertFirst.setTitle("Error");
        alertFirst.setHeaderText("Delete Category Error");
        alertFirst.setContentText("You are not one of the responsible people of this category or company/system owner, so you can not delete this category");
        alertFirst.showAndWait();
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainSystemWindow.fxml"));
        Parent root = loader.load();

        MainSystemWindow mainLibraryWindow = loader.getController();
        mainLibraryWindow.setFms(fms, user);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addIncomeForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (!CategoryController.isResponsibleUser(category.getId(), user.getId())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("Add Income Error");
            alertFirst.setContentText("You are not one of the responsible people of this category, so you can not add income");
            alertFirst.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/IncomeManagement.fxml"));
            Parent root = loader.load();

            IncomeManagement incomeManagement = loader.getController();
            incomeManagement.setFms(fms, category, user);

            Stage stage = (Stage) addIncomeBtn.getScene().getWindow();
            stage.setTitle("Finance Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void addExpenseForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Category category = getPressedCategoryName();
        if(category == null){chooseCategoryFirst();}
        else if (!CategoryController.isResponsibleUser(category.getId(), user.getId())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("Add Expenses Error");
            alertFirst.setContentText("You are not one of the responsible people of this category, so you can not add expense");
            alertFirst.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ExpensesManagement.fxml"));
            Parent root = loader.load();

            ExpensesManagement expensesManagement = loader.getController();
            expensesManagement.setFms(fms, category, user);

            Stage stage = (Stage) addIncomeBtn.getScene().getWindow();
            stage.setTitle("Finance Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    private void reloadWindow() throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CategoryManagement.fxml"));
        Parent root = loader.load();

        CategoryManagement categoryManagement = loader.getController();
        categoryManagement.setFms(fms, user);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addRootCategoryForm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddRootCategoryManagement.fxml"));
        Parent root = loader.load();

        AddRootCategoryManagement addCategoryManagement = loader.getController();
        addCategoryManagement.setFms(fms, user, category, categoryList);

        Stage stage = (Stage) addRootCategoryBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void chooseCategoryFirst() {
        Alert alertFirst = new Alert(Alert.AlertType.ERROR);
        alertFirst.setTitle("Error");
        alertFirst.setContentText("You have to choose category from the list first!!!");
        alertFirst.showAndWait();
    }
}
