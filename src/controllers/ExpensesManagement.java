package controllers;

import JDBCControllers.IncomeExpensesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.Expenses;
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ExpensesManagement implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public Button addBtn;
    @FXML
    public TextField amountField;
    @FXML
    public TextField receiverField;

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

        IncomeExpensesController.createExpenses(new Expenses(receiverField.getText(), Integer.parseInt(amountField.getText()), new Date(), category));
        loadMainWindow();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

