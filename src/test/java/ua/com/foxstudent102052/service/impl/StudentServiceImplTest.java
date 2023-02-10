package ua.com.foxstudent102052.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import ua.com.foxstudent102052.dao.interfaces.CourseRepository;
import ua.com.foxstudent102052.dao.interfaces.StudentRepository;
import ua.com.foxstudent102052.model.dto.StudentDto;
import ua.com.foxstudent102052.model.entity.Group;
import ua.com.foxstudent102052.model.entity.Student;
import ua.com.foxstudent102052.service.interfaces.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private StudentRepository studentDao;

    @Mock
    private CourseRepository courseDao;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentDao, courseDao, modelMapper);
    }

    @Test
    void MethodAddStudent_ShouldPassNewStudentToRepository() {
        // given
        var studentDto = StudentDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        var student = modelMapper.map(studentDto, Student.class);

        // when
        studentService.addStudent(studentDto);

        // then
        verify(studentDao).save(student);
    }

    @Test
    void MethodRemoveStudent_ShouldRemoveExistingStudentFromDb() {
        // given
        int id = 1;
        var student = new Student(id, new Group(), "name", "surname", List.of());

        // when
        when(studentDao.findById(id)).thenReturn(Optional.of(student));

        // then
        studentService.removeStudent(id);

        verify(studentDao).delete(student);
    }

    @Test
    void MethodRemoveStudent_ShouldThrowAnException_IfStudentDoesNotExist() {
        // given
        int id = 1;

        // when
        when(studentDao.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> studentService.removeStudent(id), "Student wasn`t removed");
    }

    @Test
    void MethodGetStudents_ShouldSendRequestToDao() {
        // given
        var students = List.of(new Student());

        // when
        when(studentDao.findAll()).thenReturn(students);
        studentService.getAll();

        // then
        verify(studentDao).findAll();
    }

    @Test
    void MethodRemoveStudent_ShouldThrowAnException_IfStudentWasNotRemoved() {
        // given
        int studentId = 1;

        // when
        when(studentDao.findById(studentId)).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> studentService.removeStudent(studentId),
                "Student wasn`t removed");
    }

    @Test
    void MethodGetStudentsByCourseShould_ReturnListOfStudents_ByCourseId() {
        // when
        when(studentDao.findByCourseId(anyInt())).thenReturn(List.of(new Student()));
        studentService.getStudentsByCourse(1);

        // then
        verify(studentDao).findByCourseId(1);
    }

    @Test
    void MethodGetStudentsByNameAndCourse_ShouldReturnListOfStudents() {
        // when
        when(studentDao.findByNameAndCourseId(anyString(), anyInt()))
            .thenReturn(List.of(new Student()));
        studentService.getStudentsByNameAndCourse("John", 1);

        // then
        verify(studentDao).findByNameAndCourseId("John", 1);
    }

    @Test
    void MethodGetStudentsByGropShould_ReturnListOfStudents_ByCourseId() {
        // when
        when(studentDao.findByGroupId(anyInt())).thenReturn(List.of(new Student()));
        studentService.getStudentsByGroup(1);

        // then
        verify(studentDao).findByGroupId(1);
    }
}
