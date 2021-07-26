package com.app.planner.mainscreencontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainScreenController {
    Profile profile;

    @FXML
    private TextField enterProfileNameTextField;// create this textField in the create profile screen

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField registrationUsernameField;

    @FXML
    private PasswordField registrationPasswordField;

    @FXML
    private PasswordField retypePasswordField;

    @FXML
    private Label registerMessage;

    @FXML
    private Label loginMessage;

    public void goToConfigurationScreen(ActionEvent event) {
        goToScreen(event,"configcontroller/configScreen.fxml");
    }

    public void goToRegistrationScreen(ActionEvent event) {
        goToScreen(event,"mainscreencontroller/registrationScreen.fxml");
    }

    public void goToLoginScreen(ActionEvent event) {
        goToScreen(event, "mainscreencontroller/loginScreen.fxml");
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreen(event, "mainscreencontroller/mainScreen.fxml");
    }

    public void goToProfileScreen(ActionEvent event, Profile profile) { // this method will open the profile screen window
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/profileScreen.fxml").getController();
        profileScreenController.setProfile(profile);
    }

    public void goToCreateProfileScreen(ActionEvent event) { // this method will open the profile screen window
        goToScreen(event,"mainscreencontroller/createProfileScreen.fxml");
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
        meal.addFoods(foods,new int[] {100,100,100});

        ArrayList<Food> foods1 = new ArrayList<>();
        foods1.add(Main.sortedFoodSearch(data,"chicken").get(0));
        foods1.add(Main.sortedFoodSearch(data,"liver").get(0));
        meal1.addFoods(foods1,new int[] {100,100});

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

    public void createNewProfile() {

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

    public void logIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        //INPUT VALIDATION HERE
        if (DatabaseConnection.login(username,password)) {
            System.out.println("logged in");
            profile = DatabaseConnection.getProfileFromDb(username);
            System.out.println(profile);
        } else {
            System.out.println("wrong logIn");
        }
    }

    public void register() throws SQLException {
        String username = registrationUsernameField.getText();
        String password = registrationPasswordField.getText();
        String retypePassword = retypePasswordField.getText();

        //INPUT VALIDATION HERE
        if (password.equals(retypePassword)) {
            if (DatabaseConnection.register(username,password)) {
                System.out.println("registered");
                System.out.println(profile);
            } else {
                registerMessage.setText("Username Already Exists");
            }
        } else {
            registerMessage.setText("Passwords don't match");
        }
    }
}
