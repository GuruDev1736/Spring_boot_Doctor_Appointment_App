package com.taskease.doctorAppointment.PayLoad;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private long id;
    private String fullName;
    private String address;
    @Size(min = 1 , max = 2 , message = "Please enter the valid age")
    private Integer age ;
    private String hospitalAddress ;
    private String qualification;
    private String services ;
    private String availability;
    private Integer fees ;
    private String startTime;
    private String EndTime;
    private String profileImage;
    @Size(min = 10, max = 10, message = "Phone No must be 10 Digits")
    private String phoneNo;
    private boolean enabled = true ;
    @Email(message = "Email Address is not valid")
    private String email ;
    @Size(min = 8, max = 15, message = "Password must be min 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date creationDate;
    private int ratingAndReview ;
}
