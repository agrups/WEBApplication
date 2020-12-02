package controllers;/*
package fms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import fms.model.Category;
import fms.model.Company;
import fms.model.FinanceManagementSystem;
import fms.model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoUpdate implements Initializable {

    @FXML
    public Button exitBtn;
    @FXML
    public Button infoBtn;
    public ListView infoList;

    private FinanceManagementSystem fms;
    private Company company;
    private Person person;

    public void setFms(FinanceManagementSystem fms, Person person, Company company){
        this.fms = fms;
        this.person = person;
        this.company = company;
        fillPersonalData();
    }
    */
/*String companyName,
                   String phoneNumber,
                   String email,
                   String contactPersonName,
                   String contactPersonSurname,
                   String loginName,
                   String psw,
                   int companyId*//*


    private void fillPersonalData() {
        infoList.getItems().clear();

        if(person != null){
            infoList.getItems().add(person.getName());
            infoList.getItems().add(person.getSurname());
            infoList.getItems().add(person.getEmail());
            infoList.getItems().add(person.getPhoneNumber());
            infoList.getItems().add(person.getLoginName());
            infoList.getItems().add(person.getPsw());
            //cat.getIncome().forEach(inc -> incomeList.getItems().add(cat.getName() + " : " + inc.getPrice() + " " + inc.getDescription() + " " + inc.getIssuer()));
        }else{
            infoList.getItems().add(company.getCompanyName());
            infoList.getItems().add(company.getEmail());
            infoList.getItems().add(company.getPhoneNumber());
            infoList.getItems().add(company.getContactPersonName());
            infoList.getItems().add(company.getContactPersonSurname());
            infoList.getItems().add(company.getLoginName());
            infoList.getItems().add(company.getPsw());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void updateInfo(ActionEvent actionEvent) {

    }

    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainSystemWindow.fxml"));
        Parent root = loader.load();

        MainSystemWindow mainLibraryWindow = loader.getController();
        mainLibraryWindow.setFms(fms, person, company);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Category getPressedCategoryName(){
        String categoryData = infoList.getSelectionModel().getSelectedItem().toString();

        return fms.getCategories().stream().filter(b -> b.getName().equals(categoryData)).findFirst().orElse(null);
    }
}
*/
