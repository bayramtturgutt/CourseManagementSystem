package com.dgpad.cms.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Student {
    private UUID ID;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Max(15)
    private Integer allowedCredits;
    List<String> courses;

    public Student(){
        this.ID = UUID.randomUUID();
        this.allowedCredits = 15;
        this.courses = new ArrayList<>();
    }

    public Student(UUID ID){
        this.ID = ID;
        this.courses = new ArrayList<>();
    }

    public Student(UUID ID, String firstName, String lastName, Integer allowedCredits, List<String> courses) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allowedCredits = allowedCredits;
        this.courses = courses;
    }
}
