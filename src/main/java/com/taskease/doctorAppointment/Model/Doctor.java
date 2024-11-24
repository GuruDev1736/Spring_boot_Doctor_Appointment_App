package com.taskease.doctorAppointment.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;
    private String address;
    private Integer age ;
    private String hospitalAddress ;
    private String qualification;
    private String services ;
    private String availability;
    private String startTime;
    private String EndTime;
    private Integer fees ;
    private String profileImage;
    @Column(unique = true)
    private String phoneNo;
    private boolean enabled ;
    @Column(unique = true)
    private String email ;
    private String password;
    private Date creationDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_role", joinColumns = @JoinColumn(name = "doctor", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Appointments> appointments = new HashSet<>();


    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<RatingAndReview> ratingAndReviews = new HashSet<>();


}
