package com.dgpad.cms.controller;

import com.dgpad.cms.model.Course;
import com.dgpad.cms.model.Student;
import com.dgpad.cms.service.CourseService;
import com.dgpad.cms.service.StudentService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class ApplicationAPIs {
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    private ApplicationAPIs(StudentService studentService, CourseService courseService){
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping(value = "/")
    public String getHeader(Model model){
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String showStudentList(Model model) {
        model.addAttribute("students", studentService.getList());
        return "students";
    }

    @GetMapping("/courses")
    public String showCoursesList(Model model) {
        model.addAttribute("courses", courseService.getList());
        return "courses";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable UUID id) throws Exception {
        studentService.delete(id.toString());
        return "redirect:/students";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable String id) throws Exception {
        courseService.delete(id);
        return "redirect:/courses";
    }

    @GetMapping("/create-student")
    public String createStudentView(Model model){
        model.addAttribute("student", new Student());
        model.addAttribute("allCourses", courseService.getList());
        return "create-student";
    }

    @PostMapping("/create-student")
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "/create-student";
        }

        studentService.create(student);
        return "redirect:/students";
    }


    @GetMapping("/create-course")
    public String createCourseView(Model model){
        model.addAttribute("course", new Course());
        return "create-course";
    }

    @PostMapping("/create-course")
    public String createCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "/create-course";
        }

        if (courseService.read(course.getSymbol()) != null) {
            bindingResult.rejectValue("symbol", "course.symbol.exists", "Course symbol already exists");
            return "create-course";
        }

        courseService.create(course);
        return "redirect:/courses";
    }

    @GetMapping("/update-student/{id}")
    public String updateStudentView(@PathVariable UUID id, Model model){
        Student student = studentService.read(id.toString());
        model.addAttribute("student", student);
        model.addAttribute("allCourses", courseService.getList());
        return "update-student";
    }

    @PostMapping("/update-student")
    public String updateStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "/update-student/"+student.getID().toString();
        }
        studentService.update(student);
        return "redirect:/students";
    }


}
