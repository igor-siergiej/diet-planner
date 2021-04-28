package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class Controller  extends Scene{
    private Stage stage;

    private static final double TITLE_FONT_SIZE = 30;
    private static final double TEXT_SIZE = 30;

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 1000;

    private Slider[] sliders = new Slider[5];
    private Label[] valueLabels = new Label[5];
    private String[] units = new String[5];

    public Controller (Stage stage) {
        super(new BorderPane(), WIDTH, HEIGHT);

        this.stage = stage;



    }
}
