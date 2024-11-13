package com.taskease.doctorAppointment.PayLoad;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JWTResponse {
    private  String token ;
    private String email ;
    private Long userId ;
    private String fullName;
    private String userRole;
    private String userProfilePic;
}
