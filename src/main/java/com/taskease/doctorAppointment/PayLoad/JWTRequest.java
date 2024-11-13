package com.taskease.doctorAppointment.PayLoad;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JWTRequest {

    private  String email ;
    private  String password ;
}
