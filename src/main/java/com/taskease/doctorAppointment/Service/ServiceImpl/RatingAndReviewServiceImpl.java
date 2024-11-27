package com.taskease.doctorAppointment.Service.ServiceImpl;


import com.taskease.doctorAppointment.Exception.ResourceNotFoundException;
import com.taskease.doctorAppointment.Model.Doctor;
import com.taskease.doctorAppointment.Model.RatingAndReview;
import com.taskease.doctorAppointment.Model.User;
import com.taskease.doctorAppointment.PayLoad.RatingAndReviewDTO;
import com.taskease.doctorAppointment.Repository.DoctorRepository;
import com.taskease.doctorAppointment.Repository.RatingAndReviewRepo;
import com.taskease.doctorAppointment.Repository.UserRepo;
import com.taskease.doctorAppointment.Service.RatingAndReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RatingAndReviewServiceImpl implements RatingAndReviewService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private RatingAndReviewRepo ratingAndReviewRepo;

    @Override
    public RatingAndReviewDTO createRating(RatingAndReviewDTO ratingAndReviewDTO, long userId, long doctorId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        Doctor doctor = this.doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","Id",doctorId));

        RatingAndReview ratingAndReview = this.modelMapper.map(ratingAndReviewDTO , RatingAndReview.class);
        ratingAndReview.setDate(new Date());
        ratingAndReview.setUser(user);
        ratingAndReview.setDoctor(doctor);
        RatingAndReview save = this.ratingAndReviewRepo.save(ratingAndReview);
        return this.modelMapper.map(save, RatingAndReviewDTO.class);
    }

    @Override
    public List<RatingAndReviewDTO> getAllRatingOfDoctor(long doctorId) {
        Doctor doctor = this.doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","Id",doctorId));
        List<RatingAndReviewDTO> list = this.ratingAndReviewRepo.findByDoctor(doctor).stream().map(ratingAndReview -> this.modelMapper.map(ratingAndReview, RatingAndReviewDTO.class)).toList();
        return list;
    }
}
