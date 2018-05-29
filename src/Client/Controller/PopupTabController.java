package Client.Controller;

import Client.ControllerManager;
import Client.Model.CalendarDay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class used to handle popups related to calendar days
 */
public class PopupTabController {

    private CalendarDay calendarDay;

    @FXML
    private Text dateText;
    @FXML
    private Tab setMenuTab;
    @FXML
    private Tab setTripTab;

    public PopupTabController() {
    }

    /**
     * Prints a string containing the selected day's date in "yyyy-MM-dd" format in the upper section of the popup
     * @param date DateString
     */
    public void setDate(String date) {
        setStringDateText(date);
    }

    /**
     * For each tab in the tabPane, this methos loads an FXML file resource and set it to a specific tab
     * @throws IOException
     */
    public void render() throws IOException {

        setDate(calendarDay.getStringDate());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/dailyDish.fxml"));
        setMenuTab.setContent(loader.load());
        DailyDishController dailyMenusController = loader.getController();
        dailyMenusController.setCalendarId(calendarDay.getCalendarId());
        dailyMenusController.filter();

        loader = new FXMLLoader(getClass().getResource("../Views/dailyTrips.fxml"));
        setTripTab.setContent(loader.load());
        TripController tripController = loader.getController();
        tripController.setCalendar(calendarDay);
        tripController.filter();
    }

    /**
     * Removes the current popup
     */
    public void remove() {
        calendarDay.setDay(calendarDay.getDay());
        ControllerManager.getInstance().removePopup();
    }

    public Integer getCalendarId() {
        return calendarDay.getCalendarId();
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarDay.setCalendarId(calendarId);
    }

    public Text getDateText() {
        return dateText;
    }

    public void setDateText(Text dateText) {
        this.dateText = dateText;
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public void setStringDateText(String dateText) {
        this.dateText.setText(dateText);
    }
}
