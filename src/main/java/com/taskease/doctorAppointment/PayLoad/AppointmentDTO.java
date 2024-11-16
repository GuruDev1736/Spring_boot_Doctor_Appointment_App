package com.taskease.doctorAppointment.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private long id ;
    private String date;
    private String time ;
    private Boolean paymentStatus;
    private String paymentId;
    private String amount ;
    private String title ;
    private String description;
    private String status = "BOOKED";
    private String reason;
    private String refundAmount;
    private long doctorId;
    private UserDTO user;
}
