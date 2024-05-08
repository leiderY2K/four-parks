package com.project.layer.Persistence.Repository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Reservation;


@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer>{
    
    @Query(
        value = "SELECT * FROM RESERVATION r, USER c " + 
                    "WHERE " +
                        "r.FK_CLIENT_IDUSER = c.IDUSER " +
                        "AND r.FK_CLIENT_IDDOCTYPE = c.FK_IDDOCTYPE "+
                        "AND c.IDUSER = :clientId "+
                        "AND c.FK_IDDOCTYPE = :docType",
        nativeQuery = true
    )
    List<Reservation> findAllByClientId(
        @Param("clientId") String clientId,
        @Param("docType") String doctype
    );

    @Query(
        value = "SELECT r.FK_IDPARKINGSPACE FROM RESERVATION r " +
                    "WHERE " +
                        "r.FK_IDCITY = :cityId " +
                        "AND r.FK_IDPARKING = :parkingId " +
                        "AND r.FK_IDVEHICLETYPE = :vehicleType " +
                        "AND r.DATERES = :dateRes " +
                        "AND ( " +
                            "(r.STARTTIMERES <= ADDTIME(:startTimeRes, '-59:00') AND r.ENDTIMERES > ADDTIME(:startTimeRes, '-59:00')) " +
                            "OR (r.STARTTIMERES < ADDTIME(:endTimeRes, '59:00') AND r.ENDTIMERES >= ADDTIME(:endTimeRes, '59:00')) " +
                            "OR (r.STARTTIMERES >= ADDTIME(:startTimeRes, '-59:00') AND r.ENDTIMERES <= ADDTIME(:endTimeRes, '59:00'))" +
                        ")",
        nativeQuery = true
    )
    List<Integer> findBusyParkingSpaces(
        @Param("cityId") String cityId, 
        @Param("parkingId") int parkingId,
        @Param("vehicleType") String vehicleType,
        @Param("dateRes") Date dateRes, 
        @Param("startTimeRes") Time startTimeRes,
        @Param("endTimeRes") Time endTimeRes
    );


}