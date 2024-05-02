package com.project.layer.Persistence.Repository;

import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Entity.UserId;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, UserId> {

    @Query(
            value ="SELECT * FROM USER u WHERE u.IDUSER = :userId",
            nativeQuery = true
    )
    Optional<User> findByUserId(@Param("userId") String userId );

    @Query(
            value ="SELECT * FROM USER u WHERE u.PHONE = :phone",
            nativeQuery = true
    )
    Optional<User> findByPhone(@Param("phone") String phone);

    @Query(
            value ="SELECT * FROM USER u WHERE u.EMAIL = :email",
            nativeQuery = true
    )
    Optional<User> findByEmail(@Param("email") String email);

    /*
    @Query(
            value = "SELECT * FROM USER_AUTHENTICATION ua " +
                    "INNER JOIN USER u ON ua.IDUSER = u.IDUSER AND ua.FK_IDDOCTYPE = u.FK_IDDOCTYPE " +
                    "WHERE u.EMAIL = :email OR u.IDUSER = :idUser OR u.PHONE = :phone OR ua.USERNAME = :username",
            nativeQuery = true
    )
    Optional<UserAuthentication> findByRegister(@Param("email") String email, @Param("idUser") String idUser, @Param("phone") String phone, @Param("username") String username);
*/
}
