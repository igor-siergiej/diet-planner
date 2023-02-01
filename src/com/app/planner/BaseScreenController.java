package com.app.planner;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.Notifications;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static javafx.collections.FXCollections.observableArrayList;

public class BaseScreenController {

    protected static Profile profile;
    protected static UndoRedoStack<String> undoRedoStack = new UndoRedoStack<>();

    @FXML
    protected static Pane mainPane;

    //TODO should all the methods here be protected?
    @FXML
    public void initialize(Pane mainPane, ToggleGroup toggleGroup, ToggleButton toggleButton, Button undoButton, Button redoButton) {
        BaseScreenController.mainPane = mainPane;
        setToggleGroupHandler(toggleGroup); // handler to make it impossible to unToggle current ToggleButton
        toggleButton.setSelected(true); // load the current screen with the current screen ToggleButton pressed

        BooleanBinding canUndo = Bindings.createBooleanBinding(() -> undoRedoStack.canUndo());
        undoButton.disableProperty().bind(canUndo.not()); // setting undo button disable
        BooleanBinding canRedo = Bindings.createBooleanBinding(() -> undoRedoStack.canRedo());
        redoButton.disableProperty().bind(canRedo.not()); // setting redo button disable
    }

    public void undo(ActionEvent event) {
        goToScreen(event,undoRedoStack.undo());
    }

    public void redo(ActionEvent event) {
        goToScreen(event,undoRedoStack.redo());
    }

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

    public void goToGraphViewScreen(ActionEvent event) {
        goToScreen(event, "graphviewcontroller/GraphViewScreen.fxml");
    }

    protected Optional<ButtonType> createYesNoAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.getDialogPane().getStylesheets().add("/com/app/planner/style.css");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.APPLY);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);

        return alert.showAndWait();
    }

    public void logOut(ActionEvent event) { // this method will open the profile screen window
        Optional<ButtonType> option = createYesNoAlert(Alert.AlertType.CONFIRMATION, "Warning!", "Log out?", "Are you sure you want to log out?");

        if (option.get() == null) {
            return;
        } else if (option.get().getButtonData() == ButtonBar.ButtonData.APPLY) {
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
        // this method exists to prevent adding to undoRedoStack before loading profile Screen
    }

    public static void updateMacroUI(float calories, float fat, float carbs, float protein, Label carbsLabel, Label fatLabel, Label proteinLabel, Label calorieLabel,
                                     ProgressBar calorieProgressBar, ProgressBar carbsProgressBar, ProgressBar proteinProgressBar, ProgressBar fatProgressBar, PieChart caloriePieChart) {
        DailyIntake dailyIntake = profile.getDailyIntake();

        float carbsGoal = dailyIntake.getTargetDose("Carbohydrates");
        float fatGoal = dailyIntake.getTargetDose("Fat");
        float proteinGoal = dailyIntake.getTargetDose("Protein");
        float calorieGoal = dailyIntake.getTargetDose("Energy (kcal)");

        // TODO figure out colours for max doses vs if someone is 20x over their fat goal, shouldn't be green right?

        float carbsMaxDose = dailyIntake.getMaximumDose("Carbohydrates");
        float fatMaxDose = dailyIntake.getMaximumDose("Fat");
        float calorieMaxDose = dailyIntake.getMaximumDose("Energy (kcal)");
        float proteinMaxDose = dailyIntake.getMaximumDose("Protein");

        carbsLabel.setText(carbs + "/" + carbsGoal);
        fatLabel.setText(fat + "/" + fatGoal);
        proteinLabel.setText(protein + "/" + proteinGoal);
        calorieLabel.setText(calories + "/" + calorieGoal);

        // set progressbar progress
        float percentOfCalories = calories / calorieGoal;
        calorieProgressBar.setProgress(percentOfCalories);
        setLimitProgressBar(calorieProgressBar, percentOfCalories, calories > calorieMaxDose);

        float percentOfCarbs = carbs / carbsGoal;
        carbsProgressBar.setProgress(percentOfCarbs);
        setLimitProgressBar(carbsProgressBar, percentOfCarbs, carbs > carbsMaxDose);

        float percentOfFat = fat / fatGoal;
        fatProgressBar.setProgress(percentOfFat);
        setLimitProgressBar(fatProgressBar, percentOfFat, fat > fatMaxDose);

        float percentOfProtein = protein / proteinGoal;
        proteinProgressBar.setProgress(percentOfProtein);
        setLimitProgressBar(proteinProgressBar, percentOfProtein, protein > proteinMaxDose);

        // set piechart values
        setPieChartValues(fat, carbs, protein, caloriePieChart);
    }

    protected static void setPieChartValues(float fat, float carbs, float protein, PieChart caloriePieChart) {
        float total = fat + carbs + protein;
        ObservableList<PieChart.Data> pieChartData =
                observableArrayList( // s = visual name, v = value where percentage will be calculated automatically
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein));

        caloriePieChart.setData(pieChartData);
        caloriePieChart.setLegendVisible(false);
    }

    @NotNull
    public static FXMLLoader getLoader(ActionEvent event, String fxmlFilePath) {
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

    public void setShowPasswordHandlers(TextField showPasswordTextField, RadioButton showPasswordButton, PasswordField passwordField) {
        showPasswordTextField.setManaged(false);
        showPasswordTextField.setVisible(false);

        // group of handlers to implement show password button functionality

        showPasswordTextField.managedProperty().bind(showPasswordButton.selectedProperty());
        showPasswordTextField.visibleProperty().bind(showPasswordButton.selectedProperty());

        passwordField.managedProperty().bind(showPasswordButton.selectedProperty().not());
        passwordField.visibleProperty().bind(showPasswordButton.selectedProperty().not());

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

    public void createFloatTextFormatter(TextField textField, int length) {
        DecimalFormat format = new DecimalFormat("#.#");
        textField.setTextFormatter(new TextFormatter<>(c ->
        {
            String newText = c.getControlNewText();
            if (newText.isEmpty()) {
                return c;
            }

            if (newText.length() > length) {
                return null;
            }

            if (newText.equals("0") || newText.equals("00") || newText.equals("000") || newText.equals("0000")) {
                return null;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
    }

    public void createIntegerTextFormatter(TextField textField, int length) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() > length) {
                return null;
            }

            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }

            return null;
        };

        textField.setTextFormatter(
                new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
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
