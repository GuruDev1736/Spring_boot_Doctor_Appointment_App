package com.taskease.doctorAppointment.Repository;

import com.taskease.doctorAppointment.Model.Appointments;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointments,Long> {

    List<Appointments> findByDoctor(Doctor doctor);
    List<Appointments> findByUser(User user);
}
