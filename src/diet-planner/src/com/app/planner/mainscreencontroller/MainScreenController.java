package com.app.planner.mainscreencontroller;

import com.app.planner.Diary;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class MainScreenController {

    @FXML
    private TextField enterProfileNameTextField;// create this textField in the create profile screen

    public void goToConfiguration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/configcontroller/configScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void createNewProfile(ActionEvent event) throws IOException { //add this method to the new profile button
        Profile profile = new Profile();
        Diary diary = new Diary();
        profile.setDiary(diary);
        profile.setProfileName(enterProfileNameTextField.getText());
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

}
