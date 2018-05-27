package Client.Controller;

/**
 * This abstract class contains the methods used to control the content of a notify popup
 */
public abstract class AbstractNotifyController {

    /**
     * This method is called to set a specific success/error message on a notify popup
     * @param message (the message shown with the popup)
     */
    abstract public void setMessage(String message);
}
