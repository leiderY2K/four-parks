package com.project.layer.Persistence.Repository;

import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, UserId> {

}
