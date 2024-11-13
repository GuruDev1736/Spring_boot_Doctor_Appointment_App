package com.taskease.doctorAppointment.Service;

import com.taskease.doctorAppointment.PayLoad.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO createDoctor(UserDTO userDTO);

}
