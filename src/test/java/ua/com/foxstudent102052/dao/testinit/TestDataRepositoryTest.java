package ua.com.foxstudent102052.dao.testinit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxstudent102052.dao.interfaces.CourseRepository;
import ua.com.foxstudent102052.dao.interfaces.GroupRepository;
import ua.com.foxstudent102052.dao.interfaces.StudentRepository;
import ua.com.foxstudent102052.model.entity.Course;
import ua.com.foxstudent102052.model.entity.Group;
import ua.com.foxstudent102052.model.entity.Student;
import ua.com.foxstudent102052.utils.RandomModelCreator;

@ExtendWith(MockitoExtension.class)
public class TestDataRepositoryTest {
    @Mock
    private StudentRepository studentDao;

    @Mock
    private CourseRepository courseDao;

    @Mock
    private GroupRepository groupDao;

    @Mock
    private RandomModelCreator randomModelCreator;

    private TestDataRepository testDataRepository;

    @BeforeEach
    void setUp() {
        testDataRepository = new TestDataRepository(studentDao, courseDao, groupDao, randomModelCreator);
    }

    @Test
    void method_postTestRecords_shouldPassToDaosRecords() {
        // given
        var students = List.of(
                new Student(0L, null, "John", "Doe", null),
                new Student(0L, null, "Jane", "Doe", null));
        var groups = List.of(
                new Group(0L, "G1", null),
                new Group(0L, "G2", null));
        var courses = List.of(
                new Course(0L, "C1", "Some description 1", null),
                new Course(0L, "C2", "Some description 2", null));

        // when
        when(groupDao.findAll()).thenReturn(groups);

        testDataRepository.postTestRecords(students, courses, groups);

        // then
        verify(studentDao, times(2)).save(any(Student.class));
        verify(courseDao, times(2)).save(any(Course.class));
        verify(groupDao, times(2)).save(any(Group.class));
    }
}
