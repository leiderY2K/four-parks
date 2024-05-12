package com.project.layer.Persistence.Repository;

import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Entity.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICardRepository extends JpaRepository<Card, UserId> {

    @Query(
            value ="SELECT * FROM CARD u WHERE u.FK_CLIENT_IDUSER LIKE %:userId%",
            nativeQuery = true
    )
    Optional<Card> findByUserId(@Param("userId") String userId );
    //List<Card> findByUserId(@Param("userId") String userId );
   // List<Card> findByUserId(UserId userId);
    @Query(
            value = "SELECT  * FROM CARD c WHERE c.SERIALCARD = :serialCard",
            nativeQuery = true
    )
    Optional<Card> findByCardNumber(@Param("serialCard") String serialCard );

    @Query(
            value = "SELECT  * FROM CARD c WHERE c.EXPIRYDATECARD = :ExpDateCard",
            nativeQuery = true
    )
    Optional<Card> findByExpDateCard(@Param("ExpDateCard") String ExpDateCard );


    @Query(
            value = "SELECT  * FROM CARD c WHERE c.CVVCARD = :cvvCard",
            nativeQuery = true
    )
    Optional<Card> findByCvvCard(@Param("cvvCard") String cvvCard );

    @Query(
            value ="SELECT * FROM CARD u WHERE u.IDUSER = :userId AND u.EXPIRYDATECARD = :expDate",
            nativeQuery = true
    )
    List<Card> findByUserIdAndExpDateCard(@Param("userId") String userId, @Param("expDate") LocalDate expDate);

}

