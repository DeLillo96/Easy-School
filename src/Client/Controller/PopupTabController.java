package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class PopupTabController {

    private Integer calendarId;

    @FXML
    private Text dateText;
    @FXML
    private Tab setMenuTab;
    @FXML
    private Tab setTripTab;

    private String date;

    public PopupTabController() {
    }

    public void setDate(String date) {
        this.date = date;
        setStringDateText(date);
    }

    public void render() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/dailyMenus.fxml"));
        setMenuTab.setContent(loader.load());
        DailyMenusController dailyMenusController = loader.getController();
        dailyMenusController.setCalendarId(calendarId);
        dailyMenusController.filter();

        try {
            GregorianCalendar today = new GregorianCalendar();
            GregorianCalendar selected = new GregorianCalendar();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            selected.setTime(df.parse(date));

            if (today.get(GregorianCalendar.YEAR) == selected.get(GregorianCalendar.YEAR) &&
                    (today.get(GregorianCalendar.DAY_OF_YEAR) - 2) <= selected.get(GregorianCalendar.DAY_OF_YEAR)) {

            } else {
                loader = new FXMLLoader(getClass().getResource("../Views/dailyTrips.fxml"));
                setTripTab.setContent(loader.load());
                DailyTripsController dailyTripsController = loader.getController();
                dailyTripsController.setCalendarId(calendarId);
                dailyTripsController.filter();
            }

        } catch (ParseException e) {
            ControllerManager.getInstance().notifyError("Parse error, the day string is not supported");
        }
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Text getDateText() {
        return dateText;
    }

    public void setDateText(Text dateText) {
        this.dateText = dateText;
    }

    public void setStringDateText(String dateText) {
        this.dateText.setText(dateText);
    }
}
