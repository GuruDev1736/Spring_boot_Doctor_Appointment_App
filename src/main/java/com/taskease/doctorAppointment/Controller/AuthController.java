package com.taskease.doctorAppointment.Controller;


import com.taskease.doctorAppointment.Exception.ErrorException;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.JWTRequest;
import com.taskease.doctorAppointment.PayLoad.JWTResponse;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Security.JwtHelper;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsService userDetailsService;

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

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        User user = this.userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email" + userDetails.getUsername(), 0));

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

        JWTResponse response = JWTResponse.builder()
                .token(token)
                .userId(userDTO.getId())
                .fullName(userDTO.getFullName())
                .userProfilePic(userDTO.getProfileImage())
                .userRole(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(""))
                .email(userDetails.getUsername()).build();

        return new ResponseEntity<>(new ApiResponse<>("200","User Logged In Successfully",response), HttpStatus.OK);
    }


    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO)
    {
        UserDTO userDTO1 = this.userService.createUser(userDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","User Created Successfully",userDTO1));
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<ApiResponse<UserDTO>> createDoctor(@RequestBody UserDTO userDTO)
    {
        UserDTO userDTO1 = this.userService.createDoctor(userDTO);
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
