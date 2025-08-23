package com.phegon.PhegonAirline.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.phegon.PhegonAirline.entities.Role;
import java.util.Optional;



public interface RoleRepo extends JpaRepository<Role,Long>{
    Optional<Role> findByName(String name);
}
