package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.EmailService;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Appointments;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.AppointmentDTO;
import com.taskease.doctorAppointment.Repository.AppointmentRepo;
import com.taskease.doctorAppointment.Repository.DoctorRepository;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Service.AppointmentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService ;

    @Override
    public AppointmentDTO createAppointment(long userId , long doctorId, AppointmentDTO appointmentDTO) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        Doctor doctor = this.doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","Id",doctorId));
        Appointments appointments = this.modelMapper.map(appointmentDTO,Appointments.class);
        appointments.setUser(user);
        appointments.setDoctor(doctor);
        appointments.setCreationDate(new Date());
        Appointments save = this.appointmentRepo.save(appointments);
        return this.modelMapper.map(save,AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO postpondAppointment(long doctorId , long appointmentId, AppointmentDTO appointmentDTO) {
        Appointments appointments = this.appointmentRepo.findById(appointmentId).orElseThrow(()-> new ResourceNotFoundException("Appointment","id",appointmentId));
        Doctor doctor  = this.doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","id",doctorId));
        appointments.setDate(appointmentDTO.getDate());
        appointments.setTime(appointmentDTO.getTime());
        appointments.setTitle(appointmentDTO.getTitle());
        appointments.setDescription(appointmentDTO.getDescription());
        Appointments save = this.appointmentRepo.save(appointments);
        emailService.postpondAppointment(doctor.getEmail(), save.getDate(),save.getTime(),save.getUser().getFullName(),save.getUser().getEmail(),save.getUser().getPhoneNo(),doctor.getFullName());
        return this.modelMapper.map(save,AppointmentDTO.class);
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentById(long doctorId) {
        Doctor doctor  = this.doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","id",doctorId));

        List<AppointmentDTO> list = this.appointmentRepo.findByDoctor(doctor).stream().map(appointments -> this.modelMapper.map(appointments,AppointmentDTO.class)).toList();
        return list;
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentByUserId(long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        List<AppointmentDTO> list = this.appointmentRepo.findByUser(user).stream().map(appointments -> this.modelMapper.map(appointments,AppointmentDTO.class)).toList();
        return list;
    }

    @Override
    public List<AppointmentDTO> getAllAppointment() {
        List<AppointmentDTO> appointmentDTOList = this.appointmentRepo.findAll().stream().map(appointments -> this.modelMapper.map(appointments,AppointmentDTO.class)).toList();
        return appointmentDTOList;
    }

    @Transactional
    @Override
    public String cancelAppointment(long appointmentId , AppointmentDTO appointmentDTO) {
        Appointments appointments = this.appointmentRepo.findById(appointmentId).orElseThrow(()-> new ResourceNotFoundException("Appointment","id",appointmentId));
        appointments.setStatus("CANCEL");
        appointments.setReason(appointmentDTO.getReason());

        int hour = (int) calculateDifferenceInHours(appointments.getCreationDate());
        if (hour>24)
        {
            int refundAmount = calculateRefund(Integer.parseInt(appointments.getAmount()));
            appointments.setRefundAmount(String.valueOf(refundAmount));
        }
        else
        {
            appointments.setRefundAmount(appointments.getAmount());
        }

        String doctorEmail = appointments.getDoctor().getEmail();
        String doctorName = appointments.getDoctor().getFullName();
        String userName = appointments.getUser().getFullName();
        String userEmail = appointments.getUser().getEmail();
        String phoneNo = appointments.getUser().getPhoneNo();
        emailService.cancelAppointment(doctorEmail,userName,userEmail,phoneNo,doctorName);
        this.appointmentRepo.save(appointments);

        return null;
    }

    @Override
    public AppointmentDTO getAppointmentById(long appointmentId) {
        Appointments appointments = this.appointmentRepo.findById(appointmentId).orElseThrow(()-> new ResourceNotFoundException("Appointment","id",appointmentId));
        return this.modelMapper.map(appointments,AppointmentDTO.class);
    }

    public long calculateDifferenceInHours(Date storedTimestamp) {
        // Convert the stored timestamp to LocalDateTime
        LocalDateTime storedTime = LocalDateTime.ofInstant(storedTimestamp.toInstant(), ZoneId.systemDefault());

        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Calculate the duration between the two timestamps
        Duration duration = Duration.between(storedTime, currentTime);

        // Get the difference in hours
        return duration.toHours();
    }

    public int calculateRefund(int amount) {
        return (int) (amount * 0.8);
    }

}
