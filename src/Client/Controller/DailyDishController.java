package Client.Controller;

import Client.Model.DailyDish;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DailyDishController extends AbstractTableController {

    private Integer calendarId;
    @FXML
    private Button warning;

    private PopupTabController popupTabController;

    public DailyDishController() throws Exception {
        super(null);
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getDailyDishService();
        JSONObject response = service.rightRead(calendarId);

        if ((boolean) response.get("success")) {

            fillWarning(response);
            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<DailyDish> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dailyDish = (JSONObject) data.get(i);

            DailyDish item = new DailyDish(this, (JSONObject) dailyDish.get(0));
            item.setCalendarId(calendarId);
            item.getSelect().setSelected(Boolean.valueOf((String) dailyDish.get(1)));
            item.getType().setText((String) dailyDish.get(2));
            list.add(item);
        }
        return list;
    }

    @FXML
    protected void saveAll() {
        try {
            ArrayList<Integer> dishIds = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                DailyDish item = (DailyDish) tableView.getItems().get(i);
                if( item.getSelect().isSelected()) {
                    dishIds.add(item.getId());
                }
            }

            RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getDailyDishService();
            notifyResult(service.assign(calendarId, dishIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyResult(JSONObject result) throws Exception {
        if((Boolean) result.get("success")) fillWarning(result);
        super.notifyResult(result);
    }

    public void fillWarning(JSONObject result) {
        if(!result.get("messages").equals("[]")) {
            warning.setVisible(true);
            String message = "ATTENTION!\nThese aliment are an eating disorder for the number of child\n";
            String messages = ((String) result.get("messages")).replace(',','\n');
            message += messages.substring(1, messages.length() - 1);
            message += "\nMake sure there are a dish in daily menu that not contain these aliment";
            warning.getTooltip().setText(message);
        } else {
            warning.setVisible(false);
        }
    }

    public PopupTabController getPopupTabController() {
        return popupTabController;
    }

    public void setPopupTabController(PopupTabController popupTabController) {
        this.popupTabController = popupTabController;
    }
}