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

/**
 * Class which generates the calendar view in home tab
 */
public class CalendarController {

    @FXML
    private ChoiceBox monthSelect;
    @FXML
    private TextField yearSelect;
    @FXML
    private Button changeCalendar;
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

    /**
     * Method which takes day, month and year and generates a string in "yyyy-MM-dd" format
     * @param dayNumber (Number of the day)
     * @return
     */
    public String dateStringConstructor(int dayNumber) {
        String returnString = ""+selectedYear+"-";
        if(selectedMonth<10) returnString = returnString+"0";
        returnString = returnString+""+(selectedMonth+1)+"-";
        if(dayNumber<10) returnString = returnString+"0";
        returnString = returnString+""+dayNumber;
        return returnString;
    }

    /**
     * Method used to load calendar cells, according to selected month and year, and to assign a DB calendar element to
     * each calendar cell
     * @param month (Selected month)
     * @param year (Selected year)
     */
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

    /**
     * Checks selected month and year and refreshes the calendar
     */
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

    /**
     * Method used to initialize the basic calendar grid
     */
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

    /**
     * Method used to initialize the weekday header on the calendar
     */
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

    /**
     * Method used to assign calendar services to the controller, to set initial month and year and to load the GUI
     */
    public void initialize() {
        try {
            calendarServices = RemoteManager.getInstance().getRemoteServicesManager().getCalendarService();
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("Server communication error (Can't call calendar services)");
        }
        selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        monthSelect.getSelectionModel().select(selectedMonth);
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        changeCalendar.setOnAction(actionEvent -> checkMonthSelectorConstraints());
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
