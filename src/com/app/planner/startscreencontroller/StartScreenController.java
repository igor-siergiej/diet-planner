package com.app.planner.startscreencontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class StartScreenController extends BaseScreenController {
    @FXML
    public void initialize() {

        //Calendar poke code
        /*DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        mainPane.getChildren().add(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New Value: " + newValue);
        });*/
    }

    Profile profile;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField loginUsernameTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private ProgressBar passwordStrengthProgressBar;

    @FXML
    private Button loginButton;

    @FXML
    private TextField registrationUsernameTextField;

    @FXML
    private PasswordField registrationPasswordField;

    @FXML
    private PasswordField registrationRetypePasswordField;

    @FXML
    private Label registerUsernameMessage;

    @FXML
    private Label registerPasswordMessage;

    @FXML
    private Label loginMessage;

    @FXML
    private ComboBox sexComboBox;

    @FXML
    private Pane femalePane;

    @FXML
    private TextField profileNameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private ComboBox breastfeedingComboBox;

    @FXML
    private ComboBox pregnantComboBox;

    @FXML
    private Button createProfileButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextArea messageTextField;

    @FXML
    private TextArea registerPrompt;

    @FXML
    private Label usernameMessage;

    @FXML
    private Label passwordMessage;

    public void goToCreateProfileScreenWithLogin(ActionEvent event, String username, String password) { // this method will open the profile screen window
        StartScreenController startScreenController = goToScreen(event, "mainscreencontroller/CreateProfileScreen.fxml").getController();
        startScreenController.initialize(username, password);
        startScreenController.setAgeTextFieldEventHandler();
        startScreenController.setCreateProfileButtonDisable();
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        StartScreenController startScreenController = goToScreen(event, "mainscreencontroller/CreateProfileScreen.fxml").getController();
        startScreenController.setAgeTextFieldEventHandler();
        startScreenController.setCreateProfileButtonDisable();
    }

    public void goToProfileScreen(ActionEvent event, Profile profile) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreen(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        //profileScreenController.initialise(profile);
    }

    //is this needed?
    private void initialize(String username, String password) {
        profile.setUsername(username);
        profile.setPassword(password);
    }

    public void setCreateProfileButtonDisable() {
        createProfileButton.disableProperty().bind(profileNameTextField.textProperty().isEmpty().or(ageTextField.textProperty().isEmpty()).or(sexComboBox.valueProperty().isNull()));
        //disabling the create profile button until the form is filled out

        breastfeedingComboBox.getSelectionModel().select(1); //setting default value for comboBoxes
        pregnantComboBox.getSelectionModel().select(1);
    }

    // same as above
    /*public void setRegistrationButtonDisable() {
        registerButton.disableProperty().bind(registrationUsernameTextField.textProperty().isEmpty().or(registrationPasswordField.textProperty().isEmpty()).or(registrationRetypePasswordField.textProperty().isEmpty()));
    }*/

    //needs to be in a different class because of ui overhaul
    /*public void sendFeedback() {
        DatabaseConnection.sendFeedback(nameTextField.getText(), emailTextField.getText(), messageTextField.getText());
    }
*/
    //TODO move this to test class in the future
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
        diary.addEntry(entry1);
        diary.addEntry(entry2);
        diary.addEntry(entry3);

        Profile profile = new Profile("", "", "testProfile", 14, "female", true, false, diary, new Option());
        goToProfileScreen(event, profile);
    }

    // will be moved to different controller class
    public void setAgeTextFieldEventHandler() {
        ageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            ageTextField.setText(InputValidation.ageValidation(newValue));
        });
    }

    // will be moved to different controller class
    public void createNewProfile(ActionEvent event) {
        String sex = (String) sexComboBox.getValue();
        int age = Integer.parseInt(ageTextField.getText());
        String profileName = profileNameTextField.getText();
        boolean isPregnant = false;
        boolean isBreastfeeding = false;

        if (sex.equals("Female")) {
            if (pregnantComboBox.getValue().equals("Yes")) {
                isPregnant = true;
            }
            if (breastfeedingComboBox.getValue().equals("Yes")) {
                isBreastfeeding = true;
            }
        }

        Profile profile = new Profile(null, null, profileName, age, sex, isPregnant, isBreastfeeding, new Diary(), new Option());
        //goToProfileScreen(event, profile);
    }

    public void showRegisterPrompt() {
        registerPrompt.setVisible(true);
    }

    public void hideRegisterPrompt() {
        registerPrompt.setVisible(false);
    }

    public void passwordStrengthHandler() {
        float passwordStrength = InputValidation.getPasswordStrength(registrationPasswordField.getText());
        if (passwordStrength < 0.6) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarWeak");
        } else if (passwordStrength < 1) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarOk");
        } else {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarStrong");
        }
        passwordStrengthProgressBar.setProgress(passwordStrength);
    }

    public void loadFemalePane() {
        String sex = (String) sexComboBox.getValue();
        if (sex.equals("Female")) {
            femalePane.setVisible(true);
        } else {
            femalePane.setVisible(false);
        }
    }

    public void loadProfile(ActionEvent event) { // this is the method to call when load profile from file is pressed
        Profile profile = new Profile();
        File file = Main.chooseLoadFile(mainPane);
        if (!(file == null)) {
            profile.loadFromFile(file);
            //goToProfileScreen(event, profile);
        } else {
            System.out.println("Choosing File closed!");
        }
    }

    public void logIn(ActionEvent event) {
        String username = loginUsernameTextField.getText();
        String password = loginPasswordField.getText();
        // clear styling
        usernameMessage.setText("");
        passwordMessage.setText("");
        loginUsernameTextField.setId("");
        loginPasswordField.setId("");
        if (InputValidation.usernameValidation(username).equals("valid")) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (DatabaseConnection.login(username, password)) {
                    //goToProfileScreen(event, DatabaseConnection.getProfileFromDb(username));
                } else {
                    usernameMessage.setText("Account does not exist");
                    usernameMessage.setId("warningLabel");
                    loginUsernameTextField.setId("text-field-warning");
                    return;
                }
            } else {
                passwordMessage.setText("Invalid Password");
                passwordMessage.setId("errorLabel");
                loginPasswordField.setId("text-field-error");
                return;
            }
        } else {
            usernameMessage.setText("Invalid Username");
            usernameMessage.setId("errorLabel");
            loginUsernameTextField.setId("text-field-error");
            return;
        }
    }

    public void register(ActionEvent event) {
        String username = registrationUsernameTextField.getText();
        String password = registrationPasswordField.getText();
        String retypePassword = registrationRetypePasswordField.getText();
        registerUsernameMessage.setText("");
        registerPasswordMessage.setText("");
        registrationUsernameTextField.setId("");
        registrationPasswordField.setId("");
        registrationRetypePasswordField.setId("");

        if (InputValidation.usernameValidation(username).equals("valid")) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (password.equals(retypePassword)) {
                    if (DatabaseConnection.register(username, password)) {
                        goToCreateProfileScreenWithLogin(event, username, password);
                    } else {
                        registerUsernameMessage.setText("Username Already exists");
                        registerUsernameMessage.setId("warningLabel");
                        registrationUsernameTextField.setId("text-field-warning");
                        return;
                    }
                } else {
                    registerPasswordMessage.setText("Passwords Not matching");
                    registerPasswordMessage.setId("errorLabel");
                    registrationRetypePasswordField.setId("text-field-error");
                    return;
                }
            } else {
                registerPasswordMessage.setText(InputValidation.passwordValidation(password));
                registerPasswordMessage.setId("errorLabel");
                registrationPasswordField.setId("text-field-error");
                return;
            }
        } else {
            registerUsernameMessage.setText(InputValidation.usernameValidation(username));
            registerUsernameMessage.setId("errorLabel");
            registrationUsernameTextField.setId("text-field-error");
            return;
        }
    }

    //TODO eventually remove
    public void testLogin(ActionEvent event) {
        DatabaseConnection.login("testProfile", "testpass!I1");
        goToScreen(event, "ProfileScreen.fxml");
    }
}