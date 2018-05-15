package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    private void loadCalendarLabels(int month, int year) {

        yearSelect.setPromptText("" + selectedYear);

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        int offset = firstDay;
        int gridCount = 1;
        Integer lblCount = 1;

        for (Node node : calendarGrid.getChildren()) {
            VBox day = (VBox) node;
            day.getChildren().clear();
            day.setStyle("-fx-backgroud-color: white");
            day.setStyle("-fx-font: 14px \"System\" ");
            if (gridCount < offset) {
                gridCount++;
                day.setStyle("-fx-background-color: #E9F2F5");
            } else {
                if (lblCount > daysInMonth) {
                    day.setStyle("-fx-background-color: #E9F2F5");
                } else {
                    Label lbl = new Label(lblCount.toString());
                    lbl.setPadding(new Insets(5));
                    lbl.setStyle("-fx-text-fill:darkslategray");
                    day.getChildren().add(lbl);
                    /*int lblCount1 = lblCount;
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                        System.out.println(lblCount1);
                    });*/
                }
                lblCount++;
            }
        }
    }

    private void initializeMonthSelector() {

        changeCalendar.setOnAction(actionEvent -> {
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
        });
    }

    private void initializeCalendarDatePicker() {
        renderDatePopup.setOnAction(actionEvent -> {
            try {
                System.out.println(calendarDatePicker.getValue().toString());
            } catch (Exception e) {
                ControllerManager.getInstance().notifyError("Please select a data from the Date Picker");
            }
        });
    }

    public void initializeCalendarGrid() {
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                VBox vPane = new VBox();
                vPane.setMinWidth(40);

                vPane.setMinWidth(weekdayHeader.getPrefWidth() / 7);
                GridPane.setVgrow(vPane, Priority.ALWAYS);

                calendarGrid.add(vPane, j, i);
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
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        initializeMonthSelector();
        initializeCalendarDatePicker();
        loadCalendarLabels(selectedMonth, selectedYear);
    }

}
