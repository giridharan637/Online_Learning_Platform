
package com.learning.models;

import java.time.LocalDateTime;

public class LessonProgress {

    private int progressId;
    private Enrollment enrollment;
    private Lesson lesson;
    private boolean completed;
    private double watchedPercentage;
    private LocalDateTime completedAt;

    public LessonProgress(int progressId, Enrollment enrollment,
                          Lesson lesson) {
        this.progressId = progressId;
        this.enrollment = enrollment;
        this.lesson = lesson;
        this.completed = false;
        this.watchedPercentage = 0;
        this.completedAt = null;
    }

    public int getProgressId() {
        return progressId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isCompleted() {
        return completed;
    }

    public double getWatchedPercentage() {
        return watchedPercentage;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setWatchedPercentage(double percentage) {
        if (percentage >= 0 && percentage <= 100) {
            this.watchedPercentage = percentage;
        }
    }

    public void markCompleted() {
        if (!completed) {
            completed = true;
            completedAt = LocalDateTime.now();
        }
    }
}
