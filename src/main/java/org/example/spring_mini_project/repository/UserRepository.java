package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByEmail(String username);
}
