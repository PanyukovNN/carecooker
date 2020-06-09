package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Role;
import com.zylex.carecooker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    List<User> findByRolesContaining(Role role);
}
