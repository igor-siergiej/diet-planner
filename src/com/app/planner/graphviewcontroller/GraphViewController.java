package com.app.planner.graphviewcontroller;

import com.app.planner.BaseScreenController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Weeks");
        yAxis.setLabel("Calories");
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);
        lineChart.setLayoutX(100);
        lineChart.setLayoutY(67);
        lineChart.setLegendVisible(false);
        mainPane.getChildren().add(lineChart);

    }

}
