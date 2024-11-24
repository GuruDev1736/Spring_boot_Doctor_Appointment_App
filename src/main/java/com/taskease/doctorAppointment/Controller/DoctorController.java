package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.DoctorDTO;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Service.DoctorService;
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
    private DoctorService doctorService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getAllEnabledDoctor()
    {
        List<DoctorDTO> allEnabledUser = this.doctorService.getAllEnabledDoctor();
        return ResponseEntity.ok(new ApiResponse<>("200","Doctors Fetched Successfully",allEnabledUser));
    }

    @GetMapping("/disabled")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getAllDisabledUsers()
    {
        List<DoctorDTO> allDisabledUsers = this.doctorService.getAllDisabledDoctor();
        return ResponseEntity.ok(new ApiResponse<>("200","Doctors Fetched Successfully",allDisabledUsers));
    }
}
