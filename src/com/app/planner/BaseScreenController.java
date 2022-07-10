package com.app.planner;

import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.createprofilecontroller.CreateProfileController;
import com.app.planner.profiledetailscontroller.ProfileDetailsController;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Optional;

public class BaseScreenController {

    protected static Profile profile;
    protected static UndoRedoStack<String> undoRedoStack = new UndoRedoStack<>();
    protected static String currentScreen;

    @FXML
    protected static Pane mainPane;

    @FXML
    public void initialize(Pane mainPane, ToggleGroup toggleGroup, ToggleButton button, Button undoButton, Button redoButton) {
        BaseScreenController.mainPane = mainPane;
        setToggleGroupHandler(toggleGroup);
        button.setSelected(true); // since we are in profileScreen set the toggleButton to be selected

        // TODO add this to every Controller, ideally figure out a baseclass initialiZe
        BooleanBinding canUndo = Bindings.createBooleanBinding(() -> undoRedoStack.canUndo());
        undoButton.disableProperty().bind(canUndo.not());
        BooleanBinding canRedo = Bindings.createBooleanBinding(() -> undoRedoStack.canRedo());
        redoButton.disableProperty().bind(canRedo.not());
    }

    // should have a undo  redo methods here which implement functionality in ui using undoRedoStack
    public void undo(ActionEvent event) {
        goToScreen(event,undoRedoStack.undo());
    }

    public void redo(ActionEvent event) {
        goToScreen(event,undoRedoStack.redo());
    }

    // these methods can only be used on screens that do not require a Profile object to be carried
    // on to the next screen if the method is called from an event handler
    public void goToCreateAccountScreen(ActionEvent event) {
        goToScreenWithoutAddingScreen(event, "createaccountcontroller/CreateAccountScreen.fxml");
    }

    public void goToStartScreen(ActionEvent event) {
        goToScreenWithoutAddingScreen(event, "startscreencontroller/StartScreen.fxml");
    }

    public void goToViewNutrientsScreen(ActionEvent event) {
        goToScreen(event, "viewnutrientscontroller/ViewNutrientsScreen.fxml");
    }

    public void goToAddEntryScreen(ActionEvent event) {
        goToScreen(event, "addentrycontroller/AddEntryScreen.fxml");
        // should the fxmlFilePath be hardcoded values instead?
    }

    public void goToProfileScreen(ActionEvent event) { // this method will open the profile screen window
        goToScreen(event, "profilescreencontroller/ProfileScreen.fxml");
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
         goToScreenWithoutAddingScreen(event, "createprofilecontroller/CreateProfileScreen.fxml");
    }

    public void goToProfileDetailsScreen(ActionEvent event) {
        goToScreen(event, "profiledetailscontroller/ProfileDetailsScreen.fxml");
    }

    public void logOut(ActionEvent event) { // this method will open the profile screen window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Log out?");
        alert.setContentText("Are you sure you want to log out?");

        alert.getDialogPane().getStylesheets().add("/com/app/planner/style.css");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.APPLY);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Remove default ButtonTypes
        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(yes, no);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            return;
        } else if (option.get() == yes) {
            goToScreen(event, "startscreencontroller/StartScreen.fxml");
        } else {
            return;
        }
    }

    private static void setWindow(ActionEvent event, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    public static FXMLLoader goToScreen(ActionEvent event, String fxmlFilePath) {
        undoRedoStack.addScreen(fxmlFilePath);
        return getLoader(event, fxmlFilePath);
    }

    public static FXMLLoader goToScreenWithoutAddingScreen(ActionEvent event, String fxmlFilePath) {
        return getLoader(event, fxmlFilePath);
    }

    @NotNull
    private static FXMLLoader getLoader(ActionEvent event, String fxmlFilePath) {
        System.out.println(undoRedoStack.toString());
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

    // maybe this should be done on a different thread so that it doesn't make the UI stuck
    public void saveProfileToDB() {
        if (DatabaseConnection.saveProfileToDb(profile.getEmail(),profile)) {
            createCorrectNotification(mainPane,"Profile saved successfully!");
        } else {
            createErrorNotification(mainPane,"An error occurred while saving profile to database");
        }
    }

    public void saveProfileToFile() {
        File file = Main.chooseSaveFile(mainPane);
        if (!(file == null)) {
            try {
                profile.saveToFile(file);
            } catch (Exception e) {
                createErrorNotification(mainPane,"An error has occurred while saving profile to file!");
            }
            createCorrectNotification(mainPane,"Profile saved successfully!");
        } else {
            createErrorNotification(mainPane,"File chooser has been closed!");
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
        ImageView img = new ImageView(new Image("com/app/planner/img/tick.png"));
        img.setFitHeight(50);
        img.setFitWidth(50);
        Notifications.create()
                .title("Success!")
                .owner(owner)
                .text(text)
                .graphic(img)
                .threshold(3, Notifications.create().title("Collapsed Notification"))
                .show();
    }
}
