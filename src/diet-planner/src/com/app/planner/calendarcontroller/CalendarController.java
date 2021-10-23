package com.app.planner.calendarcontroller;

import com.app.planner.Entry;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {
    public Profile profile;
    private LocalDate localDate = LocalDate.now();
    private YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth().getValue());

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendar;

    public void load() {
        populateCalendar(yearMonth);
    }

    public void initialize(Profile profile) {
        calendar.setId("calendar");
        this.profile = profile;
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

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialize(profile);
    }

    public void goToViewNutrientsScreen(ActionEvent event) { // this method will open the profile screen window
        ViewNutrientsController viewNutrientsController = goToScreenWithProfile(event,"viewnutrientscontroller/ViewNutrientsScreen.fxml").getController();
        viewNutrientsController.initialize(profile);
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
            if (node instanceof Label || node instanceof VBox) {
                removalCandidates.add(node);
            } else if (node instanceof Pane) {
                node.setId("calendarPane");
            }
        }
        calendar.getChildren().removeAll(removalCandidates);

        for (int i = 1; i <6;i++) {
            for (int j = 0; j < 7; j++) {
                Label label = new Label();
                label.setText(String.valueOf(calendarDate.getDayOfMonth()));
                label.setTranslateX(10);
                label.setFont(Font.font(20));
                label.setId("whiteLabel");
                if (calendarDate.equals(LocalDate.now())) {
                    getNodeFromGridPane(j,i,calendar).setId("currentDay"); //i and j swapped because GridPane.getChildren() gets the list in reverse order I think
                }
                calendar.add(label,j,i);
                ArrayList<Entry> entriesToday = getEntriesToday(profile.getDiary().getAllEntries(), calendarDate);
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                for (Entry entry : entriesToday) {
                    Label entryLabel = new Label();
                    label.setAlignment(Pos.CENTER);
                    entryLabel.setText((entry.getMeal().getMealName() + entry.getTimeEaten().getHour()) + entry.getTimeEaten().getMinute());
                    entryLabel.setId("entryLabel");
                    entryLabel.setTranslateX(15);
                    vBox.getChildren().add(entryLabel);
                }
                calendar.add(vBox,j,i);
                calendarDate = calendarDate.plusDays(1);
            }
        }
        monthLabel.setText(yearMonth1.getMonth().toString() + " " + yearMonth1.getYear());
    }

    public ArrayList<Entry> getEntriesToday(ArrayList<Entry> entries, LocalDate date) {
        ArrayList<Entry> returnList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getTimeEaten().toLocalDate().isEqual(date)) {
                returnList.add(entry);
            }
        }
        return returnList;
    }

    public void nextMonth() {
        yearMonth = yearMonth.plusMonths(1);
        populateCalendar(yearMonth);
    }

    public void previousMonth() {
        yearMonth = yearMonth.minusMonths(1);
        populateCalendar(yearMonth);
    }

    public void mouseEntered(MouseEvent e) {
        for (Node node: calendar.getChildren()) {
            if (node.getId() != null && node.getId().equals("selectedDay")) {
                node.setId("");
                if (node instanceof Pane) {
                    node.setId("calendarPane");
                }
            }
        }
        Node source = (Node)e.getTarget();
        while (!(source instanceof Pane)) { //get the parent pane of the source clicked
            source = source.getParent();
        }
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        if (colIndex == null) {
            colIndex = 0;
        }

        Node node = getNodeFromGridPane(colIndex,rowIndex,calendar);
        if (!(node.getId().equals("currentDay"))) {
            node.setId("selectedDay");
        }
    }

    private Node getNodeFromGridPane(int col, int row,GridPane gridPane) {
        Integer gridCol;
        Integer gridRow;
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


}
