package controllers;

import JDBCControllers.FMSController;
import JDBCControllers.UserController;
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
import model.FinanceManagementSystem;
import model.User;
import utils.JDBCConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpPage implements Initializable {
    private FinanceManagementSystem fms;

    @FXML
    public Button signUpBtn;
    @FXML
    public TextField nameId;
    @FXML
    public TextField surnameId;
    @FXML
    public TextField pphoneId;
    @FXML
    public TextField pemailId;
    @FXML
    public TextField plognameId;
    @FXML
    public TextField ppswId;

    private boolean isIndividual;

    private User user;

    public void setFms(FinanceManagementSystem fms, boolean isIndividual) {
        this.fms = fms;
        this.isIndividual = isIndividual;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void signup(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        System.out.println(fms.getName());

        fms = FMSController.findSystem(fms.getId());

        if (nameId.getText().trim().isEmpty() || surnameId.getText().trim().isEmpty() || pemailId.getText().trim().isEmpty() || pphoneId.getText().trim().isEmpty() || plognameId.getText().trim().isEmpty() || ppswId.getText().trim().isEmpty()) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("SignUp Error");
            alertFirst.setContentText("Some fields are empty");
            alertFirst.showAndWait();
        } else if (UserController.userExists(plognameId.getText())) {
            Alert alertFirst = new Alert(Alert.AlertType.ERROR);
            alertFirst.setTitle("Error");
            alertFirst.setHeaderText("SignUp Error");
            alertFirst.setContentText("Logname already exist");
            alertFirst.showAndWait();
        } else {

            System.out.println(isIndividual);
            user = new User(nameId.getText(),
                    surnameId.getText(), pemailId.getText(),
                    pphoneId.getText(),
                    isIndividual,
                    plognameId.getText(),
                    ppswId.getText(),
                    fms);
            UserController.create(user);
            fms.getUsers().add(user);
            System.out.println(user.getName());
            loadMainWindow();
        }
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainSystemWindow.fxml"));
        Parent root = loader.load();

        MainSystemWindow mainLibraryWindow = loader.getController();
        mainLibraryWindow.setFms(fms, user);

        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
