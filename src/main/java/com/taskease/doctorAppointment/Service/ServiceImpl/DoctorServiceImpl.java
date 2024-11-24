package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.Role;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.DoctorDTO;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Repository.DoctorRepository;
import com.taskease.doctorAppointment.Repository.RoleRepo;
import com.taskease.doctorAppointment.Service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private DoctorRepository doctorRepository ;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DoctorDTO updateDoctor(long userId, DoctorDTO userDTO) {
        return null;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO userDTO) {
        Doctor doctor = this.modelMapper.map(userDTO,Doctor.class);
        doctor.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = this.roleRepo.findById(Constants.DOCTOR_ROLE).get();
        doctor.getRoles().add(role);
        doctor.setCreationDate(new Date());
        Doctor save = this.doctorRepository.save(doctor);
        return this.modelMapper.map(save,DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getAllEnabledDoctor() {
        List<DoctorDTO> userDTOS = this.doctorRepository.enabledDoctor(Constants.DOCTOR_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,DoctorDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public List<DoctorDTO> getAllDisabledDoctor() {
        List<DoctorDTO> userDTOS = this.doctorRepository.disabledDoctor(Constants.DOCTOR_ROLE_NAME).stream().map(user -> this.modelMapper.map(user,DoctorDTO.class)).toList();
        return userDTOS;
    }

    @Override
    public void enabledDoctor(long doctorId) {

    }

    @Override
    public void disabledDoctor(long doctorId) {

    }
}
