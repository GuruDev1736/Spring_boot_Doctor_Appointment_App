package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
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
import java.util.List;

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
    public UserDTO updateUser(long userId, UserDTO userDTO) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setFullName(userDTO.getFullName());
        user.setPhoneNo(userDTO.getPhoneNo());
        user.setAddress(userDTO.getAddress());

        User updatedData = this.userRepo.save(user);
        return this.modelMapper.map(updatedData,UserDTO.class);
    }

    @Override
    public void enabledUser(long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setEnabled(true);
        this.userRepo.save(user);
    }

    @Override
    public void disabledUser(long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setEnabled(false);
        this.userRepo.save(user);
    }

    @Override
    public List<UserDTO> getAllEnabledUser() {
        List<UserDTO> userDTOS = this.userRepo.enabledUsers(Constants.USER_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,UserDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public List<UserDTO> getAllDisabledUser() {
        List<UserDTO> userDTOS = this.userRepo.disabledUsers(Constants.USER_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,UserDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public UserDTO createDoctor(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = this.roleRepo.findById(Constants.DOCTOR_ROLE).get();
        user.getRoles().add(role);
        user.setCreationDate(new Date());
        User save = this.userRepo.save(user);
        return this.modelMapper.map(save,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllEnabledDoctor() {
        List<UserDTO> userDTOS = this.userRepo.enabledUsers(Constants.DOCTOR_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,UserDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public List<UserDTO> getAllDisabledDoctor() {
        List<UserDTO> userDTOS = this.userRepo.disabledUsers(Constants.DOCTOR_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,UserDTO.class)).toList();
        return userDTOS;
    }
}
