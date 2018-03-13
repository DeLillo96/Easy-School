package Server;

import org.json.simple.JSONObject;

import java.lang.reflect.Field;
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

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("success", success);

        JSONObject jsonMessages = new JSONObject();
        for (int i = 0; i < messages.size(); i++) {
            jsonMessages.put(i, messages.get(i));
        }
        result.put("messages", jsonMessages);

        JSONObject jsonData = new JSONObject();
        for (int i = 0; i < data.size(); i++) {
            jsonData.put(i, classToJson(data.get(i)));
        }
        result.put("data", jsonData);

        return result;
    }

    private JSONObject classToJson(Object o) {
        Class targetClass = o.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();

        for(Field field : declaredFields) {
            try {
                field.setAccessible(true);
                jsonObject.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }
}
