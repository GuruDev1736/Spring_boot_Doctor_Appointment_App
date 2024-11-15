package com.taskease.doctorAppointment.Controller;

import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable("id") long userId , @RequestBody UserDTO userDTO){
        UserDTO user = this.userService.updateUser(userId,userDTO);
        return ResponseEntity.ok(new ApiResponse<>("200","User Updated Successfully",user));
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<ApiResponse<Void>> enabledUser( @PathVariable("id") long userId){
        this.userService.enabledUser(userId);
        return new ResponseEntity<>(new ApiResponse<>("200","User Enabled Successfully",null), HttpStatus.OK);
    }
    @PutMapping("/disabled/{id}")
    public ResponseEntity<ApiResponse<Void>> disabledUser( @PathVariable("id") long userId){
        this.userService.disabledUser(userId);
        return new ResponseEntity<>(new ApiResponse<>("200","User Disabled Successfully",null), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllEnabledUsers()
    {
        List<UserDTO> allEnabledUser = this.userService.getAllEnabledUser();
        return ResponseEntity.ok(new ApiResponse<>("200","Users Fetched Successfully",allEnabledUser));
    }

    @GetMapping("/disabled")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllDisabledUsers()
    {
        List<UserDTO> allDisabledUsers = this.userService.getAllDisabledUser();
        return ResponseEntity.ok(new ApiResponse<>("200","Users Fetched Successfully",allDisabledUsers));
    }

}
