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
import javafx.scene.control.ListView;
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

public class AddCategoryManagement implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public Button addBtn;
    @FXML
    public TextField newCategoryName;
    @FXML
    public TextField categoryDescription;

    private Category parentCategory;
    private Category category;
    private FinanceManagementSystem fms;
    private User user;
    private ListView categoryList;

    public void setFms(FinanceManagementSystem fms, User user, Category category, ListView categoryList) {
        this.fms = fms;
        this.user = user;
        this.category = category;
        this.categoryList = categoryList;
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

    public void add(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (newCategoryName.getText() != null && !CategoryController.categoryExist(newCategoryName.getText())) {

            Category newCategory = new Category(newCategoryName.getText(), categoryDescription.getText(), user, category, fms);

            parentCategory = category;

            newCategory.setDateCreated(new Date());
            CategoryController.createCategory(newCategory);

            CategoryController.updateParentCategory(CategoryController.findCategory(newCategoryName.getText(), fms.getId()), category.getId());

            CategoryController.addResponUser(CategoryController.findCategory(newCategoryName.getText(), fms.getId()).getId(), user.getId());

            newCategory.getResponsibleUsers().add(user);

            category.getSubcategories().add(newCategory);

            categoryList.getItems().add(newCategoryName.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System Categories");
            alert.setContentText("Subcategory was added successfully");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error with new category");
            alert.setContentText("New category name already exist");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
