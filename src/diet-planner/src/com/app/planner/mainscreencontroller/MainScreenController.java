package com.app.planner.mainscreencontroller;

import com.app.planner.Diary;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainScreenController {

    @FXML
    private Pane enterProfilePane;

    @FXML
    private TextField enterProfileNameTextField;

    public void goToConfiguration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/configcontroller/configScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void createNewProfile() {
        enterProfilePane.setVisible(true);
    }

    public void setProfile(ActionEvent event) throws IOException {
        Profile profile = new Profile();
        Diary diary = new Diary();
        profile.setDiary(diary);
        profile.setProfileName(enterProfileNameTextField.getText());
        goToProfileScreen(event, profile);
    }

    private void goToProfileScreen(ActionEvent event, Profile profile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/planner/profilescreencontroller/profileScreen.fxml"));
        Parent root = loader.load();

        ProfileScreenController profileScreenController = loader.getController();
        profileScreenController.setProfile(profile);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        window.setScene(scene);
        window.show();
    }

    public void loadProfile(ActionEvent event) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Main main = new Main();
        Profile profile = new Profile();
        profile.loadFromFile(main.chooseFile("load"));


        goToProfileScreen(event, profile);
    }

}
