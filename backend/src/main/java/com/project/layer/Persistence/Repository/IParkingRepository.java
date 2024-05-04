package com.project.layer.Persistence.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingSpace;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer>{
    
    @Query(
    value = "SELECT DISTINCT p.* FROM PARKING p " +
                "JOIN CITY c ON p.FK_IDCITY = c.IDCITY " +
                "JOIN SCHEDULE st ON p.FK_IDSCHEDULE = st.IDSCHEDULE " +
                "LEFT JOIN PARKINGTYPE pt ON p.FK_IDPARKINGTYPE = pt.IDPARKINGTYPE " +
            "WHERE c.NAME = :city " +
                "AND (:endTime IS NULL OR st.ENDTIME >= :endTime) "+
                "AND (:startTime IS NULL OR st.STARTTIME <= :startTime) "+
                "AND (:scheduleType IS NULL OR st.SCHEDULETYPE = :scheduleType) " +
                "AND (:type IS NULL OR p.FK_IDPARKINGTYPE = :type)",
    nativeQuery = true)
    List<Parking> queryParkingsByArgs(
        @Param("city") String city,
        @Param("type") String type,
        @Param("startTime") Time startTime,
        @Param("endTime") Time endTime,
        @Param("scheduleType") String scheduleType
    );

    @Query(
        value = "SELECT p.* FROM PARKING p " +
                "INNER JOIN ADDRESS a ON p.FK_COORDINATESX = a.COORDINATESX AND p.FK_COORDINATESY = a.COORDINATESY " +
                "WHERE a.COORDINATESX = :coordinateX AND a.COORDINATESY = :coordinateY",
        nativeQuery = true)
    Parking queryParkingByCoordinates(@Param("coordinateX") float coordinateX, @Param("coordinateY") float coordinateY);

    @Query(
       value = "SELECT COUNT(*) FROM PARKINGSPACE AS ps WHERE ps.FK_IDPARKING = :parkingId " +
                     "AND ps.ISCOVERED = :isCovered " +
                     "AND ps.FK_IDVEHICLETYPE = :vehicleType",
       nativeQuery = true
       )
    int countByCoveredAndParkingAndVehicleType(
                                    @Param("parkingId") int parkingId,
                                    @Param("isCovered") Boolean isCovered,                                
                                    @Param("vehicleType") String vehicleType
                                    );
   
    @Query(
        value = "SELECT DISTINCT V.IDVEHICLETYPE " + 
                "FROM FOURPARKSDATABASE.PARKINGSPACE PS " + 
                "INNER JOIN FOURPARKSDATABASE.VEHICLETYPE V ON PS.FK_IDVEHICLETYPE = V.IDVEHICLETYPE " + 
                "WHERE PS.FK_IDPARKING = :parkingId",
        nativeQuery = true
    )
    List<String> getTypeVehicleByParking(@Param("parkingId") int parkingId);

    @Query(
    value = "SELECT NEW ParkingSpace(ps.parkingSpaceId.idParkingSpace, ps.parkingSpaceId.idParking, ps.parkingSpaceId.idCity, ps.isCovered, ps.idVehicleType ) " +
                "FROM ParkingSpace AS ps " +
                "WHERE ps.parkingSpaceId.idCity = :cityId " +
                "AND ps.parkingSpaceId.idParking = :parkingId " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM Reservation r " + 
                "    WHERE r.parkingSpace.parkingSpaceId.idParkingSpace = ps.parkingSpaceId.idParkingSpace " + 
                "    AND r.dateRes = :dateRes " +
                "    AND ( " +
                "        (r.startTimeRes <= :startTimeRes AND r.endTimeRes > :startTimeRes) " +
                "        OR (r.startTimeRes < :endTimeRes AND r.endTimeRes >= :endTimeRes) " +
                "        OR (r.startTimeRes >= :startTimeRes AND r.endTimeRes <= :endTimeRes) " +
                "    ) " +
                ")"
    )
    List<ParkingSpace> getAvailableParkingSpace(
        @Param("cityId") String cityId, 
        @Param("parkingId") String parkingId, 
        @Param("dateRes") Date dateRes, 
        @Param("startTimeRes") Time startTimeRes,
        @Param("endTimeRes") Time endTimeRes
    );

    @Query(
    value = "SELECT rt.HOURCOST FROM RATE AS rt " +
            "WHERE rt.FK_IDPARKING = :idParking " +
            "AND rt.ISCOVERED = :isCovered " +
            "AND rt.FK_IDVEHICLETYPE = :vehicleType",
    nativeQuery = true
    )
    Integer getRateByVehicleType(
        @Param("idParking") int idParking,
        @Param("isCovered") boolean isCovered,
        @Param("vehicleType") String vehicleType
    );




}
