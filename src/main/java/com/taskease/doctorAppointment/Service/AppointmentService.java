package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;

public interface AppointmentService {

    AppointmentDTO createAppointment(long userId, AppointmentDTO appointmentDTO);
    AppointmentDTO postpondAppointment(long doctorId , long appointmentId , AppointmentDTO appointmentDTO);


}
