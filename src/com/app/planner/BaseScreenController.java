package com.app.planner;

import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.*;

public class BaseScreenController {

    public FXMLLoader goToScreenWithProfile(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setWindow(event, root);
        return loader;
    }
    public void goToCreateAccountScreen(ActionEvent event) {
        goToScreen(event, "startscreencontroller/CreateAccountScreen.fxml");
    }

    public void goToStartScreen(ActionEvent event) {
        goToScreen(event, "startscreencontroller/StartScreen.fxml");
    }

    public FXMLLoader goToScreen(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setWindow(event, root);
        return loader;
    }

    public void goToProfileScreen(ActionEvent event, Profile profile) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreen(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialise(profile);
    }

    public void setWindow(ActionEvent event, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    public void createErrorNotification(Node owner, String text) {
        ImageView img = new ImageView(new Image("com/app/planner/img/error.png"));
        img.setFitHeight(50);
        img.setFitWidth(50);
        Notifications.create()
                .title("Something went wrong!")
                .owner(owner)
                .text(text)
                .graphic(img)
                .threshold(3, Notifications.create().title("Collapsed Notification"))
                .show();
    }

    public void createWarningNotification() {}
}
