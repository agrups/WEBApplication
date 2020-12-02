package controllers;

import JDBCControllers.FMSController;
import JDBCControllers.UserController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;
import utils.JDBCConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {

    public static FinanceManagementSystem fms = new FinanceManagementSystem();
    private User user = new User();

    @FXML
    public Button signInBtn;
    @FXML
    public Button signUpBtn;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    int id;
    private boolean isIndividual;

    public void setFms(FinanceManagementSystem fms) {
        LoginPage.fms = fms;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.fms = chooseFMS();
            //this.fms = FMSController.getFinanceSystem("Kompanija");
            id= fms.getId();
            System.out.println(fms.getName());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        if (fms == null) {
            System.out.println("No information about system");
            Platform.exit();
        } else {
            userTypePopUp(fms);
        }
    }

    private FinanceManagementSystem chooseFMS() throws ClassNotFoundException, SQLException {
        List<String> choices = FMSController.getAllChoices();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Finance management system");
        dialog.setHeaderText("Choose your system");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String companyName = result.get();

            FinanceManagementSystem gg = FMSController.getFinanceSystem(companyName);

            return gg;
        }
        return null;
    }

    private void userTypePopUp(FinanceManagementSystem fms) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Finance Management System");
        alert.setHeaderText("What type of user are you.");
        alert.setContentText("Please choose");

        ButtonType buttonTypePerson = new ButtonType("Person");
        ButtonType buttonTypeCompany = new ButtonType("Company");

        alert.getButtonTypes().setAll(buttonTypePerson, buttonTypeCompany);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypePerson) {
            isIndividual = true;
        } else if (result.get() == buttonTypeCompany) {
            isIndividual = false;
        }
    }

    public void createUser(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        fms = FMSController.findSystem(id);
        System.out.println(isIndividual);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SignUpPage.fxml"));
        Parent root = loader.load();

        SignUpPage signUpPage = loader.getController();
        signUpPage.setFms(fms, isIndividual);

        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void validateUser(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        fms = FMSController.findSystem(id);
        if (loginField.getText().isEmpty() || pswField.getText().isEmpty()) {
            noSuchUser();
        }
        user = UserController.findUser(loginField.getText(), pswField.getText());
        if (user != null) {
            System.out.println(user.getName());
            loadMainWindow();
        } else {
            noSuchUser();
        }
    }

    private void noSuchUser() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Finance Management System");
        alert.setHeaderText("No such user");
        alert.setContentText("User with given credentials was not found. Contact the administrator.");
        alert.showAndWait();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainSystemWindow.fxml"));
        Parent root = loader.load();

        MainSystemWindow mainLibraryWindow = loader.getController();
        mainLibraryWindow.setFms(fms, user);

        Stage stage = (Stage) signInBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
