package ua.com.foxstudent102052.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.com.foxstudent102052.model.dto.CourseDto;
import ua.com.foxstudent102052.model.dto.GroupDto;
import ua.com.foxstudent102052.model.dto.StudentDto;

class RandomModelCreatorTest {
    public static final String COURSES_CSV = "csv/courses.csv";
    public static final String GROUPS_CSV = "csv/groups.csv";
    public static final String STUDENT_NAMES_CSV = "csv/student_names.csv";
    public static final String STUDENT_SURNAMES_CSV = "csv/student_surnames.csv";

    private final RandomModelCreator randomModelCreator = new RandomModelCreator();

    static List<String[]> coursesNamesAndDescriptions;
    static List<String> groupNames;
    static List<String> studentNames;
    static List<String> studentSurnames;
    static List<GroupDto> groups;
    static List<StudentDto> students;
    static List<CourseDto> courses;

    @BeforeAll
    static void init() {
        FileUtils fileUtils = new FileUtils();

        coursesNamesAndDescriptions = fileUtils.readCsvFileFromResources(COURSES_CSV);
        groupNames = fileUtils.readCsvFileFromResources(GROUPS_CSV).stream().map(s -> s[0]).toList();
        studentNames = fileUtils.readCsvFileFromResources(STUDENT_NAMES_CSV).stream().map(s -> s[0]).toList();
        studentSurnames = fileUtils.readCsvFileFromResources(STUDENT_SURNAMES_CSV).stream().map(s -> s[0]).toList();
        groups = List.of(
                GroupDto.builder().groupId(1).groupName("Group1").build(),
                GroupDto.builder().groupId(2).groupName("Group2").build(),
                GroupDto.builder().groupId(3).groupName("Group3").build(),
                GroupDto.builder().groupId(4).groupName("Group4").build());
        students = List.of(
                StudentDto.builder().studentId(0).build(), StudentDto.builder().studentId(1).build(),
                StudentDto.builder().studentId(2).build(), StudentDto.builder().studentId(3).build(),
                StudentDto.builder().studentId(4).build(), StudentDto.builder().studentId(5).build());
        courses = List.of(
                CourseDto.builder().courseId(1).build(), CourseDto.builder().courseId(2).build(),
                CourseDto.builder().courseId(3).build());
    }

    // @Test
    // void Method_getGroups_shouldReturnListOfGroups() {
    // // given
    // var groups = randomModelCreator.getGroups(groupNames);

    // // when
    // var actual = groups.stream()
    // .filter(group -> group.getGroupName() == null)
    // .toList();

    // // then
    // assertTrue(actual.isEmpty());
    // }

    // @Test
    // void Method_getCourses_shouldReturnListOfCourses() {
    // // given
    // var courses = randomModelCreator.getCourses(coursesNamesAndDescriptions);

    // // when
    // var actual = courses.stream()
    // .filter(course -> course.getCourseName() == null ||
    // course.getCourseDescription() == null)
    // .toList();

    // // then
    // assertTrue(actual.isEmpty());
    // }

    // @Test
    // void Method_getStudents_shouldReturnListOfStudents() {
    // // given
    // var students = randomModelCreator.getStudents(studentNames, studentSurnames,
    // groups, 100);

    // // when
    // var actual = students.stream()
    // .filter(student -> student.getFirstName() == null ||
    // student.getLastName() == null || student.getGroup() == null)
    // .toList();

    // // then
    // assertTrue(actual.isEmpty());
    // }

    // @Test
    // void Method_getStudents_shouldReturnListOfStudents_withGroupIdsInGivenRange()
    // {
    // // given
    // var students = randomModelCreator.getStudents(studentNames, studentSurnames,
    // groups, 100);

    // // when
    // var actual = students.stream()
    // .mapToInt(student -> student.getGroup().getGroupId())
    // .anyMatch(id -> id < 0 || id > 10);

    // // then
    // assertFalse(actual);
    // }

    // @Test
    // void
    // Method_getStudentsCoursesRelations_shouldReturnMapOfStudentCoursesRelations()
    // {
    // // given
    // int coursesCount = 2;

    // // when
    // var actual = randomModelCreator.getStudentsCoursesRelations(students,
    // courses, coursesCount);

    // // then
    // assertEquals(actual.size(), students.size());
    // }

    // @Test
    // void
    // Method_getStudentsCoursesRelations_shouldReturnMapOfStudentCoursesRelations_withMaxThreeValuesPerKey()
    // {
    // // given
    // int coursesCount = 3;

    // // when
    // var actual = randomModelCreator.getStudentsCoursesRelations(students,
    // courses, coursesCount);
    // boolean anyMatch = actual.values().stream()
    // .mapToInt(Set::size)
    // .anyMatch(size -> size <= 3 || size >= 1);

    // // then
    // assertTrue(anyMatch);
    // }
}
