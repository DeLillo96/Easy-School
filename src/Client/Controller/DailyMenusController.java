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
        /*
        ObservableList<DailyMenu> allMenus = tableView.getItems();
        ArrayList<Integer> addMenus = new ArrayList<>();
        for (DailyMenu d:allMenus) {
            if(d.getSelect().isSelected()) {
                addMenus.add(d.getId());
            }
        }
        try {
            RemoteManager.getInstance().getRemoteServicesManager().getDailyMenuService().saveAll(calendarId,addMenus);
        } catch(Exception e) {
            ControllerManager.getInstance().notifyError("Communication error (Can't call menu services)");
        }
        */
    }

    public PopupTabController getPopupTabController() {
        return popupTabController;
    }

    public void setPopupTabController(PopupTabController popupTabController) {
        this.popupTabController = popupTabController;
    }
}