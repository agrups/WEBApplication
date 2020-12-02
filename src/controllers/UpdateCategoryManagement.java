package controllers;/*
package fms.controllers;

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
import fms.model.Category;
import fms.model.Company;
import fms.model.FinanceManagementSystem;
import fms.model.Person;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private Person person;
    private Company company;

    public void setFms(FinanceManagementSystem fms, Category category, Person person, Company company){
        this.fms = fms;
        this.category = category;
        this.person = person;
        this.company = company;
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CategoryManagement.fxml"));
        Parent root = loader.load();

        CategoryManagement categoryManagement = loader.getController();
        categoryManagement.setFms(fms, person, company);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void update(ActionEvent actionEvent) throws IOException {
       // if(!newCategoryName.getText().equals("")){
            if(fms.checkIfCategoryExists(newCategoryName.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error with new category name update");
                alert.setContentText("New category name already exist");
                alert.showAndWait();
            }else{
*/
/*                category.setName(newCategoryName.getText());
                category.setDateModified(LocalDate.now());*//*

                fms.getCategoryWithName(category.getName()).setName(newCategoryName.getText());
                fms.getCategoryWithName(category.getName()).setDateModified(LocalDate.now());
            }
       // }
       // if(!categoryDescription.getText().equals("")){
*/
/*            category.setDescription(categoryDescription.getText());
            category.setDateModified(LocalDate.now());*//*

        fms.getCategoryWithName(category.getName()).setDescription(categoryDescription.getText());
        fms.getCategoryWithName(category.getName()).setDateModified(LocalDate.now());
       // }

        //loadMainWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
*/
