package com.project.layer.Persistence.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Entity.UserId;

import jakarta.transaction.Transactional;

@Repository
public interface IUserAuthRepository extends JpaRepository<UserAuthentication, UserId> {

    @Query(value = "SELECT * FROM USER_AUTHENTICATION u WHERE u.USERNAME = :username", nativeQuery = true)
    Optional<UserAuthentication> findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_AUTHENTICATION u SET u.ISBLOCKED = 1 WHERE u.USERNAME = :username", nativeQuery = true)
    void blockPassword(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_AUTHENTICATION u SET u.ATTEMPTS = u.ATTEMPTS + 1 WHERE u.USERNAME = :username", nativeQuery = true)
    void incrementFailedAttempts(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_AUTHENTICATION u SET u.ATTEMPTS = 0 WHERE u.USERNAME = :username", nativeQuery = true)
    void resetFailedAttempts(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_AUTHENTICATION u SET u.ISBLOCKED = 0 WHERE u.USERNAME = :username", nativeQuery = true)
    void unBlockPassword(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_AUTHENTICATION u SET u.PASSWORD = :pass WHERE u.USERNAME = :username", nativeQuery = true)
    void updatePass(@Param("username") String username, @Param("pass") String pass);


}
