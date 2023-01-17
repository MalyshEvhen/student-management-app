package ua.com.foxstudent102052.controller;

import lombok.AllArgsConstructor;
import ua.com.foxstudent102052.dao.exceptions.DAOException;
import ua.com.foxstudent102052.model.dto.StudentDto;
import ua.com.foxstudent102052.service.interfaces.CourseService;
import ua.com.foxstudent102052.service.interfaces.GroupService;
import ua.com.foxstudent102052.service.interfaces.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;

    public void addStudent(StudentDto studentDto) throws DAOException {
        studentService.addStudent(studentDto);
    }

    public void removeStudent(int studentId) throws NoSuchElementException, DAOException {
        studentService.removeStudent(studentId);
    }

    public void addStudentToCourse(int studentId, int courseId) throws NoSuchElementException, DAOException {
        studentService.addStudentToCourse(studentId, courseId);
    }

    public void removeStudentFromCourse(int studentId, int courseId) throws NoSuchElementException, DAOException {
        studentService.removeStudentFromCourse(studentId, courseId);
    }

    public List<StudentDto> getAllStudents() throws NoSuchElementException, DAOException {
        return studentService.getStudents()
            .stream()
            .map(this::setStudentsGroup)
            .map(this::setStudentsCourseList)
            .toList();
    }

    public List<StudentDto> getStudents(String studentName, Integer courseId) throws NoSuchElementException, DAOException {
        return studentService.getStudents(studentName, courseId)
            .stream()
            .map(this::setStudentsGroup)
            .map(this::setStudentsCourseList)
            .toList();
    }

    private StudentDto setStudentsCourseList(StudentDto studentDto) {
        try {
            int studentId = studentDto.getId();
            var courseDtoList = courseService.getCourses(studentId);
            studentDto.setCoursesList(courseDtoList);

            return studentDto;
        } catch (NoSuchElementException | DAOException e) {
            return studentDto;
        }
    }

    private StudentDto setStudentsGroup(StudentDto studentDto) {
        try {
            int groupId = studentDto.getGroup().getId();
            var studentsGroup = groupService.getGroup(groupId);
            studentDto.setGroup(studentsGroup);

            return studentDto;
        } catch (NullPointerException | NoSuchElementException | DAOException e) {
            return studentDto;
        }
    }
}
