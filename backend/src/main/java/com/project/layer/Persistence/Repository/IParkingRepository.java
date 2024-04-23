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
        value = "SELECT * FROM PARKING p, CITY c WHERE p.CITY_IDCITY = c.IDCITY AND c.DESCCITY = :city",
        nativeQuery = true
    )
    List<Parking> queryParkingsByCity(@Param("city") String city);

    @Query(
        value = "SELECT p.* FROM PARKING p INNER JOIN ADDRESS a ON p.ADDRESS_IDADDRESS = a.IDADDRESS " +
            "WHERE a.COORDINATESX = :coordinateX AND a.COORDINATESY = :coordinateY",
        nativeQuery = true)
    Parking queryParkingByCoordinates(@Param("coordinateX") String coordinateX, @Param("coordinateY") String coordinateY);

}
