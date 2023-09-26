package dev.aziz.jwt.backend.repositories;

import dev.aziz.jwt.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    User findUserById(Long id);
}
