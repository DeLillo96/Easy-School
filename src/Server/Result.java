package Server;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<String> messages;
    private boolean success;
    private List<Object> data;

    public Result() { this(new ArrayList<String>(), true, new ArrayList<Object>()); }

    public Result(boolean success) {
        this(new ArrayList<String>(), success, new ArrayList<Object>());
    }

    public Result(boolean success, List<Object> data) {
        this(new ArrayList<String>(), success, data);
    }

    public Result(List<String> messages, boolean success) {
        this(messages, success, new ArrayList<Object>());
    }

    public Result(String message, boolean success) {
        this();
        messages.add(message);
    }

    public Result(List<String> messages, boolean success, List<Object> data) {
        this.messages = messages;
        this.success = success;
        this.data = data;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addMessages(List<String> messages) {
        this.messages.addAll(messages);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
