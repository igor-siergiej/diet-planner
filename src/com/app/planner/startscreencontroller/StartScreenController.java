package com.app.planner.startscreencontroller;

import com.app.planner.*;
import com.app.planner.createprofilecontroller.CreateProfileController;
import javafx.application.Platform;
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

    Profile profile;
    Timer timer;
    Stack<NonInterruptableTask> emailTasks;
    NonInterruptableTask passwordTask;

    @FXML
    public void initialize() {
        loginButton.disableProperty().bind(Bindings.isEmpty(loginEmailTextField.textProperty()).or(Bindings.isEmpty(loginPasswordField.textProperty())));
        timer = new Timer();
        emailTasks = new Stack<>();
        passwordTask = createPasswordTask();

        //Calendar poke code
        /*DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        mainPane.getChildren().add(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New Value: " + newValue);
        });*/
    }

    @FXML
    private Pane mainPane;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private ProgressBar passwordStrengthProgressBar;

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
    private Label loginEmailMessage;

    @FXML
    private Label loginPasswordMessage;

    @FXML
    private Button loginButton;

    @FXML
    private RadioButton charLimitRadioButton;

    @FXML
    private RadioButton upperCaseRadioButton;

    @FXML
    private RadioButton lowerCaseRadioButton;

    @FXML
    private RadioButton numberRadioButton;

    @FXML
    private RadioButton specialCharRadioButton;

    public void goToCreateProfileScreenWithLogin(ActionEvent event, String email, String password) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise(email, password);
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise(null, null);
    }

    // is this needed?
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

    public abstract class NonInterruptableTask extends TimerTask {

        protected boolean isRunning = false;

        public boolean isRunning() {
            return isRunning;
        }

        protected abstract void doTaskWork();

        @Override
        public void run() {
            isRunning = true;
            doTaskWork();
            isRunning = false;
        }
    }

    public void handleLoginEmailTextfield() {
        System.out.println(emailTasks);
        if (emailTasks.size() > 0) {
            emailTasks.pop();
            loginEmailMessage.setText("");
            loginEmailTextField.setId("");
            timer.cancel();
            timer.purge();
            System.out.println("task cancelled");
            timer = new Timer();
            timer.schedule(createEmailTask(), 1000);
        } else {
            timer.schedule(createEmailTask(), 1000);
            loginEmailMessage.setText("");
            loginEmailTextField.setId("");
        }
    }

    private NonInterruptableTask createEmailTask() {
        NonInterruptableTask task = new NonInterruptableTask() {
            @Override
            protected void doTaskWork() {
                Platform.runLater(() -> {
                    String email = loginEmailTextField.getText().trim();
                    if (InputValidation.emailValidation(email)) {
                        loginEmailMessage.setId("correctLabel");
                        loginEmailMessage.setText("Correct!");
                        loginEmailTextField.setId("text-field-correct");
                    } else {
                        loginEmailMessage.setId("errorLabel");
                        loginEmailMessage.setText("Invalid email format");
                        loginEmailTextField.setId("text-field-error");
                    }
                    System.out.println("task done");
                });
            }
        };
        System.out.println("task created");
        emailTasks.push(task);
        return task;
    }


    public void handleLoginPasswordField() {
        if (passwordTask.isRunning()) {
            timer.cancel();
            timer.purge();
            timer = new Timer();
            timer.schedule(createPasswordTask(), 1000);
        } else {
            timer.schedule(createPasswordTask(), 1000);
            loginPasswordMessage.setText("");
            loginPasswordField.setId("");
        }
    }



    private NonInterruptableTask createPasswordTask() {
        NonInterruptableTask task = new NonInterruptableTask() {
            @Override
            protected void doTaskWork() {
                Platform.runLater(() -> {
                    String password = loginPasswordField.getText().trim();
                    if (InputValidation.passwordValidation(password) == "valid") {
                        loginPasswordMessage.setId("correctLabel");
                        loginPasswordMessage.setText("Correct!");
                        loginPasswordField.setId("text-field-correct");
                    } else {
                        loginPasswordMessage.setId("errorLabel");
                        loginPasswordMessage.setText(InputValidation.passwordValidation(password));
                        loginPasswordField.setId("text-field-error");
                    }
                });
            }
        };
        passwordTask = task;
        return task;
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

        Profile profile = new Profile("", "", "testProfile", 0, 0, 14, "female", true, false, diary, new Option());
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

        Profile profile = new Profile(null, null, profileName, 0, 0, age, sex, isPregnant, isBreastfeeding, new Diary(), new Option());
        //goToProfileScreen(event, profile);
    }

    public void showRegisterPrompt() {
        //registerPrompt.setVisible(true);
    }

    public void hideRegisterPrompt() {
        //registerPrompt.setVisible(false);
    }

    public void passwordStrengthHandler() {
        String password = registerPasswordField.getText();
        float passwordStrength = InputValidation.getPasswordStrength(password);
        if (passwordStrength < 0.5) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarWeak");
        } else if (passwordStrength < 0.9) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarOk");
        } else {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarStrong");
        }
        passwordStrengthProgressBar.setProgress(passwordStrength);

        if (InputValidation.isStringWithinCharLimit(password)) {
            charLimitRadioButton.setId("greenRadioButton");
            charLimitRadioButton.setSelected(true);
        } else {
            charLimitRadioButton.setSelected(false);
        }

        if (InputValidation.containsUpperCase(password)) {
            upperCaseRadioButton.setSelected(true);
            upperCaseRadioButton.setId("greenRadioButton");
        } else {
            upperCaseRadioButton.setSelected(false);
        }

        if (InputValidation.contrainsLowerCase(password)) {
            lowerCaseRadioButton.setSelected(true);
            lowerCaseRadioButton.setId("greenRadioButton");
        } else {
            lowerCaseRadioButton.setSelected(false);
        }

        if (InputValidation.containsNumber(password)) {
            numberRadioButton.setSelected(true);
            numberRadioButton.setId("greenRadioButton");
        } else {
            numberRadioButton.setSelected(false);
        }

        if (InputValidation.containsSpecialChar(password)) {
            specialCharRadioButton.setSelected(true);
            specialCharRadioButton.setId("greenRadioButton");
        } else {
            specialCharRadioButton.setSelected(false);
        }
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
            goToProfileScreen(event, profile);
        } else {
            System.out.println("Choosing File closed!");
        }
    }

    public void logIn(ActionEvent event) {
        String email = loginEmailTextField.getText();
        String password = loginPasswordField.getText();

        if (InputValidation.emailValidation(email).equals(true)) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (DatabaseConnection.login(email, password)) {
                    goToProfileScreen(event, DatabaseConnection.getProfileFromDb(email));
                } else {
                    createErrorNotification(mainPane, "Account does not exist");
                    return;
                }
            } else {
                createErrorNotification(mainPane, InputValidation.passwordValidation(password));
                return;
            }
        } else {
            createErrorNotification(mainPane, "Invalid email format");
            return;
        }
    }

    public void register(ActionEvent event) {
        String email = registerEmailTextField.getText();
        String password = registerPasswordField.getText();
        String retypePassword = registerRetypePasswordField.getText();

        registerEmailTextField.setId("");
        registerPasswordField.setId("");
        registerRetypePasswordField.setId("");

        if (InputValidation.emailValidation(email).equals(true)) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (password.equals(retypePassword)) {
                    if (DatabaseConnection.register(email, password)) { // this will register if validation is ok?
                        goToCreateProfileScreenWithLogin(event, email, password);
                    } else {
                        createErrorNotification(mainPane, "An account with this email already exists");
                        registerEmailTextField.setId("text-field-warning");
                        return;
                    }
                } else {
                    createErrorNotification(mainPane, "Passwords not Matching");
                    registerRetypePasswordField.setId("text-field-error");
                    return;
                }
            } else {
                createErrorNotification(mainPane, "Please create a stronger password");
                if (!InputValidation.isStringWithinCharLimit(password)) {
                    charLimitRadioButton.setId("errorRadioButton");
                }
                if (!InputValidation.containsUpperCase(password)) {
                    upperCaseRadioButton.setId("errorRadioButton");
                }
                if (!InputValidation.contrainsLowerCase(password)) {
                    lowerCaseRadioButton.setId("errorRadioButton");
                }
                if (!InputValidation.containsNumber(password)) {
                    numberRadioButton.setId("errorRadioButton");
                }
                if (!InputValidation.containsSpecialChar(password)) {
                    specialCharRadioButton.setId("errorRadioButton");
                }
                registerPasswordField.setId("text-field-error");
                return;
            }
        } else {
            createErrorNotification(mainPane, "Invalid Email Format");
            registerEmailTextField.setId("text-field-error");
            return;
        }
    }

    // TODO this method should be used for testing to bypass having to register a new account for each test.
    public void testRegister(ActionEvent event) {
        System.out.println("java version: " + System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise("test", "test");
    }

    //TODO eventually remove
    public void testLogin(ActionEvent event) {
        DatabaseConnection.login("testProfile", "testpass!I1");
        goToScreen(event, "ProfileScreen.fxml");
    }
}