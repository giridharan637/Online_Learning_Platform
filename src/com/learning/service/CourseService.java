
package com.learning.service;

import com.learning.models.Course;
import com.learning.repository.CourseRepository;
import java.util.ArrayList;

public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public boolean createCourse(Course course) {
        if (course == null) {
            System.out.println("Course cannot be empty.");
            return false;
        }

        if (courseRepository.findById(course.getCourseId()) != null) {
            System.out.println("Course ID already exists.");
            return false;
        }

        courseRepository.addCourse(course);
        System.out.println("Course created successfully.");
        return true;
    }

    public Course findCourseById(int courseId) {
        return courseRepository.findById(courseId);
    }

    public ArrayList<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}


// public CourseService(CourseRepository courseRepository) {
//     this.courseRepository = courseRepository;
// } 
// We pass a repository object into the service
//  instead of creating a new one inside it. 
//  This is called dependency injection.