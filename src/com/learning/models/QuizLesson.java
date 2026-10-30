package com.learning.models;

import java.time.LocalDateTime;

public class QuizLesson extends Lesson {

    private double score;
    private int attempts;
    private LocalDateTime nextAttemptTime;
    private boolean passed;

    public QuizLesson(int lessonId, String title,
                      int durationMinutes, int orderNumber) {

        super(lessonId, title, durationMinutes, orderNumber);

        this.score = 0;
        this.attempts = 0;
        this.nextAttemptTime = null;
        this.passed = false;
    }

    public boolean submitQuiz(double score) {

        if (passed) {
            System.out.println("You have already passed this quiz.");
            return true;
        }

        if (nextAttemptTime != null
                && LocalDateTime.now().isBefore(nextAttemptTime)) {

            System.out.println("Please wait until: " + nextAttemptTime);
            return false;
        }

        if (nextAttemptTime != null
                && !LocalDateTime.now().isBefore(nextAttemptTime)) {

            attempts = 0;
            nextAttemptTime = null;
        }

        if (score < 0 || score > 100) {
            System.out.println("Score must be between 0 and 100.");
            return false;
        }

        attempts++;
        this.score = score;

        if (score >= 60) {
            passed = true;
            System.out.println("Quiz passed!");
            return true;
        }

        System.out.println("Quiz failed. Attempts used: " + attempts);

        if (attempts == 3) {
            nextAttemptTime = LocalDateTime.now().plusHours(24);
            System.out.println("Three attempts failed. Wait 24 hours.");
        }

        return false;
    }

    public int getAttempts() {
        return attempts;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean isCompleted() {
        return passed;
    }
}