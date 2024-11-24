package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.Exception.ErrorException;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.*;
import com.taskease.doctorAppointment.Repository.DoctorRepository;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Security.JwtHelper;
import com.taskease.doctorAppointment.Service.DoctorService;
import com.taskease.doctorAppointment.Service.ServiceImpl.CustomUserDetailService;
import com.taskease.doctorAppointment.Service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JWTResponse>> login(@RequestBody JWTRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        // Load UserDetails and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        // Check email in User table
        Optional<User> optionalUser = this.userRepo.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

            JWTResponse response = buildJWTResponse(
                    token,
                    userDetails.getUsername(),
                    userDTO.getId(),
                    userDTO.getFullName(),
                    userDTO.getProfileImage(),
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst()
                            .orElse("")
            );

            return new ResponseEntity<>(new ApiResponse<>("200", "User Logged Successfully", response), HttpStatus.OK);
        } else {
            Doctor doctor = this.doctorRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Email: " + userDetails.getUsername(), 0));


            DoctorDTO doctorDTO = this.modelMapper.map(doctor, DoctorDTO.class);

            JWTResponse response = buildJWTResponse(
                    token,
                    userDetails.getUsername(),
                    doctorDTO.getId(),
                    doctorDTO.getFullName(),
                    doctorDTO.getProfileImage(),
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst()
                            .orElse("")
            );

            return new ResponseEntity<>(new ApiResponse<>("200", "Doctor Logged Successfully", response), HttpStatus.OK);
        }
    }

    private JWTResponse buildJWTResponse(
              String token ,
              String email ,
              Long userId ,
              String fullName,
              String userProfilePic,
              String userRole
    ) {
        return JWTResponse.builder()
                .token(token)
                .userId(userId)
                .fullName(fullName)
                .userProfilePic(userProfilePic)
                .userRole(userRole)
                .email(email)
                .build();
    }

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO)
    {
        UserDTO userDTO1 = this.userService.createUser(userDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","User Created Successfully",userDTO1));
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<ApiResponse<DoctorDTO>> createDoctor(@RequestBody DoctorDTO userDTO)
    {
        DoctorDTO userDTO1 = this.doctorService.createDoctor(userDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","Doctor Created Successfully",userDTO1));
    }


    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new ErrorException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
