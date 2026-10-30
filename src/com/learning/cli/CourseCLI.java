
package com.learning.cli;

import java.util.Scanner;
import java.math.BigDecimal;

import com.learning.models.Course;
import com.learning.models.Instructor;
import com.learning.models.Section;
import com.learning.models.Lesson;
import com.learning.models.VideoLesson;
import com.learning.models.ReadingLesson;
import com.learning.models.QuizLesson;
import com.learning.enums.Category;
import com.learning.enums.CourseLevel;
import com.learning.service.CourseService;

public class CourseCLI {

    private Scanner scanner;
    private CourseService courseService;
    private Instructor instructor;

    public CourseCLI(CourseService courseService, Instructor instructor) {
        this.scanner = Input.SCANNER;
        this.courseService = courseService;
        this.instructor = instructor;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== COURSE MANAGEMENT =====");
            System.out.println("1. Create Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by ID");
            System.out.println("4. Add Section");
            System.out.println("5. Add Lesson to Section");
            System.out.println("6. Publish Course");
            System.out.println("7. Edit Course Details");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCourse();
                    break;

                case 2:
                    viewAllCourses();
                    break;

                case 3:
                    searchCourse();
                    break;

                case 4: addSection(); break;
                case 5: addLesson(); break;
                case 6: publishCourse(); break;
                case 7: editCourse(); break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void createCourse() {
        System.out.print("Enter Course ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.println("Categories: PROGRAMMING, DESIGN, BUSINESS, DATA_SCIENCE, LANGUAGE");
        System.out.print("Enter Category: ");
        Category category;
        try { category = Category.valueOf(scanner.nextLine().trim().toUpperCase()); }
        catch (IllegalArgumentException e) { System.out.println("Invalid category."); return; }

        System.out.println("Levels: BEGINNER, INTERMEDIATE, ADVANCED");
        System.out.print("Enter Level: ");
        CourseLevel level;
        try { level = CourseLevel.valueOf(scanner.nextLine().trim().toUpperCase()); }
        catch (IllegalArgumentException e) { System.out.println("Invalid level."); return; }

        System.out.print("Enter Price (0 for free): ");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();

        if (price.signum() < 0) { System.out.println("Price cannot be negative."); return; }
        Course course = new Course(
                id, title, description, category, level, price, instructor
        );

        courseService.createCourse(course);
    }

    private void viewAllCourses() {
        if (courseService.getAllCourses().isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course course : courseService.getAllCourses()) {
            System.out.println(
                    "ID: " + course.getCourseId()
                    + " | Title: " + course.getTitle()
                    + " | Category: " + course.getCategory()
                    + " | Level: " + course.getLevel()
                    + " | Price: " + course.getPrice()
                    + " | Published: " + course.isPublished()
            );
        }
    }

    private void searchCourse() {
        System.out.print("Enter Course ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Course course = courseService.findCourseById(id);

        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Category: " + course.getCategory());
            System.out.println("Level: " + course.getLevel());
            System.out.println("Price: " + course.getPrice());
            System.out.println("Published: " + course.isPublished());
        }
    }
    private Course chooseCourse() {
        System.out.print("Course ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Course course = courseService.findCourseById(id);
        if (course == null) System.out.println("Course not found.");
        return course;
    }

    private void addSection() {
        Course course = chooseCourse();
        if (course == null) return;
        System.out.print("Section ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        for (Section section : course.getSections()) {
            if (section.getSectionId() == id) { System.out.println("Section ID already exists."); return; }
        }
        System.out.print("Section title: ");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) { System.out.println("Title required."); return; }
        course.addSection(new Section(id, title));
        System.out.println("Section added.");
    }

    private void addLesson() {
        Course course = chooseCourse();
        if (course == null) return;
        System.out.print("Section ID: ");
        int sectionId = scanner.nextInt(); scanner.nextLine();
        Section selected = null;
        for (Section section : course.getSections()) {
            if (section.getSectionId() == sectionId) selected = section;
        }
        if (selected == null) { System.out.println("Section not found."); return; }
        System.out.print("Lesson ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        for (Section section : course.getSections()) {
            for (Lesson lesson : section.getLessons()) {
                if (lesson.getLessonId() == id) { System.out.println("Lesson ID already exists."); return; }
            }
        }
        System.out.print("Lesson title: ");
        String title = scanner.nextLine();
        System.out.print("Duration in minutes: ");
        int duration = scanner.nextInt(); scanner.nextLine();
        if (duration <= 0 || title.trim().isEmpty()) { System.out.println("Invalid title or duration."); return; }
        System.out.print("Lesson type (1 Video, 2 Reading, 3 Quiz): ");
        int type = scanner.nextInt(); scanner.nextLine();
        int order = 1;
        for (Section section : course.getSections()) order += section.getLessons().size();
        Lesson lesson;
        switch (type) {
            case 1: lesson = new VideoLesson(id, title, duration, order); break;
            case 2: lesson = new ReadingLesson(id, title, duration, order); break;
            case 3: lesson = new QuizLesson(id, title, duration, order); break;
            default: System.out.println("Invalid lesson type."); return;
        }
        selected.addLesson(lesson);
        System.out.println("Lesson added.");
    }

    private void publishCourse() {
        Course course = chooseCourse();
        if (course != null) course.publish();
    }

    private void editCourse() {
        Course course = chooseCourse();
        if (course == null) return;
        System.out.print("New title (blank to keep): ");
        String title = scanner.nextLine();
        if (!title.trim().isEmpty()) course.updateTitle(title);
        System.out.print("New description (blank to keep): ");
        String description = scanner.nextLine();
        if (!description.trim().isEmpty()) course.updateDescription(description);
        if (!course.isPublished()) {
            System.out.print("New category (blank to keep): ");
            String category = scanner.nextLine().trim();
            if (!category.isEmpty()) {
                try { course.updateCategory(Category.valueOf(category.toUpperCase())); }
                catch (IllegalArgumentException e) { System.out.println("Invalid category; unchanged."); }
            }
            System.out.print("New level (blank to keep): ");
            String level = scanner.nextLine().trim();
            if (!level.isEmpty()) {
                try { course.updateLevel(CourseLevel.valueOf(level.toUpperCase())); }
                catch (IllegalArgumentException e) { System.out.println("Invalid level; unchanged."); }
            }
        } else System.out.println("Category and level locked after publishing.");
        System.out.println("Course details updated.");
    }
}
