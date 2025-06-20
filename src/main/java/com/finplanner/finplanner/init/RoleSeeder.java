package com.finplanner.finplanner.init;

import com.finplanner.finplanner.model.Role;
import com.finplanner.finplanner.model.UserRole;
import com.finplanner.finplanner.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for(UserRole role: UserRole.values()){
            roleRepository.findByRole(role).orElseGet(()->{
                Role newRole = new Role();
                newRole.setRole(role);
                return roleRepository.save(newRole);
            });
        }
    }
}
