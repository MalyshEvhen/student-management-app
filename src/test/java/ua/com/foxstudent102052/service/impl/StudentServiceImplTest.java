package ua.com.foxstudent102052.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.com.foxstudent102052.dao.interfaces.StudentDao;
import ua.com.foxstudent102052.model.dto.StudentDto;
import ua.com.foxstudent102052.model.entity.Student;
import ua.com.foxstudent102052.service.interfaces.StudentService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private StudentDao studentDao;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentDao);
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
        verify(studentDao).addStudent(student);
    }

    @Test
    void MethodRemoveStudent_ShouldRemoveExistingStudentFromDb() {
        // given
        int id = 1;

        // when
        when(studentDao.getStudent(id)).thenReturn(Optional.of(new Student()));
        doNothing().when(studentDao).removeStudent(id);

        // then
        studentService.removeStudent(id);

        verify(studentDao).removeStudent(id);
    }

    @Test
    void MethodRemoveStudent_ShouldThrowAnException_IfStudentDoesNotExist() {
        // given
        int id = 1;

        // when
        when(studentDao.getStudent(id)).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class,
            () -> studentService.removeStudent(id),
            "Student wasn`t removed");
    }

    @Test
    void MethodGetStudents_ShouldSendRequestToDao() {
        // given
        var students = List.of(new Student());

        // when
        when(studentDao.getAll()).thenReturn(students);
        studentService.getAll();

        // then
        verify(studentDao).getAll();
    }

    @Test
    void MethodGetStudents_ShouldThrowAnException_WhenStudentsDoesNotExist() {
        // when
        when(studentDao.getAll()).thenReturn(List.of());

        // then
        assertThrows(NoSuchElementException.class, () -> studentService.getAll(),
            "Students doesn't exist");
    }

    @Test
    void MethodRemoveStudent_ShouldThrowAnException_IfStudentWasNotRemoved() {
        // given
        int studentId = 1;

        // when
        when(studentDao.getStudent(studentId)).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> studentService.removeStudent(studentId),
            "Student wasn`t removed");
    }

    @Test
    void MethodAddStudentToCourse_ShouldAddExistingStudentToExistingCourse() {
        // given
        Student newStudent = Student.builder().id(1).firstName("John").lastName("Doe").build();

        // when
        when(studentDao.getStudent(newStudent.getId())).thenReturn(Optional.of(newStudent));
        studentService.addStudentToCourse(1, 1);

        // then
        verify(studentDao).addStudentToCourse(1, 1);
    }

    @Test
    void MethodRemoveStudentFromCourse_ShouldRemoveExistingStudentFromExistingCourse() {
        // given
        int studentId = 1;
        int groupId = 1;
        var student = new Student(studentId, groupId, "", "");

        // when
        when(studentDao.getStudentsByCourse(groupId)).thenReturn(List.of(student));
        studentService.removeStudentFromCourse(studentId, groupId);

        // then
        verify(studentDao).removeStudentFromCourse(studentId, groupId);
    }

    @Test
    void MethodGetStudentsByCourseShould_ReturnListOfStudents_ByCourseId() {
        // when
        when(studentDao.getStudentsByCourse(anyInt())).thenReturn(List.of(new Student()));
        studentService.getStudentsByCourse(1);

        // then
        verify(studentDao).getStudentsByCourse(1);
    }

    @Test
    void MethodGetStudentsByNameAndCourse_ShouldReturnListOfStudents() {
        // when
        when(studentDao.getStudentsByNameAndCourse(anyString(), anyInt()))
            .thenReturn(List.of(new Student()));
        studentService.getStudentsByNameAndCourse("John", 1);

        // then
        verify(studentDao).getStudentsByNameAndCourse("John", 1);
    }

    @Test
    void MethodGetStudentsByGropShould_ReturnListOfStudents_ByCourseId() {
        // when
        when(studentDao.getStudentsByGroup(anyInt())).thenReturn(List.of(new Student()));
        studentService.getStudentsByGroup(1);

        // then
        verify(studentDao).getStudentsByGroup(1);
    }
}
