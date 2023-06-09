package ua.com.foxstudent102052.service.interfaces;

import java.util.List;

import ua.com.foxstudent102052.model.dto.CourseDto;

public interface CourseService {
    void addCourse(CourseDto courseDto);

    CourseDto getCourseById(Long id);

    List<CourseDto> getAll();

    List<CourseDto> getCoursesByStudent(Long studentId);
}
