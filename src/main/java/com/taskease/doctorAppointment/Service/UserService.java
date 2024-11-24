package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.UserDTO;

import java.util.List;

public interface UserService {

    // User
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(long userId , UserDTO userDTO);
    List<UserDTO> getAllEnabledUser();
    List<UserDTO> getAllDisabledUser();


    //common
    void enabledUser(long userId);
    void disabledUser(long userId);
}
