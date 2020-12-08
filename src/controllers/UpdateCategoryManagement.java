package controllers;

import JDBCControllers.CategoryController;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateCategoryManagement implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public TextField newCategoryName;
    @FXML
    public TextField categoryDescription;

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

    public void update(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (newCategoryName.getText().equals("") || CategoryController.categoryExist(newCategoryName.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error with new category name update");
            alert.setContentText("New category name already exist or fields are empty");
            alert.showAndWait();
        } else {
            category.setName(newCategoryName.getText());
            category.setDateModified(new Date());
            category.setDescription(categoryDescription.getText());
            CategoryController.update(category);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category update");
            alert.setContentText("Category was updated successfully");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


