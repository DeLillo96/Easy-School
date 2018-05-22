package Client.Controller;

import Client.ControllerManager;
import Client.Model.CalendarDay;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private boolean foundCalendars;
    private boolean refreshView;
    private String errorMessage;
    private List<CalendarDay> dayList = new ArrayList<>();
    private BaseService calendarServices;

    public String dateStringConstructor(int dayNumber) {
        String returnString = ""+selectedYear+"-";
        if(selectedMonth<10) returnString = returnString+"0";
        returnString = returnString+""+(selectedMonth+1)+"-";
        if(dayNumber<10) returnString = returnString+"0";
        returnString = returnString+""+dayNumber;
        return returnString;
    }

    private void loadCalendarLabels(int month, int year) {

        yearSelect.setPromptText("" + selectedYear);
        foundCalendars=false;

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        int gridCount = 1;
        Integer lblCount = 1;

        JSONObject data = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject filters = new JSONObject();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lowerDateString = dateStringConstructor(1);
        String upperDateString = dateStringConstructor(daysInMonth);

        try {
            Date fromDate = dateFormat.parse(lowerDateString);
            Date toDate = dateFormat.parse(upperDateString);
            filters.put("dateFrom", fromDate);
            filters.put("dateTo", toDate);
            result = calendarServices.read(filters);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("Server communication error");
        }

        if((result!=null) && ((boolean) result.get("success"))) {
            data = (JSONObject) result.get("data");
            if(!data.isEmpty()) foundCalendars=true;
        }


        for (CalendarDay day : dayList) {
            day.clearContainer();
            if (gridCount < firstDay) {
                gridCount++;
                day.setUnusedDay();
            } else {
                if (lblCount > daysInMonth) {
                    day.setUnusedDay();
                } else {
                    day.setDay(lblCount);
                    day.setCalendarId(null);
                    if(foundCalendars) {
                        String actualCalendar = dateStringConstructor(lblCount);
                        for (int i = 0; i < data.size(); i++) {
                            JSONObject singleJsonDay = (JSONObject) data.get(i);
                            if(actualCalendar.equals((String) singleJsonDay.get("date"))) {
                                day.setCalendarId(Integer.parseInt((String) singleJsonDay.get("id")));
                                break;
                            }
                        }
                    }
                }
                lblCount++;
            }
        }
    }

    private void checkMonthSelectorConstraints() {
        refreshView = true;
        errorMessage = "";
        selectedMonth = monthSelect.getSelectionModel().getSelectedIndex();

        try {
            if (!yearSelect.getText().equals("")) {
                selectedYear = Integer.parseInt(yearSelect.getText());
            }
            loadCalendarLabels(selectedMonth, selectedYear);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("Please insert a valid year");
        }
    }

    private void checkDatePickerConstraints() {
        try {
            JSONObject dataFilter = new JSONObject();
            JSONObject dataCreateParams = new JSONObject();
            JSONObject data;
            JSONObject day;
            Integer calendarId = 1;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date calendarDay = dateFormat.parse(calendarDatePicker.getValue().toString());
            dataFilter.put("date", calendarDay);
            JSONObject response = calendarServices.read(dataFilter);
            if((boolean) response.get("success")) {
                data = (JSONObject) response.get("data");
                if(!(data.isEmpty())) {
                    day = (JSONObject) data.get(0);
                    calendarId = Integer.parseInt((String) day.get("id"));
                }else {
                    dataCreateParams.put("date", calendarDatePicker.getValue().toString());
                    JSONObject result = calendarServices.save(dataCreateParams);
                    if ((boolean) result.get("success")) {
                        data = (JSONObject) ((JSONObject) result.get("data")).get(0);
                        calendarId = Integer.parseInt((String) data.get("id"));
                    }
                }
            }
            ControllerManager.getInstance().renderCalendarPopup(calendarId, calendarDatePicker.getValue().toString());
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("Please select a data from the Date Picker");
            e.printStackTrace();
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
        try {
            calendarServices = RemoteManager.getInstance().getRemoteServicesManager().getCalendarService();
        } catch (Exception e) {}
        selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        monthSelect.getSelectionModel().select(selectedMonth);
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

    public BaseService getCalendarServices() {
        return calendarServices;
    }

    public void setCalendarServices(BaseService calendarServices) {
        this.calendarServices = calendarServices;
    }
}
