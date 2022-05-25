package com.app.planner.startscreencontroller;

import com.app.planner.*;
import com.app.planner.createprofilecontroller.CreateProfileController;
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
    private TextField loginEmailTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private ProgressBar passwordStrengthProgressBar;

    @FXML
    private Button loginButton;

    @FXML
    private TextField registerEmailTextField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField registerRetypePasswordField;

    @FXML
    private Label registerEmailMessage;

    @FXML
    private Label registerRetypePasswordMessage;

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
    private Label loginEmailMessage;

    @FXML
    private RadioButton maxCharRadioButton;

    @FXML
    private Label loginPasswordMessage;

    public void goToCreateProfileScreenWithLogin(ActionEvent event, String email, String password) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialiseCredentials(email, password);
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml");
        //startScreenController.setAgeTextFieldEventHandler();
        //startScreenController.setCreateProfileButtonDisable();
    }

    //is this needed?
    private void initialize(String email, String password) {
        profile.setEmail(email);
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
        diary.addEntry(entry);
        diary.addEntry(entry);
        diary.addEntry(entry);
        diary.addEntry(entry);

        diary.addEntry(entry1);
        diary.addEntry(entry2);
        diary.addEntry(entry3);

        Profile profile = new Profile("","", "testProfile",0,0, 14, "female", true, false, diary, new Option());
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

        Profile profile = new Profile(null, null, profileName,0,0 ,age, sex, isPregnant, isBreastfeeding, new Diary(), new Option());
        //goToProfileScreen(event, profile);
    }

    public void showRegisterPrompt() {
        registerPrompt.setVisible(true);
    }

    public void hideRegisterPrompt() {
        registerPrompt.setVisible(false);
    }

    public void passwordStrengthHandler() {
        float passwordStrength = InputValidation.getPasswordStrength(registerPasswordField.getText());
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

    public void logIn() {
        String email = loginEmailTextField.getText();
        String password = loginPasswordField.getText();
        // clear styling
        loginEmailMessage.setText("");
        loginPasswordMessage.setText("");
        loginEmailTextField.setId("");
        loginPasswordField.setId("");
        if (InputValidation.emailValidation(email).equals(true)) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (DatabaseConnection.login(email, password)) {
                    //goToProfileScreen(event, DatabaseConnection.getProfileFromDb(username));
                } else {
                    loginEmailMessage.setText("Account does not exist");
                    loginEmailMessage.setId("warningLabel");
                    loginEmailTextField.setId("text-field-warning");
                    return;
                }
            } else {
                loginPasswordMessage.setText("Invalid Password");
                loginPasswordMessage.setId("errorLabel");
                loginPasswordField.setId("text-field-error");
                return;
            }
        } else {
            loginEmailMessage.setText("Invalid email format");
            loginEmailMessage.setId("errorLabel");
            loginEmailTextField.setId("text-field-error");
            return;
        }
    }

    public void register(ActionEvent event) {
        maxCharRadioButton.setSelected(true);
        String email = registerEmailTextField.getText();
        String password = registerPasswordField.getText();
        String retypePassword = registerRetypePasswordField.getText();
        registerEmailMessage.setText("");
        registerEmailTextField.setId("");
        registerPasswordField.setId("");
        registerRetypePasswordField.setId("");

        if (InputValidation.emailValidation(email).equals(true)) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (password.equals(retypePassword)) {
                    if (DatabaseConnection.register(email, password)) { // this will register if validation is ok?
                        goToCreateProfileScreenWithLogin(event, email, password);
                    } else {
                        registerEmailMessage.setText("An account with this email already exists");
                        registerEmailMessage.setId("warningLabel");
                        registerEmailTextField.setId("text-field-warning");
                        return;
                    }
                } else {
                    registerRetypePasswordMessage.setText("Passwords Not matching");
                    registerRetypePasswordField.setId("errorLabel");
                    registerRetypePasswordField.setId("text-field-error");
                    return;
                }
            } else {
                // change style id of radio buttons in grid pane
                registerPasswordField.setId("text-field-error");
                return;
            }
        } else {
            registerEmailMessage.setText("Invalid Email format");
            registerEmailMessage.setId("errorLabel");
            registerEmailTextField.setId("text-field-error");
            return;
        }
    }

    // TODO this method should be used for testing to bypass having to register a new account for each test.
    public void testRegister(ActionEvent event) {
        goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml");
    }

    //TODO eventually remove
    public void testLogin(ActionEvent event) {
        DatabaseConnection.login("testProfile", "testpass!I1");
        goToScreen(event, "ProfileScreen.fxml");
    }
}