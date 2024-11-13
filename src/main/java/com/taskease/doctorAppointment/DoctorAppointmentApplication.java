package com.taskease.doctorAppointment;

import com.taskease.doctorAppointment.Constant.Constants;
import com.taskease.doctorAppointment.Model.Role;
import com.taskease.doctorAppointment.Repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DoctorAppointmentApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo ;


	public static void main(String[] args) {
		SpringApplication.run(DoctorAppointmentApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}


	@Override
	public void run(String... args) throws Exception {
		try{
			Role role = new Role();
			role.setId(Constants.DOCTOR_ROLE);
			role.setRoleName(Constants.DOCTOR_ROLE_NAME);

			Role role1 = new Role();
			role1.setId(Constants.USER_ROLE);
			role1.setRoleName(Constants.USER_ROLE_NAME);

			List<Role> roles = List.of(role,role1);

			List<Role> result = roleRepo.saveAll(roles);

		}catch (Exception e ){
			e.printStackTrace();
		}
	}
}
