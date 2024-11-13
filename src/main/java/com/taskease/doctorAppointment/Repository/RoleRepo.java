package com.taskease.doctorAppointment.Repository;


import com.taskease.doctorAppointment.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
