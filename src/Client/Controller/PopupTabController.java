package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class PopupTabController {

    @FXML
    private Text dateText;

    private String date;

    public void setDate(String date) {
        this.date = date;
        setStringDateText(date);
    }

    public PopupTabController() { }

    @FXML
    public void initialize() throws IOException {

    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
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
