package com.example.rendezVous;

import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.Role;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.repositories.RoleRepository;
import com.example.rendezVous.repositories.UserRepository;
import com.example.rendezVous.services.userServices.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RendezVousApplication {
	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(RendezVousApplication.class, args);
	}
	@Bean
	CommandLineRunner run(RoleRepository roleRpository , RoleService roleService, UserRepository userRepository){
		return args -> {

			if (roleRpository.count()<1) {
				roleRpository.save(new Role(null, ERole.ROLE_ADMIN));
				roleRpository.save(new Role(null,ERole.ROLE_PRO));
				roleRpository.save(new Role(null,ERole.ROLE_USER));
			}
			if(!userRepository.existsByEmail("Admin")){
				UserModel user = new UserModel(
						"Admin",

						encoder.encode("password")
				);
				Role userRole = roleService.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				user.getRoles().add(userRole);
				userRepository.save(user);
			}
		};
	}
}
