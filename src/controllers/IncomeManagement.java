package controllers;

import JDBCControllers.IncomeExpensesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.FinanceManagementSystem;
import model.Income;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class IncomeManagement implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public Button addBtn;
    @FXML
    public TextField amountField;
    @FXML
    public TextField issuerField;

    private Category category;
    private FinanceManagementSystem fms;
    private User user;

    public void setFms(FinanceManagementSystem fms, Category category, User user) {
        this.fms = fms;
        this.category = category;
        this.user = user;
    }

    public void exit(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        loadMainWindow();
    }

    private void loadMainWindow() throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CategoryManagement.fxml"));
        Parent root = loader.load();

        CategoryManagement categoryManagement = loader.getController();
        categoryManagement.setFms(fms, user);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Income income = (new Income(issuerField.getText(), Integer.parseInt(amountField.getText()), new Date(), category));
        IncomeExpensesController.create(income);
        category.getIncome().add(income);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Income");
        alert.setContentText("Income was added successfully");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

