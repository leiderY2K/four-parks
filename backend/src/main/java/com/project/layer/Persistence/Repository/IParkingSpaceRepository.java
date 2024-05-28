package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ParkingSpaceId;

import jakarta.transaction.Transactional;

@Repository
public interface IParkingSpaceRepository extends JpaRepository<ParkingSpace, ParkingSpaceId>{

    @Query(
        value = "SELECT DISTINCT ps.* FROM PARKINGSPACE ps " +
                    "LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING " +
                    "WHERE p.FK_IDCITY = :idCity AND p.IDPARKING = :idParking " +
                        "AND ps.FK_IDVEHICLETYPE = :idVehicleType " +
                        "AND ps.ISUNCOVERED = :isUncovered",
        nativeQuery = true
    )
    List<ParkingSpace> findAllByParking(
        @Param("idParking") int idParking,
        @Param("idCity") String idCity,
        @Param("idVehicleType") String idVehicleType,
        @Param("isUncovered") Boolean isUncovered
    );

    @Query(
        value = "SELECT COUNT(*) FROM PARKINGSPACE ps " +
                    "LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING " +
                    "WHERE p.FK_IDCITY = :idCity AND p.IDPARKING = :idParking ",
        nativeQuery = true
    )
    Integer countByParking(
        @Param("idParking") int idParking,
        @Param("idCity") String idCity
    );

    @Query(
        value = "SELECT COUNT(*) FROM PARKINGSPACE ps " +
                    "LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING " +
                    "WHERE p.FK_IDCITY = :idCity AND p.IDPARKING = :idParking " +
                    "AND (:vehicleType IS NULL OR ps.FK_IDVEHICLETYPE = :vehicleType)",
        nativeQuery = true
    )
    Integer countByParkingAndVehicleType(
        @Param("idParking") int idParking,
        @Param("idCity") String idCity,
        @Param("vehicleType") String vehicleType
    );

    @Transactional
    @Modifying
    @Query(
        value = "DELETE FROM PARKINGSPACE ps " +
                    "WHERE ps.FK_IDPARKING = :parkingId " + 
                        "AND ps.ISUNCOVERED = :isUncovered " +
                        "AND ps.FK_IDCITY = :cityId " + 
                        "AND ps.FK_IDVEHICLETYPE = :vehicleTypeId " +
                    "ORDER BY IDPARKINGSPACE DESC LIMIT :amount",
        nativeQuery = true
    )
    void deleteByParking(
        @Param("parkingId") Integer parkingId,
        @Param("cityId") String cityId, 
        @Param("isUncovered") Boolean isUncovered,
        @Param("vehicleTypeId") String vehicleTypeId,
        @Param("amount") Integer amount
    );

    @Query(value="SELECT * FROM PARKINGSPACE ps WHERE ps.FK_IDCITY = :cityId",nativeQuery = true)
    List<ParkingSpace> allCityParkingSpaces(@Param("cityId") String cityId);

}
