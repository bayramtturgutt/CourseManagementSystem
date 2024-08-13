package com.dgpad.cms.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface ServiceTemplate<T> {
    public boolean create(T obj) throws Exception;
    public boolean delete(String ID) throws Exception;
    public T read(String ID);
    public T update(T obj) throws Exception;
    public List<T> getList();
    public T deserialize(JSONObject object);
    public JSONObject serialize(T obj);
    public List<T> deserialize(JSONArray objects);
    public JSONArray serialize(List<T> objects);
}
