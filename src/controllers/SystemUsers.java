package controllers;

import JDBCControllers.CategoryController;
import JDBCControllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.FinanceManagementSystem;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SystemUsers implements Initializable {
    @FXML
    public Button deleteUserBtn;
    @FXML
    public Button exitBtn;
    @FXML
    public ListView usersList;

    private FinanceManagementSystem fms;
    private User user;

    public void setFms(FinanceManagementSystem fms, User user) throws SQLException, ClassNotFoundException {
        this.fms = fms;
        this.user = user;
        fillListWithData();
    }

    private void fillListWithData() throws SQLException, ClassNotFoundException {
        usersList.getItems().clear();

        List<User> allUsers = UserController.getAllSysUsers(fms.getId());
        allUsers.forEach(user -> usersList.getItems().add(user.getName()));
    }

    public void deleteUser(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        User user = getPressedUser();
        if (CategoryController.subcategoriesExistInCategory(user.getId())) {
        } else {
            UserController.remove(user);
            fillListWithData();
        }
    }

    public User getPressedUser() throws SQLException, ClassNotFoundException {
        String user = usersList.getSelectionModel().getSelectedItem().toString();
        System.out.println(user);
        return UserController.findUser(user);
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        loadMainWindow();
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

    public void reloadWindow() throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SystemUsers.fxml"));
        Parent root = loader.load();

        SystemUsers systemUsers = loader.getController();
        systemUsers.setFms(fms, user);

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
}

