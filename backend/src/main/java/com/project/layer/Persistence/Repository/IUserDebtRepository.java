package com.project.layer.Persistence.Repository;

import com.project.layer.Persistence.Entity.UserDebt;
import com.project.layer.Persistence.Entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUserDebtRepository extends JpaRepository<UserDebt, UserId> {
    @Query(
            value ="SELECT * FROM USERDEBT u WHERE u.IDUSER LIKE %:userId% AND u.debtamount > 0",
            nativeQuery = true
    )
    List<UserDebt> findPendingDebtsByUserId(String userId);
}
