package Client.Model;

import Client.Controller.CalendarController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarDay {
    private Integer calendarId;
    private Integer day;
    private Date date;
    private VBox container;
    private CalendarController controller;

    /**
     * Generates a new object with a linked container containing all the daily news
     * @param calendarController (Main calendar controller)
     */
    public CalendarDay(CalendarController calendarController) {
        this.setController(calendarController);
        setContainer(new VBox());
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("calendar-box");
        events();
    }

    /**
     * Method called on calendar refresh, clears cell's style in order to a successive assignment
     */
    public void clearContainer() {
        setDay(0);
        container.getChildren().clear();
        container.getStyleClass().remove("unactive-box");
        container.getStyleClass().remove("today-box");
    }

    /**
     * Method used to set a calendar cell as unused (darker colour and no day label)
     */
    public void setUnusedDay() {
        container.getStyleClass().add("unactive-box");
    }

    /**
     * Method used to sign calendar cell as today cell
     */
    public void setToday() {
        container.getStyleClass().add("today-box");
    }

    /**
     * Method used to set listeners and related events to trigger
     */
    public void events() {
        container.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openPopup());
    }

    /**
     * Method used to render a calendar day popup referred to actual clicked calendar cell
     */
    public void openPopup() {
        JSONObject filters = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data;
        JSONObject dataCreateParams = new JSONObject();
        String actualDateString = controller.dateStringConstructor(day);
        if (getDay() != 0) {
            if (calendarId == null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date calendarDay = dateFormat.parse(actualDateString);
                    filters.put("date", calendarDay);
                } catch (Exception e) {
                    ControllerManager.getInstance().notifyError("Invalid dates (Required format: yyyy-MM-dd");
                }
                try {
                    result = controller.getCalendarServices().read(filters);
                } catch (Exception e) {
                    ControllerManager.getInstance().notifyError("Communication error (Can't call calendar services)");
                }
                if ((result != null) && (result.size()>0) && ((boolean) result.get("success")) && !(((JSONObject) result.get("data")).isEmpty() )) {
                    data = (JSONObject) result.get("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject singleJsonDay = (JSONObject) data.get(i);
                        if (actualDateString.equals(singleJsonDay.get("date"))) {
                            this.setCalendarId(Integer.parseInt((String) singleJsonDay.get("id")));
                            break;
                        }
                    }
                } else {
                    dataCreateParams.put("date", actualDateString);
                    try {
                        result = controller.getCalendarServices().save(dataCreateParams);
                    } catch (Exception e) {
                        ControllerManager.getInstance().notifyError("Communication error: (Can't call calendar services)");
                    }
                    if ((boolean) result.get("success")) {
                        data = (JSONObject) ((JSONObject) result.get("data")).get(0);
                        calendarId = Integer.parseInt((String) data.get("id"));
                    }
                }
            }
            ControllerManager.getInstance().renderCalendarPopup(this);
        }
    }

    public Integer getDay() {
        return day;
    }

    /**
     * Method used to set a day to a calendar cell through a specific label
     * @param day (Day of the current month)
     */
    public void setDay(Integer day) {
        this.container.getChildren().clear();
        this.day = day;
        Label lbl = new Label(day.toString());
        lbl.setPadding(new Insets(5));
        this.container.getChildren().add(lbl);

        addViewTrip();
    }

    private void addViewTrip() {
        try {
            JSONObject filters = new JSONObject();
            filters.put("day", calendarId);
            BaseService service = RemoteManager.getInstance().getRemoteServicesManager().getDayTripService();
            JSONObject response = service.read(filters);
            JSONObject data = (JSONObject) response.get("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject trip = (JSONObject) data.get(i);
                Label label = new Label((String) trip.get("code"));
                label.getStyleClass().add("calendar-label");
                container.getChildren().add(label);
            }
        } catch (Exception ignored){}
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() {
        return controller.dateStringConstructor(day);
    }

    public VBox getContainer() {
        return container;
    }

    public void setContainer(VBox container) {
        this.container = container;
    }

    public CalendarController getController() {
        return controller;
    }

    public void setController(CalendarController controller) {
        this.controller = controller;
    }
}