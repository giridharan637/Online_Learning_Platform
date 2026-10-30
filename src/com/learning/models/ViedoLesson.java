package com.learning.models;

public class VideoLesson extends Lesson {

    private double watchedMinutes;

    public VideoLesson(int lessonId, String title,
                       int durationMinutes, int orderNumber) {

        super(lessonId, title, durationMinutes, orderNumber);
        this.watchedMinutes = 0;
    }

    public void watch(double minutes) {
        if (minutes > 0) {
            watchedMinutes += minutes;

            if (watchedMinutes > getDurationMinutes()) {
                watchedMinutes = getDurationMinutes();
            }
        }
    }

    public double getWatchedMinutes() {
        return watchedMinutes;
    }

    @Override
    public boolean isCompleted() {
        return watchedMinutes >= getDurationMinutes() * 0.90;
    }
}
