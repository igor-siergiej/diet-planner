package com.app.planner;

import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.createprofilecontroller.CreateProfileController;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public void goToViewNutrientsScreen(ActionEvent event) {
        ViewNutrientsController viewNutrientsController = goToScreen(event, "viewnutrientscontroller/ViewNutrientsScreen.fxml").getController();
        viewNutrientsController.initialise();
    }

    public void goToAddEntryScreen(ActionEvent event) {
        AddEntryController addEntryController = goToScreen(event, "addentrycontroller/AddEntryScreen.fxml").getController();
        addEntryController.initialise();
        // should the fxmlFilePath be hardcoded values instead?
    }

    public void goToProfileScreen(ActionEvent event) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreen(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialise();
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise( null);
    }

    public void logOut(ActionEvent event) { // this method will open the profile screen window
        goToScreen(event, "startscreencontroller/StartScreen.fxml");
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

    public void setShowPasswordInit(TextField showPasswordTextField, RadioButton showPasswordButton, PasswordField passwordField) {
        showPasswordTextField.setManaged(false);
        showPasswordTextField.setVisible(false);


        // Bind properties. Toggle textField and passwordField
        // visibility and managability properties mutually when checkbox's state is changed.
        // Because we want to display only one component (textField or passwordField)
        // on the scene at a time.
        showPasswordTextField.managedProperty().bind(showPasswordButton.selectedProperty());
        showPasswordTextField.visibleProperty().bind(showPasswordButton.selectedProperty());

        passwordField.managedProperty().bind(showPasswordButton.selectedProperty().not());
        passwordField.visibleProperty().bind(showPasswordButton.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        showPasswordTextField.textProperty().bindBidirectional(passwordField.textProperty());
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
