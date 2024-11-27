package com.taskease.doctorAppointment.PayLoad;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingAndReviewDTO {

    private long id;
    private int rating;
    private String review;
    private Date date;
    private UserDTO user;

}
