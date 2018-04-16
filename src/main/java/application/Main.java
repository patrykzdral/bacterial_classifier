package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{

    private static Stage mainStage;
    @Override
    public void start(Stage primaryStage) {
        try {
            setMainStage(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/chooseDatabase.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.getIcons().add(new Image("/image/bacteria.png"));
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();


            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        Main.mainStage = mainStage;
    }


}
