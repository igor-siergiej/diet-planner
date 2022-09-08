package com.app.planner.graphviewcontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.Entry;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class GraphViewController extends BaseScreenController {

    @FXML
    private ToggleButton graphViewButton;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private Pane mainPane;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,graphViewButton,undoButton,redoButton);
        ArrayList<Entry> entries = profile.getDiary().getAllEntries();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Weeks");
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(entries.size()+1);
        xAxis.setTickUnit(1);

        yAxis.setLabel("Calories");
        yAxis.setMinorTickVisible(false);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(2000);
        yAxis.setTickUnit(200);

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < entries.size(); i++) {
            series.getData().add(new XYChart.Data(i + 1, entries.get(i).getNutrientValueForEntry("Energy (kcal)")));
        }

        lineChart.getData().add(series);
        lineChart.setLayoutX(100);
        lineChart.setLayoutY(67);
        lineChart.setLegendVisible(false);

        mainPane.getChildren().add(lineChart);
    }

}
