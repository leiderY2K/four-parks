package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ParkingSpaceId;

@Repository
public interface IParkingSpaceRepository extends JpaRepository<ParkingSpace, ParkingSpaceId> {
    
   @Query(
       value = "SELECT COUNT(*) FROM PARKINGSPACE AS ps WHERE ps.FK_IDPARKING = :parkingId " +
                     "AND ps.ISCOVERED = :isCovered " +
                     "AND ps.FK_IDVEHICLETYPE = :vehicleType",
       nativeQuery = true
       )
   int countByCoveredAndParkingAndVehicleType(@Param("parkingId") int parkingId,
                                    @Param("isCovered") Boolean isCovered,                                
                                    @Param("vehicleType") String vehicleType);
   
   @Query(
      value = "SELECT DISTINCT V.IDVEHICLETYPE " + 
               "FROM FOURPARKSDATABASE.PARKINGSPACE PS " + 
               "INNER JOIN FOURPARKSDATABASE.VEHICLETYPE V ON PS.FK_IDVEHICLETYPE = V.IDVEHICLETYPE " + 
               "WHERE PS.FK_IDPARKING = :parkingId",
      nativeQuery = true
   )
   List<String> getTypeVehicleByParking(@Param("parkingId") int parkingId);


}
