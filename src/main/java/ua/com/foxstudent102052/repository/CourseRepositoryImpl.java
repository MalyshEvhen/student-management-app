package ua.com.foxstudent102052.repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxstudent102052.model.Course;
import ua.com.foxstudent102052.model.Student;

import java.util.List;

@Slf4j
public class CourseRepositoryImpl implements CourseRepository {
    DAOFactory daoFactory;
    
    public CourseRepositoryImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public void addCourse(@NonNull Course course) {
        daoFactory.doPost(String.format("""
                INSERT
                INTO courses (course_name, course_description)
                VALUES ('%s', '%s');""",
            course.getCourseName(),
            course.getCourseDescription()));
    }

    @Override
    public void removeCourse(int id) {
        daoFactory.doPost(String.format("""
                DELETE
                FROM courses
                WHERE course_id = %d;""",
            id));
    }

    @Override
    public void updateCourseName(int courseId, String courseName) {
        daoFactory.doPost(String.format("""
                UPDATE courses
                SET course_name = '%s'
                WHERE course_id = %d;""",
            courseName,
            courseId));
    }

    @Override
    public void updateCourseDescription(int courseId, String courseDescription) {
        daoFactory.doPost(String.format("""
                UPDATE courses
                SET course_description = '%s'
                WHERE course_id = %d;""",
            courseDescription,
            courseId));
    }

    @Override
    public void updateCourse(@NonNull Course course) {
        daoFactory.doPost(String.format("""
                UPDATE courses
                SET (course_name, course_description) = ('%s', '%s')
                WHERE course_id = %d;""",
            course.getCourseName(),
            course.getCourseDescription(),
            course.getCourseId()));
    }

    @Override
    public List<Course> getAllCourses() {
        String query = "SELECT * FROM courses;";

        return daoFactory.getCourses(query);
    }

    @Override
    public Course getCourseById(int courseId) {
        String query = String.format("""
                SELECT *
                FROM courses
                WHERE course_id = %d;""",
            courseId);

        return daoFactory.getCourse(query);
    }

    @Override
    public Course getCourseByName(String courseName) {
        String query = String.format("""
                SELECT *
                FROM courses
                WHERE course_name = '%s';""",
            courseName);
        
        return daoFactory.getCourse(query);
    }

    @Override
    public List<Student> getStudentsByCourseId(int courseId) {
        String query = String.format("""
                SELECT *
                FROM students
                WHERE student_id IN (
                    SELECT student_id
                    FROM students_courses
                    WHERE course_id = %d);""",
            courseId);

        return daoFactory.getStudents(query);
    }
}
