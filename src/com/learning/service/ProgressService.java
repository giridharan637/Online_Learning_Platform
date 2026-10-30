
package com.learning.service;

import com.learning.models.Course;
import com.learning.models.Enrollment;
import com.learning.models.Lesson;
import com.learning.models.LessonProgress;
import com.learning.models.Section;
import com.learning.enums.EnrollmentStatus;
import com.learning.repository.ProgressRepository;

import java.util.ArrayList;

public class ProgressService {

    private ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public boolean markLessonCompleted(
            Enrollment enrollment, Lesson lesson) {

        if (enrollment == null || lesson == null) {
            System.out.println("Enrollment or lesson is missing.");
            return false;
        }

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            System.out.println("Enrollment must be active.");
            return false;
        }

        Course course = enrollment.getCourse();
        ArrayList<Lesson> allLessons = new ArrayList<>();

        for (Section section : course.getSections()) {
            allLessons.addAll(section.getLessons());
        }

        boolean lessonFound = false;

        for (Lesson currentLesson : allLessons) {
            if (currentLesson.getLessonId() == lesson.getLessonId()) {
                lessonFound = true;
                break;
            }
        }

        if (!lessonFound) {
            System.out.println("Lesson does not belong to this course.");
            return false;
        }

        // Every lesson before the requested lesson must be completed.
        for (Lesson currentLesson : allLessons) {
            if (currentLesson.getLessonId() == lesson.getLessonId()) {
                break;
            }

            LessonProgress previousProgress =
                    findProgress(enrollment, currentLesson);

            if (previousProgress == null
                    || !previousProgress.isCompleted()) {
                System.out.println(
                        "Complete the previous lessons first."
                );
                return false;
            }
        }

        // Check the lesson's own completion rule.
        if (!lesson.isCompleted()) {
            System.out.println(
                    "Lesson requirements have not been completed."
            );
            return false;
        }

        LessonProgress existing = findProgress(enrollment, lesson);

        if (existing != null) {
            existing.markCompleted();
        } else {
            int nextId = 1;

            for (LessonProgress record : progressRepository.findAll()) {
                if (record.getProgressId() >= nextId) {
                    nextId = record.getProgressId() + 1;
                }
            }

            LessonProgress progress =
                    new LessonProgress(nextId, enrollment, lesson);

            progress.markCompleted();
            progressRepository.addProgress(progress);
        }

        System.out.println("Lesson completed successfully.");

        if (getProgressPercentage(enrollment) >= 100.0) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            System.out.println("Course completed successfully!");
        }

        return true;
    }

    private LessonProgress findProgress(
            Enrollment enrollment, Lesson lesson) {

        for (LessonProgress progress : progressRepository.findAll()) {
            if (progress.getEnrollment().getEnrollmentId()
                        == enrollment.getEnrollmentId()
                    && progress.getLesson().getLessonId()
                        == lesson.getLessonId()) {
                return progress;
            }
        }

        return null;
    }

    public double getProgressPercentage(Enrollment enrollment) {
        if (enrollment == null) {
            return 0.0;
        }

        int totalLessons = 0;
        int completedLessons = 0;

        for (Section section : enrollment.getCourse().getSections()) {
            for (Lesson lesson : section.getLessons()) {
                totalLessons++;

                LessonProgress progress = findProgress(enrollment, lesson);

                if (progress != null && progress.isCompleted()) {
                    completedLessons++;
                }
            }
        }

        if (totalLessons == 0) {
            return 0.0;
        }

        return completedLessons * 100.0 / totalLessons;
    }
}
