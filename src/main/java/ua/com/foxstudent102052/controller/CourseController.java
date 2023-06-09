package ua.com.foxstudent102052.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.com.foxstudent102052.model.dto.CourseDto;
import ua.com.foxstudent102052.service.interfaces.CourseService;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAll();
    }

    @PostMapping("/add")
    public void addCourse(CourseDto courseDto) {
        courseService.addCourse(courseDto);
    }
}
