package com.app.planner;

import com.app.planner.util.DatabaseConnection;
import com.app.planner.util.GsonStorage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainscreencontroller/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/app/planner/util/style.css");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        db.testConnection();
        GsonStorage gsonStorage = new GsonStorage();
        gsonStorage.saveObjectToJson();

        launch(args);
    }
}
