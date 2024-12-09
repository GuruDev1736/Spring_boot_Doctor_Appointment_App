package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.DoctorDTO;
import com.taskease.doctorAppointment.PayLoad.UserDTO;

import java.util.List;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO userDTO);
    DoctorDTO updateDoctor(long userId , DoctorDTO userDTO);
    List<DoctorDTO> getAllEnabledDoctor();
    List<DoctorDTO> getAllDisabledDoctor();
    void enabledDoctor(long doctorId);
    void disabledDoctor(long doctorId);
    DoctorDTO getDoctorById(long id);
}
