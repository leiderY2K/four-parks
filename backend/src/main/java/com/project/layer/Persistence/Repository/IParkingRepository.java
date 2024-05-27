package com.project.layer.Persistence.Repository;

import java.sql.Time;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingId;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, ParkingId> {

        @Query(value = "SELECT DISTINCT p.* FROM PARKING p " +
                        "JOIN CITY c ON p.FK_IDCITY = c.IDCITY " +
                        "JOIN SCHEDULE st ON p.FK_IDSCHEDULE = st.IDSCHEDULE " +
                        "LEFT JOIN PARKINGTYPE pt ON p.FK_IDPARKINGTYPE = pt.IDPARKINGTYPE " +
                        "WHERE c.NAME = :city " +
                        "AND (:endTime IS NULL OR p.ENDTIME >= :endTime) " +
                        "AND (:startTime IS NULL OR p.STARTTIME <= :startTime) " +
                        "AND (:scheduleType IS NULL OR st.SCHEDULETYPE = :scheduleType) " +
                        "AND (:type IS NULL OR p.FK_IDPARKINGTYPE = :type)", nativeQuery = true)
        List<Parking> queryParkingsByArgs(
                        @Param("city") String city,
                        @Param("type") String type,
                        @Param("startTime") Time startTime,
                        @Param("endTime") Time endTime,
                        @Param("scheduleType") String scheduleType);

        @Query(value = "SELECT p.* FROM PARKING p " +
                        "INNER JOIN ADDRESS a ON p.FK_COORDINATESX = a.COORDINATESX AND p.FK_COORDINATESY = a.COORDINATESY "
                        +
                        "WHERE a.COORDINATESX = :coordinateX AND a.COORDINATESY = :coordinateY", nativeQuery = true)
        Parking queryParkingByCoordinates(@Param("coordinateX") float coordinateX,
                        @Param("coordinateY") float coordinateY);

        @Query(value = "SELECT COUNT(*) FROM PARKINGSPACE AS ps WHERE ps.FK_IDPARKING = :parkingId " +
                        "AND ps.ISUNCOVERED = :isUncovered " +
                        "AND ps.FK_IDVEHICLETYPE = :vehicleType", nativeQuery = true)
        int countByCoveredAndParkingAndVehicleType(
                        @Param("parkingId") int parkingId,
                        @Param("isUncovered") Boolean isUncovered,
                        @Param("vehicleType") String vehicleType);

        @Query(value = "SELECT DISTINCT V.IDVEHICLETYPE " +
                        "FROM FOURPARKSDATABASE.PARKINGSPACE PS " +
                        "INNER JOIN FOURPARKSDATABASE.VEHICLETYPE V ON PS.FK_IDVEHICLETYPE = V.IDVEHICLETYPE " +
                        "WHERE PS.FK_IDPARKING = :parkingId", nativeQuery = true)
        List<String> getTypeVehicleByParking(@Param("parkingId") int parkingId);

        @Query(value = "SELECT * FROM PARKING p " +
                        "WHERE p.FK_ADMIN_IDUSER = :idUser " +
                        "AND p.FK_ADMIN_IDDOCTYPE = :idDoctype ", nativeQuery = true)
        Parking findByAdminId(
                        @Param("idUser") String idUser,
                        @Param("idDoctype") String idDoctype);

        @Query(value = "SELECT CAPACITY FROM PARKING p " +
                        "WHERE p.IDPARKING = :idParking ", nativeQuery = true)
        int getCapacity(int idParking);

        @Query(
                value = "SELECT p.* FROM PARKING p " +
                        "WHERE FK_IDCITY = :idCity",                
                nativeQuery = true
        )
        List<Parking> findByCityId(@Param("idCity") String idCity);
}
