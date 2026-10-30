package com.learning.models;

public abstract class Lesson {

    private int lessonId;
    private String title;
    private int durationMinutes;
    private int orderNumber;

    public Lesson(int lessonId, String title,
                  int durationMinutes, int orderNumber) {

        this.lessonId = lessonId;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.orderNumber = orderNumber;
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public abstract boolean isCompleted();
}