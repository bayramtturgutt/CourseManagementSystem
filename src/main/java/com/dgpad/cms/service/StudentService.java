package com.dgpad.cms.service;

import com.dgpad.cms.model.Course;
import com.dgpad.cms.model.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService implements ServiceTemplate<Student> {
    private HashMap<UUID, Student> students = new HashMap<>();
    private final ReadWriteService readWriteService;
    @Autowired
    private StudentService(ReadWriteService readWriteService) throws Exception {
        this.readWriteService = readWriteService;
        students = convertListToHashSet(deserialize((JSONArray) readWriteService.Read("data","students")));
    }

    @Override
    public boolean create(Student obj) throws Exception {
        students.put(obj.getID(),obj);
        writeUpdates();
        return true;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        students.remove(UUID.fromString(ID));
        writeUpdates();
        return true;
    }

    @Override
    public Student read(String ID) {
      return students.get(UUID.fromString(ID));
    }

    @Override
    public Student update(Student obj) throws Exception {
        students.put(obj.getID(),obj);
        writeUpdates();
        return obj;
    }

    @Override
    public List<Student> getList() {
        return new ArrayList<>(students.values());
    }

    @Override
    public Student deserialize(JSONObject object) {
        String ID = object.getString("ID");
        String firstName = object.getString("firstName");
        String lastName = object.getString("lastName");
        Integer allowedCredits = object.getInt("allowedCredits");
        List<String> courses = convertJSONArrayToList(object.getJSONArray("courses"));
        return new Student(UUID.fromString(ID), firstName,lastName,allowedCredits,courses);
    }

    @Override
    public JSONObject serialize(Student obj) {
        JSONObject object = new JSONObject();
        object.put("ID", obj.getID().toString());
        object.put("firstName", obj.getFirstName());
        object.put("lastName", obj.getLastName());
        object.put("allowedCredits", obj.getAllowedCredits());
        object.put("courses",new JSONArray(obj.getCourses()));
        return object;
    }

    @Override
    public List<Student> deserialize(JSONArray objects) {
        List<Student> studentList = new ArrayList<>();
        for(int i = 0 ; i< objects.length() ; i++)
            studentList.add(deserialize(objects.getJSONObject(i)));
        return studentList;
    }

    @Override
    public JSONArray serialize(List<Student> objects) {
        JSONArray studentArray = new JSONArray();
        for(Student student : objects)
            studentArray.put(serialize(student));
        return studentArray;
    }

    private List<String> convertJSONArrayToList(JSONArray array){
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            stringList.add(array.getString(i));
        }
        return stringList;
    }

    public void writeUpdates() throws Exception {
        
    }

    private HashMap<UUID,Student> convertListToHashSet(List<Student> students){
        HashMap<UUID, Student> studentHashMap = new HashMap<>();

        for (Student student : students) {
            studentHashMap.put(student.getID(), student);
        }

        return studentHashMap;
    }

    private List<Student> convertHashMapToList(HashMap<UUID,Student> students) {
        List<Student> studentList = new ArrayList<>();

        for (Map.Entry<UUID, Student> entry : students.entrySet()) {
            Student student = entry.getValue();
            studentList.add(student);
        }

        return studentList;
    }
}
