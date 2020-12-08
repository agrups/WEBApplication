package controllers;

import JDBCControllers.CategoryController;
import JDBCControllers.IncomeExpensesController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainSystemWindow implements Initializable {

    @FXML
    public Button systemInfoBtn;
    @FXML
    public Button listUsersBtn;
    @FXML
    public Button balanceBtn;
    @FXML
    public Button manageCategoriesBtn;
    @FXML
    public Button updateYourInfoBtn;
    @FXML
    public Button incomeExpensesBtn;
    @FXML
    public Button exitBtn;

    private FinanceManagementSystem fms;

    private User user;

    public void setFms(FinanceManagementSystem fms, User user) {
        this.fms = fms;
        this.user = user;
        populateWithData();
    }

    private void populateWithData() {
    }

    public void showSystemInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println(user.getName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System Information");
        alert.setHeaderText("System Information");
        alert.setContentText(fms.toString());
        alert.showAndWait();
    }

    public void listUsersForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SystemUsers.fxml"));
        Parent root = loader.load();

        SystemUsers systemUsers = loader.getController();
        systemUsers.setFms(fms, user);

        Stage stage = (Stage) listUsersBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showBalance(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int balance = getAllIncome() - getAllExpenses();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Company Balance");
        alert.setHeaderText("Balance");
        alert.setContentText(String.valueOf(balance));
        alert.showAndWait();
    }

    private int getAllIncome() throws SQLException, ClassNotFoundException {
        int allIncome = 0;
        for (Category cat : CategoryController.getAllCategories(fms.getId())) {
            for (Income inc : IncomeExpensesController.getIncome(cat.getId())) {
                allIncome += inc.getPrice();
            }
        }
        return allIncome;
    }

    private int getAllExpenses() throws SQLException, ClassNotFoundException {
        int allExpenses = 0;
        for (Category cat : CategoryController.getAllCategories(fms.getId())) {
            for (Expenses expenses : IncomeExpensesController.getExpenses(cat.getId())) {
                System.out.println(expenses.getPrice());
                allExpenses += expenses.getPrice();
            }
        }
        return allExpenses;
    }

    public void manageCategoriesForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CategoryManagement.fxml"));
        Parent root = loader.load();

        CategoryManagement categoryManagement = loader.getController();
        categoryManagement.setFms(fms, user);

        Stage stage = (Stage) manageCategoriesBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateYourInfoForm(ActionEvent actionEvent) throws IOException {
        if (user != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UserInfoUpdate.fxml"));
            Parent root = loader.load();

            UserInfoUpdate userInfoUpdate = loader.getController();
            userInfoUpdate.setFms(fms, user);

            Stage stage = (Stage) updateYourInfoBtn.getScene().getWindow();
            stage.setTitle("Finance Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void manageIncomeExpensesForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/IncomeExpensesLists.fxml"));
        Parent root = loader.load();

        IncomeExpensesLists incomeExpensesManagement = loader.getController();
        incomeExpensesManagement.setFms(fms, user);

        Stage stage = (Stage) incomeExpensesBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

}
