package controllers;

import JDBCControllers.UserController;
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
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserInfoUpdate implements Initializable {

    @FXML
    public Button exitBtn;
    @FXML
    public Button infoBtn;
    public ListView infoList;
    public TextField name;
    public TextField surname;
    public TextField email;
    public TextField phoneNumber;
    public TextField loginName;
    public TextField psw;

    private FinanceManagementSystem fms;
    private User user;

    public void setFms(FinanceManagementSystem fms, User user){
        this.fms = fms;
        this.user = user;
        fillPersonalData();
    }

    private void fillPersonalData() {

        if(user != null){
            name.setText(user.getName());
            surname.setText(user.getSurname());
            email.setText(user.getEmail());
            phoneNumber.setText(user.getPhoneNumber());
            loginName.setText(user.getLoginName());
            psw.setText(user.getPassword());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void updateInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        user.setName(name.getText());
        user.setSurname(surname.getText());
        user.setEmail(email.getText());
        user.setPhoneNumber(phoneNumber.getText());
        user.setLoginName(loginName.getText());
        user.setPassword(psw.getText());

        UserController.updateUserInfo(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User update");
        alert.setContentText("User was updated successfully");
        alert.showAndWait();
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainSystemWindow.fxml"));
        Parent root = loader.load();

        MainSystemWindow mainLibraryWindow = loader.getController();
        mainLibraryWindow.setFms(fms,user);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

}


