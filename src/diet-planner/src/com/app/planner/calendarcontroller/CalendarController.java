package com.app.planner.calendarcontroller;

import com.app.planner.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private LocalDate localDate = LocalDate.now();
    private YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth().getValue());

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendar;

    @FXML
    protected void initialize() {
        populateCalendar(yearMonth);
    }

    public void populateCalendar(YearMonth yearMonth1) {
        //clear the Id's on all nodes of the calendar
        for (Node node: calendar.getChildren()) {
            node.setId("");
        }

        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth1.getYear(), yearMonth1.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }

        //removes all of the labels from the calendar so that the new months days can the displayed
        final List<Node> removalCandidates = new ArrayList<>();
        for (Node node : calendar.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                removalCandidates.add(label);
            }
        }
        calendar.getChildren().removeAll(removalCandidates);

        for (int i = 1; i <6;i++) {
            for (int j = 0; j < 7; j++) {
                Label label = new Label();
                label.setText(String.valueOf(calendarDate.getDayOfMonth()));
                label.setTranslateX(10);
                label.setFont(Font.font(20));
                if (calendarDate.equals(LocalDate.now())) {
                    getNodeFromGridPane(j,i,calendar).setId("currentDay"); //i and j swapped because GridPane.getChildren() gets the list in reverse order I think
                }
                calendar.add(label,j,i);
                calendarDate = calendarDate.plusDays(1);
            }
        }
        monthLabel.setText(yearMonth1.getMonth().toString() + " " + String.valueOf(yearMonth1.getYear()));
    }

    public void nextMonth() {
        yearMonth = yearMonth.plusMonths(1);
        populateCalendar(yearMonth);
    }

    public void previousMonth() {
        yearMonth = yearMonth.minusMonths(1);
        populateCalendar(yearMonth);
    }

    @FXML
    private void mouseEntered(MouseEvent e) {
        for (Node node: calendar.getChildren()) {
            if (node.getId() != null && node.getId().equals("selectedDay")) {
                node.setId("");
            }
        }
        Node source = (Node)e.getTarget() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        if (colIndex == null) {
            colIndex = 0;
        }
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
        Node node = getNodeFromGridPane(colIndex,rowIndex,calendar);
        if (!(node.getId().equals("currentDay"))) {
            node.setId("selectedDay");
        }
    }

    private Node getNodeFromGridPane(int col, int row,GridPane gridPane) {
        Integer gridCol = new Integer(0);
        Integer gridRow = new Integer(0);
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == null) {
                gridRow = 0;
            } else {
                gridRow = GridPane.getRowIndex(node);
            }
            if (GridPane.getColumnIndex(node) == null) {
                gridCol = 0;
            } else {
                gridCol = GridPane.getColumnIndex(node);
            }
            if (gridCol == col && gridRow == row) {
                return node;
            }
        }
        return null;
    }

    public void goToMainScreen(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/com/app/planner/mainscreencontroller/mainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        mainScreenScene.getStylesheets().add("com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }
}
