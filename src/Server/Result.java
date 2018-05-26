package Server;

import Server.Entity.AbstractEntity;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Object used to communicate DB operations' results.
 * Contains the result (true or false), a list of messages linked to the result and a list of data used in read
 * operations
 */
public class Result {
    private List<String> messages = new ArrayList<String>();
    private boolean success = true;
    private List<Object> data = new ArrayList<Object>();

    public Result() {
    }

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

    /**
     * Method which transforms a result into a JSONObject in order to be transferred to the client
     * @return (JSONObject containing current result)
     */
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("success", success);

        result.put("messages", messages.toString());

        JSONObject jsonData = new JSONObject();
        for (int i = 0; i < data.size(); i++) {
            jsonData.put(i, dataToJson(data.get(i)));
        }
        result.put("data", jsonData);

        return result;
    }

    /**
     * Method which transforms result's data into a JSONObject
     * @param o (Data contained into current result)
     * @return (JSONObject containing parsed data)
     */
    private JSONObject dataToJson(Object o) {
        if (o instanceof Object[]){
            return objectsToJson((Object[]) o);
        } else {
            return classToJson(o);
        }
    }

    /**
     * Method which transforms data's objects into a JSONObject
     * @param objects (Objects contained into current result's data)
     * @return (JSONObject containing parsed objects)
     */
    private JSONObject objectsToJson(Object[] objects) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] != null) {
                jsonObject.put(i, parseObject(objects[i], objects[i].getClass()));
            }
        }
        return jsonObject;
    }

    /**
     * Method which transforms data class into a JSONObject
     * @param o (Data class contained into current result)
     * @return (JSONObject containing parsed data)
     */
    private JSONObject classToJson(Object o) {
        Class targetClass = o.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();

        Class extendedClass = targetClass.getSuperclass();
        if (extendedClass != null) {
            declaredFields = Stream.of(declaredFields, extendedClass.getDeclaredFields()).flatMap(Stream::of).toArray(Field[]::new);
        }

        JSONObject jsonObject = new JSONObject();

        for (Field field : declaredFields) {
            if (field.getType() != Set.class) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(o);

                    if (obj != null) {
                        jsonObject.put(field.getName(), parseObject(obj, field.getType()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

    /**
     * Method which parse a generic object into a specific class object
     * @param object (Object to parse)
     * @param objectClass (Type of class to parse to)
     * @return (Parsed obect)
     */
    private Object parseObject(Object object, Class objectClass) {
        if (object instanceof AbstractEntity) {
            return classToJson(object);
        } else {
            String item = object.toString();

            if(objectClass == Boolean.class) {
                item = Boolean.toString((Boolean) object);
            }
            if (objectClass == Date.class) {
                item = new SimpleDateFormat("yyyy-MM-dd").format(object);
            }
            return item;
        }
    }
}
