package Client.Model;

import Client.Controller.CalendarController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.geometry.Insets;
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

    public CalendarDay(CalendarController calendarController) {
        this.setController(calendarController);
        setContainer(new VBox());
        container.setMinWidth(40);
        container.setMinWidth(this.getController().getWeekdayHeader().getPrefWidth() / 7);
        container.getStyleClass().add("calendar-box");
        events();
    }

    public void clearContainer() {
        setDay(0);
        container.getChildren().clear();
        container.getStyleClass().remove("unactive-box");
    }

    public void setUnusedDay() {
        container.getStyleClass().add("unactive-box");
    }

    public void events() {
        container.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openPopup());
    }

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
                } catch (Exception e) { }
                try {
                    result = controller.getCalendarServices().read(filters);
                } catch (Exception e) {
                    ControllerManager.getInstance().notifyError("Read error from server");
                }
                if ((result != null) && (result.size()>0) && ((boolean) result.get("success")) && !(((JSONObject) result.get("data")).isEmpty() )) {
                    data = (JSONObject) result.get("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject singleJsonDay = (JSONObject) data.get(i);
                        if (actualDateString.equals((String) singleJsonDay.get("date"))) {
                            this.setCalendarId(Integer.parseInt((String) singleJsonDay.get("id")));
                            break;
                        }
                    }
                }else {
                    dataCreateParams.put("date", actualDateString);
                    try {
                        result = controller.getCalendarServices().save(dataCreateParams);
                    } catch (Exception e) {
                        ControllerManager.getInstance().notifyError("Save error from server");
                    }
                    if ((boolean) result.get("success")) {
                        data = (JSONObject) ((JSONObject) result.get("data")).get(0);
                        calendarId = Integer.parseInt((String) data.get("id"));
                        System.out.println(calendarId);
                    }
                }
            }
            ControllerManager.getInstance().renderCalendarPopup(calendarId, actualDateString);
        }
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
        Label lbl = new Label(day.toString());
        lbl.setPadding(new Insets(5));
        this.container.getChildren().add(lbl);
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