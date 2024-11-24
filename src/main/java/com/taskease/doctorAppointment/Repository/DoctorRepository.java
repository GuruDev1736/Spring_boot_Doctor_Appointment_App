package com.taskease.doctorAppointment.Repository;

import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Optional<Doctor> findByEmail(String email);

    @Query("SELECT u FROM Doctor u JOIN u.roles r WHERE r.roleName = :role AND u.enabled = false")
    List<Doctor> disabledDoctor(@Param("role") String role);

    @Query("SELECT u FROM Doctor u JOIN u.roles r WHERE r.roleName = :role AND u.enabled = true")
    List<Doctor> enabledDoctor(@Param("role") String role);
}
