
package com.learning.repository;

import com.learning.models.LessonProgress;
import java.util.ArrayList;

public class ProgressRepository {

    private ArrayList<LessonProgress> progressRecords;

    public ProgressRepository() {
        progressRecords = new ArrayList<>();
    }

    public void addProgress(LessonProgress progress) {
        if (progress != null) {
            progressRecords.add(progress);
        }
    }

    public LessonProgress findById(int progressId) {
        for (LessonProgress progress : progressRecords) {
            if (progress.getProgressId() == progressId) {
                return progress;
            }
        }
        return null;
    }

    public ArrayList<LessonProgress> findAll() {
        return new ArrayList<>(progressRecords);
    }
}
