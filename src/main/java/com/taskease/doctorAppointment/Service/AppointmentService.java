package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentDTO createAppointment(long userId, long doctorId , AppointmentDTO appointmentDTO);
    AppointmentDTO postpondAppointment(long doctorId , long appointmentId , AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getAllAppointmentById(long doctorId);
    List<AppointmentDTO> getAllAppointmentByUserId(long userId);
    List<AppointmentDTO> getAllAppointment();
    String cancelAppointment(long appointmentId , AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointmentById(long appointmentId);


}
