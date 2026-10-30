package com.learning.models;

import java.util.ArrayList;

public class Section {

    private int sectionId;
    private String title;
    private ArrayList<Lesson> lessons;

    public Section(int sectionId, String title) {
        this.sectionId = sectionId;
        this.title = title;
        this.lessons = new ArrayList<>();
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null) {
            lessons.add(lesson);
        }
    }
}