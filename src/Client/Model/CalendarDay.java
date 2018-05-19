package Client.Model;

import Client.Controller.CalendarController;
import Client.ControllerManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CalendarDay {
    private Integer day;
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
        if (getDay() != 0) {
            ControllerManager.getInstance().renderCalendarPopup("" + controller.getSelectedYear() + "-" + (controller.getSelectedMonth() + 1) + "-" + getDay());
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