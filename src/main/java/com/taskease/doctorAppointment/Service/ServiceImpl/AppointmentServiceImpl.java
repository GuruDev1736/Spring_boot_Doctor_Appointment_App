package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.EmailService;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Appointments;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;
import com.taskease.doctorAppointment.Repository.AppointmentRepo;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService ;

    @Override
    public AppointmentDTO createAppointment(long userId, AppointmentDTO appointmentDTO) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        this.userRepo.findById(appointmentDTO.getDoctorId()).orElseThrow(()-> new ResourceNotFoundException("Doctor","id",appointmentDTO.getDoctorId()));
        Appointments appointments = this.modelMapper.map(appointmentDTO,Appointments.class);
        appointments.setUser(user);
        Appointments save = this.appointmentRepo.save(appointments);
        return this.modelMapper.map(save,AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO postpondAppointment(long doctorId , long appointmentId, AppointmentDTO appointmentDTO) {
        Appointments appointments = this.appointmentRepo.findById(appointmentId).orElseThrow(()-> new ResourceNotFoundException("Appointment","id",appointmentId));
        User doctor  = this.userRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","id",doctorId));
        appointments.setDate(appointmentDTO.getDate());
        appointments.setTime(appointmentDTO.getTime());
        appointments.setTitle(appointmentDTO.getTitle());
        appointments.setDescription(appointmentDTO.getDescription());
        Appointments save = this.appointmentRepo.save(appointments);
        emailService.postpondAppointment(doctor.getEmail(), save.getDate(),save.getTime(),save.getUser().getFullName(),save.getUser().getEmail(),save.getUser().getPhoneNo(),doctor.getFullName());
        return this.modelMapper.map(save,AppointmentDTO.class);
    }

    //TODO get all appoints for the user  , get all the appointment for the doctor , cancel the appointment and send the email to the doctor that the appointment has been cancelled
}
