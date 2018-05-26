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

/**
 * This abstract class contains the methods used to control the content of a generic view of the application
 */
public abstract class AbstractTableController {

    protected BaseService service;
    @FXML
    protected TableView<AbstractRowModel> tableView;
    private boolean newRowFlag = true;

    public AbstractTableController(BaseService baseService) {
        service = baseService;
    }

    /**
     * Method used to fill a generic tableView with specific elements, according to particular filters given in input.
     * At the first invocation, the filters are empty in order to fill the tableView with all the elements contained in
     * the invoked DB table
     */
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

    /**
     * Method which takes particular filters in input and returns an ArrayList containing all the elements read from DB
     * (according to filters' conditions), transported to the client through a JSONObject and parsed into specific rows
     * @param filters
     * @return an ArrayList containing all the parsed rows to insert into the tableView
     * @throws Exception RemoteException
     */
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


    /**
     * Method used to generate a new tableView row
     * @param item (Specific type of row, different for every tableView)
     */
    protected void addIntoTable(AbstractRowModel item) {
        if (isNewRowFlag()) {
            tableView.getItems().add(item);
            tableView.scrollTo(item);
            setNewRowFlag(false);
        }
    }


    /**
     * Method used to delete a specific tableView row
     * @param abstractRowModel (Specific model of row, different for every table)
     */
    public void delete(AbstractRowModel abstractRowModel) {
        tableView.getItems().remove(abstractRowModel);
    }

    /**
     * Method used to get values from filters' fields
     * @return
     */
    protected abstract JSONObject takeFilters();

    /**
     * Method which parses an input JSONObject and generates an ArrayList of a table's specific row model
     * @param data (Parsed JSONObject)
     * @return (ArrayList of elements that will be insert into a tableView)
     * @throws Exception ParseException
     */
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
}
