package ua.com.foxstudent102052.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.com.foxstudent102052.dao.interfaces.CourseRepository;
import ua.com.foxstudent102052.model.dto.CourseDto;
import ua.com.foxstudent102052.model.entity.Course;
import ua.com.foxstudent102052.service.interfaces.CourseService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
        private final ModelMapper modelMapper = new ModelMapper();

        @Mock
        private CourseRepository courseDao;

        private CourseService courseService;

        @BeforeEach
        void setUp() {
                courseService = new CourseServiceImpl(courseDao, modelMapper);
        }

        @Test
        void MethodAddCourse_ShouldPassCourseToRepository() {
                // given
                Course courseFromDb = Course.builder()
                                .name("Java")
                                .description("Java course")
                                .students(List.of())
                                .build();
                CourseDto newCourse = modelMapper.map(courseFromDb, CourseDto.class);

                // when
                courseService.addCourse(newCourse);

                // then
                verify(courseDao).save(courseFromDb);
        }

        @Test
        void MethodGetAllCourses_ShouldReturnListOfAllStudents() {
                // given
                var courses = List.of(
                                Course.builder()
                                                .name("Java")
                                                .description("Java course")
                                                .build(),
                                Course.builder()
                                                .name("C++")
                                                .description("C++ course")
                                                .build(),
                                Course.builder()
                                                .name("C#")
                                                .description("C# course")
                                                .build());

                // when
                when(courseDao.findAll()).thenReturn(courses);

                // then
                var expected = courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).toList();

                var actual = courseService.getAll();
                assertEquals(expected, actual);
        }

        @Test
        void MethodGetCourseById_ShouldReturnCourseFromDb() {
                // given
                var course = Course.builder()
                                .name("Java")
                                .description("Java course")
                                .build();

                // when
                when(courseDao.findById(1L)).thenReturn(Optional.of(course));

                // then
                var expected = CourseDto.builder()
                                .name("Java")
                                .description("Java course")
                                .build();
                var actual = courseService.getCourseById(1L);

                assertEquals(expected, actual);
        }

        @Test
        void MethodGetCoursesByStudentId_ShouldReturnCourseFromDb() {
                // given
                var course = Course.builder()
                                .name("Java")
                                .description("Java course")
                                .build();

                // when
                when(courseDao.findByStudentId(1L)).thenReturn(List.of(course));

                // then
                var expected = List.of(
                                CourseDto.builder()
                                                .name("Java")
                                                .description("Java course")
                                                .build());
                var actual = courseService.getCoursesByStudent(1L);

                assertEquals(expected, actual);
        }
}
