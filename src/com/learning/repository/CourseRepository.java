
package com.learning.repository;

import com.learning.models.Course;
import java.util.ArrayList;

public class CourseRepository {

    private ArrayList<Course> courses;

    public CourseRepository() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
        }
    }

    public Course findById(int courseId) {
        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> findAll() {
        return new ArrayList<>(courses);
    }
}
