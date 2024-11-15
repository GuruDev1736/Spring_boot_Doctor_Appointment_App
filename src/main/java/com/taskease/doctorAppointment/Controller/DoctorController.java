package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllEnabledDoctor()
    {
        List<UserDTO> allEnabledUser = this.userService.getAllEnabledUser();
        return ResponseEntity.ok(new ApiResponse<>("200","Users Fetched Successfully",allEnabledUser));
    }

    @GetMapping("/disabled")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllDisabledUsers()
    {
        List<UserDTO> allDisabledUsers = this.userService.getAllDisabledUser();
        return ResponseEntity.ok(new ApiResponse<>("200","Users Fetched Successfully",allDisabledUsers));
    }
}
