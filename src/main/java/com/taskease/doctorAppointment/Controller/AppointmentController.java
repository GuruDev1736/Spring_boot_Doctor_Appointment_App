package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;
import com.taskease.doctorAppointment.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @PostMapping("/{id}/{doctorId}/create")
    public ResponseEntity<ApiResponse<AppointmentDTO>> createAppointment(@PathVariable("id") long userId , @PathVariable("doctorId") long doctorId, @RequestBody AppointmentDTO appointmentDTO){
        AppointmentDTO appointment = this.appointmentService.createAppointment(userId , doctorId, appointmentDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","Appointment Created Successfully",appointment));
    }

    @PutMapping("/{doctorId}/{appointmentId}/update")
    public ResponseEntity<ApiResponse<AppointmentDTO>> postpondAppointment(@PathVariable("doctorId") long doctorId , @PathVariable("appointmentId")long appointmentId , @RequestBody AppointmentDTO appointmentDTO)
    {
        AppointmentDTO appointmentDTO1 = this.appointmentService.postpondAppointment(doctorId, appointmentId, appointmentDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","Appointment Postponded Successfully",appointmentDTO1));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAllAppointmentById(@PathVariable("doctorId") long doctorId)
    {
        List<AppointmentDTO> list = this.appointmentService.getAllAppointmentById(doctorId);
        return ResponseEntity.ok(new ApiResponse<>("200","Fetched All the Appointments",list));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAllAppointmentByUserId(@PathVariable("userId") long userId)
    {
        List<AppointmentDTO> list = this.appointmentService.getAllAppointmentByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>("200","Fetched All the Appointments",list));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAllAppointment()
    {
        List<AppointmentDTO> list = this.appointmentService.getAllAppointment();
        return ResponseEntity.ok(new ApiResponse<>("200","Fetched All the Appointments",list));
    }

    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> cancelAppointment(@PathVariable("appointmentId") long appointmentId , @RequestBody AppointmentDTO appointmentDTO)
    {
        this.appointmentService.cancelAppointment(appointmentId,appointmentDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","Appointment Successfully Cancelled",""));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentDTO>> getAppointmentById(@PathVariable("appointmentId") long appointmentId)
    {
        AppointmentDTO appointmentDTO = this.appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(new ApiResponse<>("200","Appointment Successfully Fetched",appointmentDTO));
    }

}
