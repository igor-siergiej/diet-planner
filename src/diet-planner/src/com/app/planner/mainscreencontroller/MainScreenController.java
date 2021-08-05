package com.app.planner.mainscreencontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jdk.internal.util.xml.impl.Input;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class MainScreenController {
    Profile profile;

    @FXML
    private TextField loginUsernameTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField registrationUsernameTextField;

    @FXML
    private PasswordField registrationPasswordField;

    @FXML
    private PasswordField registrationRetypePasswordField;

    @FXML
    private Label registerMessage;

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

    public void goToConfigurationScreen(ActionEvent event) {
        goToScreen(event,"configcontroller/configScreen.fxml");
    }

    public void goToRegistrationScreen(ActionEvent event) { // this method will open the profile screen window
        MainScreenController mainScreenController = goToScreenWithProfile(event,"mainscreencontroller/registrationScreen.fxml").getController();
        mainScreenController.setRegistrationButtonDisable();
    }

    public void goToLoginScreen(ActionEvent event) { // this method will open the profile screen window
        MainScreenController mainScreenController = goToScreenWithProfile(event,"mainscreencontroller/loginScreen.fxml").getController();
        mainScreenController.setLoginButtonDisable();
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreen(event, "mainscreencontroller/mainScreen.fxml");
    }

    public void goToProfileScreen(ActionEvent event, Profile profile) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/profileScreen.fxml").getController();
        profileScreenController.setProfile(profile);
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        MainScreenController mainScreenController = goToScreenWithProfile(event,"mainscreencontroller/createProfileScreen.fxml").getController();
        mainScreenController.setAgeTextFieldEventHandler();
        mainScreenController.setCreateProfileButtonDisable();
    }

    private void goToScreen(ActionEvent event, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            Main.setWindow(event, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader goToScreenWithProfile(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setWindow(event, root);
        return loader;
    }

    public void setCreateProfileButtonDisable() {
        createProfileButton.disableProperty().bind(profileNameTextField.textProperty().isEmpty().or(ageTextField.textProperty().isEmpty()).or(sexComboBox.valueProperty().isNull()));
        //disabling the create profile button until the form is filled out

        breastfeedingComboBox.getSelectionModel().select(1); //setting default value for comboBoxes
        pregnantComboBox.getSelectionModel().select(1);
    }

    public void setLoginButtonDisable() {
        loginButton.disableProperty().bind(loginUsernameTextField.textProperty().isEmpty().or(loginPasswordField.textProperty().isEmpty()));
    }

    public void setRegistrationButtonDisable() {
        registerButton.disableProperty().bind(registrationUsernameTextField.textProperty().isEmpty().or(registrationPasswordField.textProperty().isEmpty()).or(registrationRetypePasswordField.textProperty().isEmpty()));
    }

    public void createTestProfile(ActionEvent event) {
        Profile profile = new Profile();
        profile.setProfileName("testProfile");
        profile.setSex("male");
        profile.setPregnant(false);
        profile.setBreastFeeding(false);
        profile.setAge(20);
        Diary diary = new Diary();

        Meal meal = new Meal();
        meal.setMealName("breakfast1");

        Meal meal1 = new Meal();
        meal1.setMealName("breakfast2");

        ArrayList<Food> data = Main.initialiseData();

        ArrayList<Food> foods = new ArrayList<>();
        foods.add(Main.sortedFoodSearch(data,"chicken").get(0));
        foods.add(Main.sortedFoodSearch(data,"liver").get(0));
        foods.add(Main.sortedFoodSearch(data,"eggs").get(0));
        meal.addFoods(foods,new ArrayList<>(Arrays.asList(100,100,100)));

        ArrayList<Food> foods1 = new ArrayList<>();
        foods1.add(Main.sortedFoodSearch(data,"chicken").get(0));
        foods1.add(Main.sortedFoodSearch(data,"liver").get(0));
        meal1.addFoods(foods1,new ArrayList<>(Arrays.asList(100,100)));

        Entry entry = new Entry(meal, LocalDateTime.now(),EntryType.DINNER);
        Entry entry1 = new Entry(meal1, LocalDateTime.now().minusDays(1),EntryType.DINNER);
        Entry entry2 = new Entry(meal1, LocalDateTime.now().minusDays(2),EntryType.DINNER);
        Entry entry3 = new Entry(meal1, LocalDateTime.now().minusDays(3),EntryType.DINNER);

        diary.addEntry(entry);
        diary.addEntry(entry1);
        diary.addEntry(entry2);
        diary.addEntry(entry3);

        profile.setDiary(diary);

        goToProfileScreen(event,profile);
    }

    public void setAgeTextFieldEventHandler() {
        ageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            ageTextField.setText(InputValidation.ageValidation(newValue));
        });
    }

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

        Profile profile = new Profile(profileName,age,sex,isPregnant,isBreastfeeding,new Diary());
        goToProfileScreen(event,profile);
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
        profile.loadFromFile(Main.chooseFile("load"));
        goToProfileScreen(event, profile);
    }

    public void openHyperlink() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://icons8.com/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logIn(ActionEvent event) {
        String username = loginUsernameTextField.getText();
        String password = loginPasswordField.getText();
        if (InputValidation.usernameValidation(username).equals("valid")) {
            if (InputValidation.passwordValidation(password).equals("valid")) {
                if (DatabaseConnection.login(username,password)) {
                    goToProfileScreen(event,DatabaseConnection.getProfileFromDb(username));
                } else {
                    loginMessage.setText("Account does not exist");
                    return;
                }
            } else {
                loginMessage.setText("Invalid Password");
                return;
            }
        } else {
            loginMessage.setText("Invalid Username");
            return;
        }
    }

    public void register(ActionEvent event) {
        String username = registrationUsernameTextField.getText();
        String password = registrationPasswordField.getText();
        String retypePassword = registrationRetypePasswordField.getText();
        if (password.equals(retypePassword)) {
            if (InputValidation.usernameValidation(username).equals("valid")) {
                if (InputValidation.passwordValidation(password).equals("valid")) {
                    if (DatabaseConnection.register(username,password)) {
                        goToCreateProfileScreen(event);
                    } else {
                        registerMessage.setText("Username Already exists");
                        return;
                    }
                } else {
                    registerMessage.setText(InputValidation.passwordValidation(password));
                    return;
                }
            } else {
                registerMessage.setText("Invalid Username");
                return;
            }
        } else {
            registerMessage.setText("Passwords Not matching");
            return;
        }
    }

    public void testLogin(ActionEvent event) {
        DatabaseConnection.login("test","testpass!I1");
        goToProfileScreen(event,DatabaseConnection.getProfileFromDb("test"));
    }
}
