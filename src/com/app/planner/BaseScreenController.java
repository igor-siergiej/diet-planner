package com.app.planner;

import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.*;

public class BaseScreenController {

    protected static Profile profile;

    // should have a undo  redo methods here which implement functionality in ui using undoRedoStack
    protected void undo() {

    }

    protected void redo() {

    }

    // these methods can only be used on screens that do not require a Profile object to be carried
    // on to the next screen if the method is called from an event handler
    public void goToCreateAccountScreen(ActionEvent event) {
        goToScreen(event, "createaccountcontroller/CreateAccountScreen.fxml");
    }

    public void goToStartScreen(ActionEvent event) {
        goToScreen(event, "startscreencontroller/StartScreen.fxml");
    }

    public void goToProfileScreen(ActionEvent event) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreen(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialise(profile);
    }

    public void logOut(ActionEvent event) { // this method will open the profile screen window
        goToScreen(event, "startscreencontroller/StartScreen.fxml");
    }

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

    private void setWindow(ActionEvent event, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    protected FXMLLoader goToScreen(ActionEvent event, String fxmlFilePath) {
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

    public static void setLimitProgressBar(ProgressBar progressBar, float percentValueToTarget, boolean isOverMaximumDose) {
        if (percentValueToTarget >= 1 && isOverMaximumDose == false) { //targetDose hit but not over maximum dose
            progressBar.setStyle("-fx-accent: green;");
        } else if (isOverMaximumDose == true) {
            progressBar.setStyle("-fx-accent: red"); // if over maximum does
        } else {
            progressBar.setStyle("-fx-accent: #007bff;"); // not 100% but not over maximum dose
        }
    }

    protected void setToggleGroupHandler(ToggleGroup toggleGroup) {
        // so that the user cannot unselect an already selected toggle group
        toggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    protected void createErrorNotification(Node owner, String text) {
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

    // this will be used for saving to file or db for instant feedback
    protected void createCorrectNotification(Node owner, String text) {
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
}
