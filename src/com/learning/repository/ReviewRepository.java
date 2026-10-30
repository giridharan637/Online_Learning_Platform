
package com.learning.repository;

import com.learning.models.Review;
import java.util.ArrayList;

public class ReviewRepository {

    private ArrayList<Review> reviews;

    public ReviewRepository() {
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }

    public Review findById(int reviewId) {
        for (Review review : reviews) {
            if (review.getReviewId() == reviewId) {
                return review;
            }
        }
        return null;
    }

    public ArrayList<Review> findAll() {
        return new ArrayList<>(reviews);
    }
}
