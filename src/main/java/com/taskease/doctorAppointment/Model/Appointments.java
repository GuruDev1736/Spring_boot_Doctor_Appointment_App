package com.taskease.doctorAppointment.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String date;
    private String time ;
    private Boolean paymentStatus;
    private String paymentId;
    private String amount ;
    private String title ;
    private String description;
    private String status;
    private String reason;
    private String refundAmount;
    public Date creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctor doctor;



}
