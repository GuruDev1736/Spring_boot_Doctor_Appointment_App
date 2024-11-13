package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Model.Role;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Repository.RoleRepo;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.Date;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepo userRepo ;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = this.roleRepo.findById(Constants.USER_ROLE).get();
        user.getRoles().add(role);

        user.setCreationDate(new Date());

        User save = this.userRepo.save(user);
        return this.modelMapper.map(save,UserDTO.class);
    }

    @Override
    public UserDTO createDoctor(UserDTO userDTO) {
        return null;
    }
}
