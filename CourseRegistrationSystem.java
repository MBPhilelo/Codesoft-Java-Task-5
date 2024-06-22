/**
 * 
 */

/**
 * @author HP
 *
 */
import java.util.HashMap;
import java.util.Map;

public class CourseRegistrationSystem {
    private Map<String, Course> courses;
    private Map<String, Student> students;

    public CourseRegistrationSystem() {
        courses = new HashMap<>();
        students = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public void addStudent(Student student) {
        students.put(student.getStudentID(), student);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public Student getStudent(String studentID) {
        return students.get(studentID);
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public Map<String, Student> getStudents() {
        return students;
    }
}

