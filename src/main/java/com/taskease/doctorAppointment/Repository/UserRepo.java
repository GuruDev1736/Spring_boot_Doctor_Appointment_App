package com.taskease.doctorAppointment.Repository;


import com.taskease.doctorAppointment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = :role AND u.enabled = false")
    List<User> disabledUsers(@Param("role") String role);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = :role AND u.enabled = true")
    List<User> enabledUsers(@Param("role") String role);


}
