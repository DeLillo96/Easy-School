package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.DailyMenu;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DailyMenusController extends AbstractTableController {

    private Integer calendarId;

    private PopupTabController popupTabController;

    public DailyMenusController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService());
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<DailyMenu> list = search(takeFilters());

            RelationService dailyMenuService = RemoteManager.getInstance().getRemoteServicesManager().getDailyMenuService();
            JSONObject result = dailyMenuService.rightRead(calendarId);

            ArrayList<DailyMenu> dailyMenu = parseIntoRows((JSONObject) result.get("data"));
            for (DailyMenu menu : list) {
                dailyMenu.forEach(o -> {
                    if (o.getId().equals(menu.getId())) {
                        menu.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<AbstractRowModel> items = FXCollections.observableArrayList(list);
            tableView.setItems(items);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<DailyMenu> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dailyMenu = (JSONObject) data.get(i);

            DailyMenu item = new DailyMenu(this, dailyMenu);
            item.setCalendarId(calendarId);
            list.add(item);
        }
        return list;
    }

    @FXML
    protected void saveAll() {
        try {
            ArrayList<Integer> menuIds = new ArrayList<>();
            tableView.getItems().forEach(item -> menuIds.add(Integer.parseInt((String) item.getData().get("id"))));

            RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getDailyMenuService();
            notifyResult(service.assign(calendarId, menuIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PopupTabController getPopupTabController() {
        return popupTabController;
    }

    public void setPopupTabController(PopupTabController popupTabController) {
        this.popupTabController = popupTabController;
    }
}