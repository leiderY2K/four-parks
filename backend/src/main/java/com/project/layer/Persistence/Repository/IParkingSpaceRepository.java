package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ParkingSpaceId;

@Repository
public interface IParkingSpaceRepository extends JpaRepository<ParkingSpace, ParkingSpaceId>{

    @Query(
        value = "SELECT DISTINCT ps.* FROM PARKINGSPACE ps " +
                    "LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING " +
                    "WHERE p.FK_IDCITY = :idCity AND p.IDPARKING = :idParking",
        nativeQuery = true
    )
    List<ParkingSpace> findAllByParking(@Param("idParking") int idParking,@Param("idCity") String idCity);

    @Query(
        value = "SELECT COUNT(*) FROM PARKINGSPACE ps " +
                    "LEFT JOIN PARKING p ON ps.FK_IDPARKING = p.IDPARKING " +
                    "WHERE p.FK_IDCITY = :idCity AND p.IDPARKING = :idParking " +
                    "AND (:vehicleType IS NULL OR ps.FK_IDVEHICLETYPE = :vehicleType)",
        nativeQuery = true
    )
    Integer countByParkingAndVehicleType(@Param("idParking") int idParking,@Param("idCity") String idCity,@Param("vehicleType") String vehicleType);

}
