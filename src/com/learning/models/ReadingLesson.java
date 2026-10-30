package com.learning.models;

public class ReadingLesson extends Lesson {

    private boolean read;

    public ReadingLesson(int lessonId, String title,
                         int durationMinutes, int orderNumber) {

        super(lessonId, title, durationMinutes, orderNumber);
        this.read = false;
    }

    public void markAsRead() {
        read = true;
    }

    @Override
    public boolean isCompleted() {
        return read;
    }
}