package com.project.layer.Persistence.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Entity.UserId;

@Repository
public interface IUserAuthRepository extends JpaRepository<UserAuthentication, UserId> {
    Optional<UserAuthentication> findByUsername(String username);
}
