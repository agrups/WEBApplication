package controllers;

import JDBCControllers.CategoryController;
import JDBCControllers.IncomeExpensesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IncomeExpensesLists implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public ListView incomeList;
    @FXML
    public ListView expensesList;

    private FinanceManagementSystem fms;
    private User user;

    public void setFms(FinanceManagementSystem fms, User user) throws SQLException, IOException, ClassNotFoundException {
        this.fms = fms;
        this.user = user;
        fillIncomeWithData();
        fillExpensesWithData();
    }

    private void fillIncomeWithData() throws SQLException, ClassNotFoundException, IOException {
        incomeList.getItems().clear();

        for (Category cat : CategoryController.getAllCategories(fms.getId())) {
            for (Income income : IncomeExpensesController.getIncome(cat.getId())) {
                if (income == null) {

                } else {
                    incomeList.getItems().add(cat.getName() + " : " + income.getPrice() + " " + income.getName() + " " + income.getDateCreated());
                }
            }
        }
    }

    private void fillExpensesWithData() throws SQLException, ClassNotFoundException, IOException {
        expensesList.getItems().clear();

        for (Category cat : CategoryController.getAllCategories(fms.getId())) {
            for (Expenses ex : IncomeExpensesController.getExpenses(cat.getId())) {
                if (ex == null) {

                } else {
                    expensesList.getItems().add(cat.getName() + " : " + ex.getPrice() + " " + ex.getName() + " " + ex.getDateCreated());
                }
            }
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
    }
}


