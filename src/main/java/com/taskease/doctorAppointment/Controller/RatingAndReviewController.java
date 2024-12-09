package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.RatingAndReviewDTO;
import com.taskease.doctorAppointment.Service.RatingAndReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingAndReviewController {

    @Autowired
    private RatingAndReviewService ratingAndReviewService;


    @PostMapping("/create/{userId}/{doctorId}")
    public ResponseEntity<ApiResponse<RatingAndReviewDTO>> creatingRating(@RequestBody RatingAndReviewDTO ratingAndReviewDTO , @PathVariable("doctorId") long doctorId , @PathVariable("userId") long userId)
    {
        RatingAndReviewDTO ratingAndReviewDTO1 = this.ratingAndReviewService.createRating(ratingAndReviewDTO,userId,doctorId);
        return ResponseEntity.ok(new ApiResponse<>("200","Rating Posted Successfully",ratingAndReviewDTO1));
    }


    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<List<RatingAndReviewDTO>>> getAllRatingOfTheDoctor(@PathVariable("doctorId") long doctorId)
    {
        List<RatingAndReviewDTO> list  = this.ratingAndReviewService.getAllRatingOfDoctor(doctorId);
        return ResponseEntity.ok(new ApiResponse<>("200","Fetched All the Ratings",list));
    }
}
