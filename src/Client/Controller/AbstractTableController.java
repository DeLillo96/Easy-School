package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Shared.BaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class AbstractTableController {

    protected BaseService service;
    @FXML
    protected TableView<AbstractRowModel> tableView;
    private boolean newRowFlag = true;

    public AbstractTableController(BaseService baseService) {
        service = baseService;
    }

    @FXML
    public void filter() {
        try {
            ArrayList list = search(takeFilters());
            ObservableList items = FXCollections.observableArrayList(list);
            tableView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    protected ArrayList search(JSONObject filters) throws Exception {
        JSONObject response = service.read(filters);

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    protected void notifyResult(JSONObject result) throws Exception {
        if ((boolean) result.get("success")) {
            ControllerManager.getInstance().notifySuccess(result.get("messages").toString());
        } else {
            String errorMessage = result.get("messages").toString();
            throw new Exception(errorMessage);
        }
    }

    protected void addIntoTable(AbstractRowModel item) {
        if (isNewRowFlag()) {
            tableView.getItems().add(item);
            tableView.scrollTo(item);
            setNewRowFlag(false);
        }
    }

    public void delete(AbstractRowModel abstractRowModel) {
        tableView.getItems().remove(abstractRowModel);
    }

    protected abstract JSONObject takeFilters();

    protected abstract ArrayList parseIntoRows(JSONObject data) throws Exception;

    public BaseService getService() {
        return service;
    }

    public void setService(BaseService service) {
        this.service = service;
    }

    public boolean isNewRowFlag() {
        return newRowFlag;
    }

    public void setNewRowFlag(boolean newRowFlag) {
        this.newRowFlag = newRowFlag;
    }

    public TableView<AbstractRowModel> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<AbstractRowModel> tableView) {
        this.tableView = tableView;
    }
}
