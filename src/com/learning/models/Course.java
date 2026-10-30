

package com.learning.models;

import com.learning.enums.Category;
import com.learning.enums.CourseLevel;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Course {

    private int courseId;
    private String title;
    private String description;
    private Category category;
    private CourseLevel level;
    private BigDecimal price;
    private Instructor instructor;
    private boolean published;
    private ArrayList<Section> sections;

    public Course(int courseId, String title, String description,
                  Category category, CourseLevel level,
                  BigDecimal price, Instructor instructor) {

        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.price = price;
        this.instructor = instructor;
        this.published = false;
        this.sections = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean publish() {
        if (sections.isEmpty()) {
            System.out.println("Cannot publish: Add at least one section.");
            return false;
        }

        int totalLessons = 0;

        for (Section section : sections) {
            totalLessons += section.getLessons().size();
        }

        if (totalLessons < 3) {
            System.out.println("Cannot publish: Add at least 3 lessons.");
            return false;
        }

        published = true;
        System.out.println("Course published successfully!");
        return true;
    }

    public void addSection(Section section) {
        if (section != null) {
            sections.add(section);
        }
    }

    public ArrayList<Section> getSections() {
        return new ArrayList<>(sections);
    }

    public boolean updateCategory(Category newCategory) {
        if (published) {
            System.out.println("Cannot change category after publishing.");
            return false;
        }

        if (newCategory == null) {
           System.out.println("Category cannot be empty.");
           return false;
        }

        this.category = newCategory;
        return true;
    }

    public boolean updateLevel(CourseLevel newLevel) {
        if (published) {
           System.out.println("Cannot change level after publishing.");
           return false;
        }

       if (newLevel == null) {
           System.out.println("Level cannot be empty.");
           return false;
        }

        this.level = newLevel;
       return true;
    }
    public void updateTitle(String newTitle) {
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            this.title = newTitle;
        }
    }


    public void updateDescription(String newDescription) {
        if (newDescription != null && !newDescription.trim().isEmpty()) {
            this.description = newDescription;
        }
    }
}

