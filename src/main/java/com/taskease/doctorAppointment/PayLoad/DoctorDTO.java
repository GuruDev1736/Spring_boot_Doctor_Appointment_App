package com.taskease.doctorAppointment.PayLoad;

import jakarta.persistence.Column;
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
    private Integer age ;
    private String hospitalAddress ;
    private String qualification;
    private String services ;
    private String availability;
    private String startTime;
    private String EndTime;
    private Integer fees ;
    private String profileImage;
    private String phoneNo;
    private boolean enabled ;
    private String email ;
    private String password;
    private Date creationDate;
}
