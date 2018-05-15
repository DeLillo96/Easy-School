package Client.Controller;

import Client.ControllerManager;
import Client.Model.CalendarDay;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarController {

    @FXML
    private ChoiceBox monthSelect;
    @FXML
    private TextField yearSelect;
    @FXML
    private Button changeCalendar;
    @FXML
    private DatePicker calendarDatePicker;
    @FXML
    private Button renderDatePopup;
    @FXML
    private HBox weekdayHeader;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private AnchorPane rootPane;

    private int selectedMonth;
    private int selectedYear;
    private boolean refreshView;
    private String errorMessage;
    private List<CalendarDay> dayList = new ArrayList<>();

    private void loadCalendarLabels(int month, int year) {

        yearSelect.setPromptText("" + selectedYear);

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        int offset = firstDay;
        int gridCount = 1;
        Integer lblCount = 1;

        for (CalendarDay day : dayList) {
            day.clearContainer();
            if (gridCount < offset) {
                gridCount++;
                day.setUnusedDay();
            } else {
                if (lblCount > daysInMonth) {
                    day.setUnusedDay();
                } else {
                    day.setDay(lblCount);
                }
                lblCount++;
            }
        }
    }

    private void checkMonthSelectorConstraints() {
        refreshView = true;
        errorMessage = "";
        try {
            if (monthSelect.getSelectionModel().getSelectedIndex() > 0) {
                selectedMonth = monthSelect.getSelectionModel().getSelectedIndex() - 1;
            } else throw new Exception();
        } catch (Exception e) {
            refreshView = false;
            errorMessage = errorMessage + "Please select a month\n";
        }

        try {
            if (!yearSelect.getText().equals("")) {
                selectedYear = Integer.parseInt(yearSelect.getText());
            }
        } catch (Exception e) {
            refreshView = false;
            errorMessage = errorMessage + "Please insert a valid year";
        }

        if (refreshView) {
            loadCalendarLabels(selectedMonth, selectedYear);
        } else {
            ControllerManager.getInstance().notifyError(errorMessage);
        }
    }

    private void checkDatePickerConstraints() {
        try {
            ControllerManager.getInstance().renderCalendarPopup(calendarDatePicker.getValue().toString());
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("Please select a data from the Date Picker");
        }
    }

    public void initializeCalendarGrid() {
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CalendarDay calendarDay = new CalendarDay(this);

                GridPane.setVgrow(calendarDay.getContainer(), Priority.ALWAYS);

                dayList.add(calendarDay);
                calendarGrid.add(calendarDay.getContainer(), j, i);
            }
        }

        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(rootPane.getHeight() / 7);
            calendarGrid.getRowConstraints().add(row);
        }
    }

    public void initializeCalendarWeekdayHeader() {

        int weekdays = 7;

        String[] weekAbbr = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int i = 0; i < weekdays; i++) {
            StackPane pane = new StackPane();
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);
            pane.setMinWidth(weekdayHeader.getPrefWidth() / 7);
            weekdayHeader.getChildren().add(pane);
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }

    public void initialize() {
        selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        monthSelect.getSelectionModel().select(selectedMonth+1);
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        changeCalendar.setOnAction(actionEvent -> checkMonthSelectorConstraints());
        renderDatePopup.setOnAction(actionEvent -> checkDatePickerConstraints());
        loadCalendarLabels(selectedMonth, selectedYear);
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public HBox getWeekdayHeader() {
        return weekdayHeader;
    }

    public void setWeekdayHeader(HBox weekdayHeader) {
        this.weekdayHeader = weekdayHeader;
    }
}
