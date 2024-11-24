package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;
import com.taskease.doctorAppointment.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
