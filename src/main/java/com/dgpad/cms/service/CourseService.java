package com.dgpad.cms.service;

import com.dgpad.cms.model.Course;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService implements ServiceTemplate<Course> {
    private HashMap<String,Course> courses = new HashMap<>();
    private final ReadWriteService readWriteService;
    @Autowired
    private CourseService(ReadWriteService readWriteService) throws Exception {
        this.readWriteService = readWriteService;
        courses = convertListToHashSet(deserialize((JSONArray) readWriteService.Read("data","courses")));
    }
    @Override
    public boolean create(Course obj) throws Exception {
        courses.put(obj.getSymbol(),obj);
        writeUpdates();
        return true;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        courses.remove(ID);
        writeUpdates();
        return true;
    }

    @Override
    public Course read(String ID) {
        return courses.get(ID);
    }

    @Override
    public Course update(Course obj) throws Exception {
        courses.put(obj.getSymbol(),obj);
        writeUpdates();
        return obj;
    }

    @Override
    public List<Course> getList() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public Course deserialize(JSONObject object) {
        String  name = object.getString("name");
        String  symbol = object.getString("symbol");
        Integer credits = object.getInt("credits");
        String instructorName = object.getString("instructorName");
        String instructorEmail = object.getString("instructorEmail");
        return new Course(name,symbol,credits,instructorName,instructorEmail);
    }

    @Override
    public JSONObject serialize(Course obj) {
       JSONObject object = new JSONObject();
       object.put("name",obj.getName());
       object.put("symbol",obj.getSymbol());
       object.put("credits",obj.getCredits());
       object.put("instructorName",obj.getInstructorName());
       object.put("instructorEmail",obj.getInstructorEmail());
       return object;
    }

    @Override
    public List<Course> deserialize(JSONArray objects) {
       List<Course> courseList = new ArrayList<>();
       for(int i = 0 ; i< objects.length() ; i++)
           courseList.add(deserialize(objects.getJSONObject(i)));
       return courseList;
    }

    @Override
    public JSONArray serialize(List<Course> objects) {
        JSONArray coursesArray = new JSONArray();
        for(Course course : objects)
            coursesArray.put(serialize(course));
        return coursesArray;
    }

    public void writeUpdates() throws Exception {

    }

    private HashMap<String,Course> convertListToHashSet(List<Course> courses){
        HashMap<String, Course> courseMap = new HashMap<>();

        for (Course course : courses) {
            courseMap.put(course.getSymbol(), course);
        }

        return courseMap;
    }

    private List<Course> convertHashMapToList(HashMap<String, Course> courseMap) {
        List<Course> courseList = new ArrayList<>();

        for (Map.Entry<String, Course> entry : courseMap.entrySet()) {
            Course course = entry.getValue();
            courseList.add(course);
        }

        return courseList;
    }
}
