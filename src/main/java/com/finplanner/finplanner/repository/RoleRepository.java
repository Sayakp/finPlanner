package com.finplanner.finplanner.repository;

import com.finplanner.finplanner.model.Role;
import com.finplanner.finplanner.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(UserRole role);
}
