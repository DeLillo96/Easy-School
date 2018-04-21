package Server;

import Server.Entity.AbstractEntity;
import org.json.simple.JSONObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Result {
    private List<String> messages = new ArrayList<String>();
    private boolean success = true;
    private List<Object> data = new ArrayList<Object>();

    public Result(){ }

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
        this(success);
        messages.add(message);
    }

    public Result(List<String> messages, boolean success, List<Object> data) {
        this.messages.addAll(messages);
        this.success = success;
        this.data.addAll(data);
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

    public void addData(Object data) {
        this.data.add(data);
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

        result.put("messages", messages.toString());

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
            if(field.getType() != Set.class) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(o);

                    if(obj != null) {
                        jsonObject.put(field.getName(), parseObject(obj, field.getType()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

    private Object parseObject(Object object, Class objectClass) {
        if(objectClass.getSuperclass() == AbstractEntity.class) {
            return classToJson(object);
        } else {
            String item = object.toString();

            if (objectClass == Date.class) {
                item = item.replace(' ','T');
            }
            return item;
        }
    }
}
