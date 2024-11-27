package com.taskease.doctorAppointment.Repository;

import com.taskease.doctorAppointment.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taskease.doctorAppointment.Model.RatingAndReview;

import java.util.List;

public interface RatingAndReviewRepo extends JpaRepository<RatingAndReview, Long> {

    List<RatingAndReview> findByDoctor(Doctor doctor);

}
