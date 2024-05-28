package com.project.layer.Persistence.Repository;

import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserDebt;

import com.project.layer.Persistence.Entity.UserDebtId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUserDebtRepository extends JpaRepository<UserDebt, UserDebtId> {


    @Query(
            value = "SELECT DEBTAMOUNT " +
                    "FROM USERDEBT " +
                    "WHERE IDUSER = :userId AND FK_IDDOCTYPE = :idDocType " +
                    "GROUP BY IDUSER",
            nativeQuery = true
    )
    List<Integer> findDebtByUserId(@Param("userId") String userId, @Param("idDocType") String idDocType);


    @Transactional
    @Modifying
    @Query("UPDATE UserDebt ud SET ud.debtAmount = :newDebtAmount, ud.owes = :newOwes WHERE ud.debt.clientDebt = :client")
    void updateUserDebtTransactional(@Param("newDebtAmount") int newDebtAmount, @Param("newOwes") boolean newOwes, @Param("client") User client);

}

