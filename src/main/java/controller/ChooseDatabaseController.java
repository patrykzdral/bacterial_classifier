package controller;

import application.Main;
import customMessageBox.CustomMessageBox;
import database.dao.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseDatabaseController implements Initializable {
    public TextField textFieldDataBaseName;
    public TextField textFieldTextFieldDataBaseIp;
    public TextField textFieldDataBaseLogin;
    public PasswordField textFieldDataBasePassword;
    public Button buttonLoginToDefaultDataBase;
    public Button buttonLoginToNewDataBase;
    private CustomMessageBox customMessageBox;
    static DbConnection dbConnection;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox();

    }

    public void loginToDefault_onAction(ActionEvent actionEvent) {
        try {
            dbConnection= new DbConnection();
            enterApplication();
        } catch (SQLException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja zalogowania nie powiedzie się.", "Powód: Błąd połączenia SQL.").showAndWait();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loginToNew_onAction(ActionEvent actionEvent) {
            try {
                dbConnection= new DbConnection(textFieldDataBaseLogin.getText(),textFieldDataBasePassword.getText(),textFieldTextFieldDataBaseIp.getText(),textFieldDataBaseName.getText());
                enterApplication();
            } catch (SQLException e) {
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja zalogowania nie powiedzie się.", "Powód: Błąd połączenia SQL.").showAndWait();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    private void enterApplication() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/main.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = Main.getMainStage();
            stage.setScene(new Scene(parent));
        } catch (IOException ioEcx) {
            Logger.getLogger(ChooseDatabaseController.class.getName()).log(Level.SEVERE, null, ioEcx);

        }
    }

}
