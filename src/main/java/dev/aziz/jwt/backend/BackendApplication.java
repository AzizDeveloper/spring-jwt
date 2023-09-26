package dev.aziz.jwt.backend;

import dev.aziz.jwt.backend.config.PasswordConfig;
import dev.aziz.jwt.backend.entites.Role;
import dev.aziz.jwt.backend.entites.User;
import dev.aziz.jwt.backend.repositories.RoleRepository;
import dev.aziz.jwt.backend.repositories.UserRepository;
import dev.aziz.jwt.backend.repositories.VideoRepository;
import dev.aziz.jwt.backend.entites.Video;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			RoleRepository roleRepository,
			VideoRepository videoRepository,
			PasswordConfig passwordConfig) {
		return args -> {
			Video video1 = new Video(1L,"001 User Signup Page.mp4", "uploads/001 User Signup Page.mp4");
			Video video2 = new Video(2L,"002 Post User (Backend).mp4", "uploads/002 Post User (Backend).mp4");
			Video video3 = new Video(3L,"004 Response Body (Backend).mp4", "uploads/004 Response Body (Backend).mp4");
			Video video4 = new Video(4L,"008 Handling Input Change (Frontend).mp4", "uploads/008 Handling Input Change (Frontend).mp4");
			Video video5 = new Video(5L,"011 Sending Requests to Backend (Frontend).mp4", "uploads/011 Sending Requests to Backend (Frontend).mp4");

			videoRepository.saveAll(List.of(video1, video2, video3, video4, video5));

			Role userRole = new Role("USER");
			Role adminRole = new Role("ADMIN");

			roleRepository.save(userRole);
			roleRepository.save(adminRole);

			User aziz = new User(
					"aziz",
					"abdukarimov",
					"azizdev",
					passwordConfig.passwordEncoder().encode("qweasd"),
					Set.of(userRole, adminRole),
					List.of(video1)
			);

			userRepository.save(aziz);

			User bob = new User(
					"bob",
					"john",
					"bobdev",
					passwordConfig.passwordEncoder().encode("asdasd"),
					Set.of(userRole),
					List.of(video2, video3)
			);
			userRepository.save(bob);

			User azim = new User(
					"azim",
					"abdukarimov",
					"azimdev",
					passwordConfig.passwordEncoder().encode("asdasd"),
					Set.of(userRole),
					List.of(video4, video5)
			);
			userRepository.save(azim);

		};
	}
}
