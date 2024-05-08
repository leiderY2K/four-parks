package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.layer.Persistence.Entity.Rate;
import com.project.layer.Persistence.Entity.RateId;

public interface IRateRepository extends JpaRepository<Rate, RateId> {
    
    @Query(
        value = "SELECT DISTINCT rt.HOURCOST FROM RATE AS rt " +
                        "WHERE rt.FK_IDPARKING = :idParking " +
                        "AND rt.FK_IDCITY = :idCity " +
                        "AND rt.FK_IDVEHICLETYPE = :idVehicleType " +
                        "AND rt.ISUNCOVERED = :isUncovered",
        nativeQuery = true
    )
    Integer getHourCostByParkingSpace(
        @Param("idParking") int idParking,
        @Param("idCity") String idCity,
        @Param("idVehicleType") String idVehicleType,
        @Param("isUncovered") boolean isUncovered
    );

    @Query(
        value = "SELECT rt.RESERVATIONCOST FROM RATE AS rt " +
                        "WHERE rt.FK_IDPARKING = :idParking " +
                        "AND rt.FK_IDCITY = :idCity " +
                        "AND rt.FK_IDVEHICLETYPE = :idVehicleType " +
                        "AND rt.ISUNCOVERED = :isUncovered",
        nativeQuery = true
    )
    Integer getReservationCostByParkingSpace(
        @Param("idParking") int idParking,
        @Param("idCity") String idCity,
        @Param("idVehicleType") String idVehicleType,
        @Param("isUncovered") boolean isUncovered
    );


}
