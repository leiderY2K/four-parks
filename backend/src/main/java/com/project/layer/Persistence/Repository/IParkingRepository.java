package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Parking;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer>{
    
    @Query(
        value = "SELECT * FROM PARKING p, CITY c WHERE p.CITY_IDCITY = c.IDCITY AND c.NAME = :city",
        nativeQuery = true
    )
    List<Parking> queryParkingsByCity(@Param("city") String city);

    @Query(
        value = "SELECT * FROM PARKING p, CITY c, PARKINGTYPE pt WHERE p.CITY_IDCITY = c.IDCITY AND c.NAME = :city " + 
                                                                "AND p.PARKINGTYPE_IDPARKINGTYPE = pt.IDPARKINGTYPE AND pt.DESCPARKINGTYPE = :type",
        nativeQuery = true
    )
    List<Parking> queryParkingsByCityAndType(@Param("city") String city, @Param("type") String type);

    @Query(
        value = "SELECT p.* FROM PARKING p " +
                "INNER JOIN ADDRESS a ON p.ADDRESS_COORDINATESX = a.COORDINATESX AND p.ADDRESS_COORDINATESY = a.COORDINATESY " +
                "WHERE a.COORDINATESX = :coordinateX AND a.COORDINATESY = :coordinateY",
        nativeQuery = true)
    Parking queryParkingByCoordinates(@Param("coordinateX") float coordinateX, @Param("coordinateY") float coordinateY);

}
