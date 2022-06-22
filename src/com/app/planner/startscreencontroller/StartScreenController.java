package com.app.planner.startscreencontroller;

import com.app.planner.*;
import com.app.planner.createprofilecontroller.CreateProfileController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class StartScreenController extends BaseScreenController {

    //Calendar poke code
        /*DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        mainPane.getChildren().add(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New Value: " + newValue);
        });*/

    @FXML
    private Pane mainPane;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Label loginEmailMessage;

    @FXML
    private Label loginPasswordMessage;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.disableProperty().bind(Bindings.isEmpty(loginEmailTextField.textProperty()).or(Bindings.isEmpty(loginPasswordField.textProperty())));
        InputValidator inputValidator = new InputValidator();
        inputValidator.createEmailValidator(loginEmailTextField, loginEmailMessage);
        inputValidator.createPasswordValidator(loginPasswordField, loginPasswordMessage);
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise(null, null);
    }

    // TODO move this to test class in the future
    public void createTestProfile(ActionEvent event) {
        Diary diary = new Diary();

        Meal meal = new Meal();
        meal.setMealName("breakfast1");

        Meal meal1 = new Meal();
        meal1.setMealName("breakfast2");

        ArrayList<Food> data = Main.initialiseData();

        ArrayList<Food> foods = new ArrayList<>();
        foods.add(Main.sortedFoodSearch(data, "chicken").get(0));
        foods.add(Main.sortedFoodSearch(data, "liver").get(0));
        foods.add(Main.sortedFoodSearch(data, "eggs").get(0));
        meal.addFoods(foods, new ArrayList<>(Arrays.asList(100, 100, 100)));

        ArrayList<Food> foods1 = new ArrayList<>();
        foods1.add(Main.sortedFoodSearch(data, "chicken").get(0));
        foods1.add(Main.sortedFoodSearch(data, "liver").get(0));
        meal1.addFoods(foods1, new ArrayList<>(Arrays.asList(100, 100)));

        Entry entry = new Entry(meal, LocalDateTime.now(), EntryType.DINNER);
        Entry entry1 = new Entry(meal1, LocalDateTime.now().minusDays(1), EntryType.DINNER);
        Entry entry2 = new Entry(meal1, LocalDateTime.now().minusDays(2), EntryType.DINNER);
        Entry entry3 = new Entry(meal1, LocalDateTime.now().minusDays(3), EntryType.DINNER);

        diary.addEntry(entry);
        diary.addEntry(entry);
        diary.addEntry(entry);
        diary.addEntry(entry);
        diary.addEntry(entry);

        diary.addEntry(entry1);
        diary.addEntry(entry2);
        diary.addEntry(entry3);

        profile = new Profile("", "", "testProfile", 0, 0, 14, "female", true, false, diary, new Option());
        goToProfileScreen(event);
    }

    public void loadProfile(ActionEvent event) { // this is the method to call when load profile from file is pressed
        Profile profile = new Profile();
        File file = Main.chooseLoadFile(mainPane);
        if (!(file == null)) {
            profile.loadFromFile(file);
            goToProfileScreen(event);
        } else {
            System.out.println("Choosing File closed!");
        }
    }

    public void logIn(ActionEvent event) {
        String email = loginEmailTextField.getText();
        String password = loginPasswordField.getText();

        if (StringValidation.emailValidation(email).equals(StringValidation.RETURN_STRING)) {
            if (StringValidation.passwordValidation(password).equals(StringValidation.RETURN_STRING)) {
                if (DatabaseConnection.login(email, password)) {
                    profile = DatabaseConnection.getProfileFromDb(email);
                    goToProfileScreen(event);
                } else {
                    createErrorNotification(mainPane, "Account does not exist");
                    return;
                }
            } else {
                createErrorNotification(mainPane, StringValidation.passwordValidation(password));
                return;
            }
        } else {
            createErrorNotification(mainPane, "Invalid email format");
            return;
        }
    }

    //TODO eventually remove or remake for testing
    public void testLogin(ActionEvent event) {
        DatabaseConnection.login("testProfile", "testpass!I1");
        goToScreen(event, "ProfileScreen.fxml");
    }
}