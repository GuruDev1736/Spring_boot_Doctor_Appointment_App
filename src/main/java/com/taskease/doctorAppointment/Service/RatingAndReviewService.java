package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.RatingAndReviewDTO;

import java.util.List;

public interface RatingAndReviewService {
    RatingAndReviewDTO createRating(RatingAndReviewDTO ratingAndReviewDTO , long userId , long doctorId);
    List<RatingAndReviewDTO> getAllRatingOfDoctor(long doctorId);
}
