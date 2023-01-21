package ua.com.foxstudent102052.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxstudent102052.dao.exceptions.DAOException;
import ua.com.foxstudent102052.dao.interfaces.StudentDao;
import ua.com.foxstudent102052.model.entity.Student;

@JdbcTest
@Sql({ "/scripts/ddl/Table_creation.sql", "/scripts/dml/testDB_Data.sql" })
class StudentDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Student> studentRowMapper = BeanPropertyRowMapper.newInstance(Student.class);
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDaoImpl(jdbcTemplate, studentRowMapper);
    }

    @Test
    void MethodAddStudent_ShouldAddStudentToDb() throws DAOException {
        // given
        var newStudent = Student.builder()
                .studentId(1)
                .groupId(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        // when
        studentDao.addStudent(newStudent);
        int expected = studentDao.getAll().size();
        int actual = 11;

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodAddStudentToCourse_ShouldAddStudentToNewCourse() throws DAOException {
        // given
        var expected = Student.builder()
                .studentId(1)
                .groupId(1)
                .firstName("Leia")
                .lastName("Organa")
                .build();

        // when
        studentDao.addStudentToCourse(1, 2);
        var actual = studentDao.getStudentsByCourse(1).get(0);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodGetAllStudents_ShouldReturnAllStudents() throws DAOException {
        // when
        var actual = studentDao.getAll().size();

        // then
        assertEquals(10, actual);
    }

    @Test
    void MethodGetStudentsByCourseId_ShouldReturnStudentByCourseId() throws DAOException {
        var expected = List.of(
                Student.builder()
                        .studentId(1)
                        .groupId(1)
                        .firstName("Leia")
                        .lastName("Organa")
                        .build(),
                Student.builder()
                        .studentId(2)
                        .groupId(1)
                        .firstName("Luke")
                        .lastName("Skywalker")
                        .build(),
                Student.builder()
                        .studentId(3)
                        .groupId(1)
                        .firstName("Han")
                        .lastName("Solo")
                        .build(),
                Student.builder()
                        .studentId(4)
                        .groupId(1)
                        .firstName("Padme")
                        .lastName("Amidala")
                        .build());

        var actual = studentDao.getStudentsByGroup(1);

        assertEquals(expected, actual);
    }

    @Test
    void MethodGetStudentsByGroup_ShouldReturnStudentByGroupId() throws DAOException {
        var expected = List.of(
                Student.builder()
                        .studentId(1)
                        .groupId(1)
                        .firstName("Leia")
                        .lastName("Organa")
                        .build(),
                Student.builder()
                        .studentId(2)
                        .groupId(1)
                        .firstName("Luke")
                        .lastName("Skywalker")
                        .build(),
                Student.builder()
                        .studentId(4)
                        .groupId(1)
                        .firstName("Padme")
                        .lastName("Amidala")
                        .build(),
                Student.builder()
                        .studentId(5)
                        .groupId(2)
                        .firstName("Dart")
                        .lastName("Maul")
                        .build(),
                Student.builder()
                        .studentId(9)
                        .groupId(2)
                        .firstName("Dart")
                        .lastName("Vader")
                        .build(),
                Student.builder()
                        .studentId(10)
                        .groupId(3)
                        .firstName("Jah Jah")
                        .lastName("Binks")
                        .build());

        var actual = studentDao.getStudentsByCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    void MethodGetStudentsByNameAndCourse_ShouldReturnListOfStudents_ByStudentNameAndCourseId() throws DAOException {
        var expected = List.of(
                Student.builder()
                        .studentId(5)
                        .groupId(2)
                        .firstName("Dart")
                        .lastName("Maul")
                        .build(),
                Student.builder()
                        .studentId(9)
                        .groupId(2)
                        .firstName("Dart")
                        .lastName("Vader")
                        .build());

        var actual = studentDao.getStudentsByNameAndCourse("Dart", 1);

        assertEquals(expected, actual);
    }

    @Test
    void MethodRemoveStudent_ShouldRemoveStudent_IfItInDataBase() throws DAOException {
        // when
        studentDao.removeStudent(1);

        int expected = 9;
        int actual = studentDao.getAll().size();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodGetStudentById_ShouldReturnStudentFromDb() throws DAOException {
        // given
        var expected = Student.builder()
                .studentId(1)
                .groupId(1)
                .firstName("Leia")
                .lastName("Organa")
                .build();

        // when
        var actual = studentDao.getStudent(1).get();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void MethodRemoveStudentFromCourse_ShouldRemoveStudentCourseRelation_IfExist() throws DAOException {
        // given
        studentDao.removeStudentFromCourse(1, 1);
        var expected = List.of();

        // when
        var actual = studentDao.getStudentsByNameAndCourse("Leia", 1);

        // then
        assertEquals(expected, actual);
    }
}
