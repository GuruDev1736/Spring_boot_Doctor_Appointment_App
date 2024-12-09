package com.taskease.doctorAppointment.Service.ServiceImpl;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.RatingAndReview;
import com.taskease.doctorAppointment.Model.Role;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.DoctorDTO;
import com.taskease.doctorAppointment.PayLoad.RatingAndReviewDTO;
import com.taskease.doctorAppointment.PayLoad.UserDTO;
import com.taskease.doctorAppointment.Repository.DoctorRepository;
import com.taskease.doctorAppointment.Repository.RatingAndReviewRepo;
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

    private final ModelMapper modelMapper ;

    private final DoctorRepository doctorRepository ;

    private final RatingAndReviewRepo ratingAndReviewRepo;


    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(ModelMapper modelMapper, DoctorRepository doctorRepository, RatingAndReviewRepo ratingAndReviewRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.doctorRepository = doctorRepository;
        this.ratingAndReviewRepo = ratingAndReviewRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

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

    @Override
    public DoctorDTO getDoctorById(long id) {
        // Fetch the doctor entity
        Doctor doctor = this.doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));

        // Map Doctor entity to DoctorDTO
        DoctorDTO doctorDTO = this.modelMapper.map(doctor, DoctorDTO.class);

        // Fetch ratings and reviews for the doctor
        List<RatingAndReview> ratingsAndReviews = this.ratingAndReviewRepo.findByDoctor(doctor);

        // Calculate the average rating
        if (!ratingsAndReviews.isEmpty()) {
            double averageRating = ratingsAndReviews.stream()
                    .mapToInt(RatingAndReview::getRating) // Extract the rating
                    .average() // Calculate average
                    .orElse(0.0); // Default to 0.0 if no ratings
            doctorDTO.setRatingAndReview((int) Math.round(averageRating)); // Assign the rounded average
        } else {
            doctorDTO.setRatingAndReview(0); // No reviews, set rating to 0
        }

        return doctorDTO;
    }
}
