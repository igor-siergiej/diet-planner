package com.app.planner.mainscreencontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class MainScreenController {
    Profile profile;

    @FXML
    private TextField enterProfileNameTextField;// create this textField in the create profile screen

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void goToConfigurationScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/configcontroller/configScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goToRegistrationScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/com/app/planner/mainscreencontroller/registrationscreen.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goToLoginScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/mainscreencontroller/loginscreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goToMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/mainscreencontroller/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void createNewProfile(ActionEvent event) { //add this method to the new profile button
        Profile profile = new Profile();
        profile.setProfileName("profile1");
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
        //profile.setProfileName(enterProfileNameTextField.getText());
        //if we add more variables to Profile class here you will assign more info from ui components
        goToProfileScreen(event, profile);
    }

    public void goToProfileScreen(ActionEvent event,Profile profile) { // this method will open the profile screen window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/planner/profilescreencontroller/profileScreen.fxml"));
            Parent root = loader.load();

            ProfileScreenController profileScreenController = loader.getController();
            profileScreenController.setProfile(profile);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/com/app/planner/style.css");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void logIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        //LOGIN VALIDATION HERE???
        if (DatabaseConnection.login(username,password)) {
            System.out.println("logged in");
            profile = DatabaseConnection.getProfileFromDb(username);
            System.out.println(profile);
        } else {
            System.out.println("wrong logIn");
        }
    }

}
