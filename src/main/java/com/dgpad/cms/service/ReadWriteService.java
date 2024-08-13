package com.dgpad.cms.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Objects;

@Service
public class ReadWriteService {
    public Object Read(String folder, String name) throws Exception {
        try {
            Resource resource = new ClassPathResource(folder + "/" + name + ".json");
            InputStream input = resource.getInputStream();
            String staticResourceString = new String(input.readAllBytes());

            if (Objects.isNull(staticResourceString) || staticResourceString.isEmpty())
                throw new IllegalArgumentException();

            Object json = new JSONTokener(staticResourceString).nextValue();
            if (json instanceof JSONObject) {
                return new JSONObject(staticResourceString);
            } else if (json instanceof JSONArray) {
                return new JSONArray(staticResourceString);
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
    public void Write(String folder, String name, Object object) throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource(folder + "/" + name + ".json");
            String filePath = resource.getFile().getAbsolutePath();

            // Append the JSON data to the file
            FileWriter fileWriter = new FileWriter(filePath, false);
            fileWriter.write(object.toString());
            fileWriter.write("\n"); // Add a new line for each entry
            fileWriter.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
