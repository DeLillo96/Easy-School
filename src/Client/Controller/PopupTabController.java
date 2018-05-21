package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PopupTabController {

    private Integer calendarId;

    @FXML
    private Text dateText;

    @FXML
    private Tab setMenuTab;

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
