package com.taskease.doctorAppointment.Repository;

import com.taskease.doctorAppointment.Model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointments,Long> {
}
