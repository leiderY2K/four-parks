package com.project.layer.Persistence.Repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Parking;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer>{
    
    @Query(
        value = "SELECT * FROM PARKING p "+
                    "JOIN CITY c ON p.FK_IDCITY = c.IDCITY "+
                    "JOIN SCHEDULE st ON p.FK_IDSCHEDULE = st.IDSCHEDULE "+
                    "LEFT JOIN PARKINGTYPE pt ON p.FK_IDPARKINGTYPE = pt.IDPARKINGTYPE "+
                "WHERE c.NAME = :city "+
                    "AND (:endTime IS NULL OR st.ENDTIME >= :endTime) "+
                    "AND (:startTime IS NULL OR st.STARTTIME <= :startTime) "+
                    "AND (:scheduleType IS NULL OR st.SCHEDULETYPE = :scheduleType) "+
                    "AND (:type IS NULL OR p.FK_IDPARKINGTYPE = :type)", 
        nativeQuery = true)
    List<Parking> queryParkingsByArgs(@Param("city") String city, @Param("type") String type,@Param("startTime") Time startTime, @Param("endTime") Time endTime, @Param("scheduleType") String scheduleType);

    @Query(
        value = "SELECT p.* FROM PARKING p " +
                "INNER JOIN ADDRESS a ON p.FK_COORDINATESX = a.COORDINATESX AND p.FK_COORDINATESY = a.COORDINATESY " +
                "WHERE a.COORDINATESX = :coordinateX AND a.COORDINATESY = :coordinateY",
        nativeQuery = true)
    Parking queryParkingByCoordinates(@Param("coordinateX") float coordinateX, @Param("coordinateY") float coordinateY);

}
