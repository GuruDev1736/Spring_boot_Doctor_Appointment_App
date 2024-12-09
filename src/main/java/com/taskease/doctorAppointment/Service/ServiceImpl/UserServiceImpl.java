package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Constant.EmailService;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Role;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Repository.RoleRepo;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Constant.OTPService;
import com.taskease.doctorAppointment.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepo userRepo ;

    private final ModelMapper modelMapper ;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final OTPService otpService;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, RoleRepo roleRepo, PasswordEncoder passwordEncoder, EmailService emailService, OTPService otpService) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.otpService = otpService;
    }

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
    public void sendOTP(String email) {
        User user  = this.userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email is not Register",email,0));

        String otp = otpService.generateOtp(email);
        emailService.sendOtp(email, otp);
    }

    @Override
    public Boolean validateOTP(String email, String otp) {
        boolean isValid = otpService.validateOtp(email, otp);
        if (isValid) {
            otpService.clearOtp(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void changePassword(String email , String password) {
        Optional<User> optionalUser = this.userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(password));
            this.userRepo.save(user);
        }

    }

}
