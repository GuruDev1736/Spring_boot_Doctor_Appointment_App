package com.taskease.doctorAppointment.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private long doctorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;



}
