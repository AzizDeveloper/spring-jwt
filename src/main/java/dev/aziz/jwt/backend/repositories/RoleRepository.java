package dev.aziz.jwt.backend.repositories;

import dev.aziz.jwt.backend.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
