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
        VBox vBox = new VBox();
        vBox.setMinWidth(40);
        vBox.setMinWidth(this.getController().getWeekdayHeader().getPrefWidth() / 7);
        setContainer(vBox);
        events();
    }

    public void clearContainer() {
        setDay(0);
        this.container.getChildren().clear();
        this.container.setStyle("-fx-backgroud-color: white");
        this.container.setStyle("-fx-font: 14px \"System\" ");
    }

    public void setUnusedDay() {

        this.container.setStyle("-fx-background-color: #E9F2F5");

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
        lbl.setStyle("-fx-text-fill:darkslategray");
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