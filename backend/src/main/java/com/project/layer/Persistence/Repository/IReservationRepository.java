package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer>{

    @Query(
        value = "SELECT * FROM RESERVATION r, CLIENT c " + 
                    "WHERE r.FK_CLIENT_IDUSER = c.IDUSER " +
                        "AND r.FK_CLIENT_IDDOCTYPE = c.FK_IDDOCTYPE "+
                        "AND c.IDUSER = :clientId "+
                        "AND c.FK_IDDOCTYPE = :docType",
        nativeQuery = true
    )
    List<Reservation> findAllByClientId(@Param("clientId") String clientId, @Param("docType") String doctype);

    
}