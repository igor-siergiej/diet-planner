package com.app.planner.calendarcontroller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {
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
        Node source = (Node)e.getTarget() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        if (colIndex == null) {
            colIndex = 0;
        }
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
    }
}
