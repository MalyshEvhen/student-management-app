package ua.com.foxstudent102052.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import ua.com.foxstudent102052.model.dto.CourseDto;
import ua.com.foxstudent102052.model.dto.GroupDto;
import ua.com.foxstudent102052.model.dto.StudentDto;

@Component
public class RandomModelCreator {
    private static final Random random = new Random();

    public List<GroupDto> getGroups(List<String> groupNames) {
        var groupList = new ArrayList<GroupDto>();

        for (String groupName : groupNames) {
            var group = GroupDto.builder()
                    .groupName(groupName)
                    .build();
            groupList.add(group);
        }

        return groupList;
    }

    public List<CourseDto> getCourses(List<String[]> courses) {
        var courseDtoArrayList = new ArrayList<CourseDto>();

        for (var courseString : courses) {
            var courseDto = CourseDto.builder()
                    .courseName(courseString[0])
                    .courseDescription(courseString[1])
                    .build();
            courseDtoArrayList.add(courseDto);
        }

        return courseDtoArrayList;
    }

    public List<StudentDto> getStudents(List<String> names, List<String> surnames, List<GroupDto> groupDtoList,
            int studentsCount) {
        var studentDtoList = new ArrayList<StudentDto>();
        int groupCount = groupDtoList.size();

        for (int i = 0; i < studentsCount; i++) {
            studentDtoList.add(StudentDto.builder()
                    .group(groupDtoList.get(random.nextInt(groupCount)))
                    .firstName(names.get(random.nextInt(names.size())))
                    .lastName(surnames.get(random.nextInt(surnames.size())))
                    .build());
        }

        return studentDtoList;
    }

    public Map<Integer, Set<Integer>> getStudentsCoursesRelations(List<StudentDto> studentDtoList,
            List<CourseDto> courseDtoList, int maxCoursesCount) {
        int[] studentIds = studentDtoList.stream().mapToInt(StudentDto::getStudentId).toArray();
        int[] coursesIds = courseDtoList.stream().mapToInt(CourseDto::getCourseId).toArray();

        var studentCourseMap = new HashMap<Integer, Set<Integer>>();

        for (int studentId : studentIds) {
            var courses = new HashSet<Integer>();

            for (int j = 1; j <= maxCoursesCount; j++) {
                int courseId = coursesIds[random.nextInt(maxCoursesCount)];

                if (Boolean.FALSE.equals(courses.contains(courseId)) && courses.size() < 3) {
                    courses.add(courseId);
                }
            }
            studentCourseMap.put(studentId, courses);
        }

        return studentCourseMap;
    }
}
