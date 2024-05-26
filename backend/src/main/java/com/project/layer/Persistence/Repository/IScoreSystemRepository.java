package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ParkingId;
import com.project.layer.Persistence.Entity.ScoreSystem;

@Repository
public interface IScoreSystemRepository extends JpaRepository<ScoreSystem, ParkingId>{
    @Query(
        value = "SELECT ss.* FROM SCORESYSTEM ss, PARKING p " +
                    "WHERE ss.IDPARKING = p.IDPARKING " +
                        "AND ss.FK_IDCITY = p.FK_IDCITY " +
                        "AND p.FK_ADMIN_IDUSER = :idUser " +
                        "AND p.FK_ADMIN_IDDOCTYPE = :idDocType",
        nativeQuery = true
    )
    ScoreSystem findByAdminId(
        @Param("idUser") String idUser,
        @Param("idDocType") String idDocType
    );

    @Query(
        value = "SELECT ss.* FROM SCORESYSTEM ss " +
                    "WHERE ss.IDPARKING = :idParking " +
                        "AND ss.FK_IDCITY = :idCity ",
        nativeQuery = true
    )
    ScoreSystem findByParkingId(
        @Param("idParking") Integer idParking,
        @Param("idCity") String idCity
    );
}
