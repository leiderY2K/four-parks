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
        value = "SELECT * FROM PARKING p WHERE p.CITY_IDCITY = ?1",
        nativeQuery = true
    )
    List<Parking> queryParkingsPerCity(@Param("city") String city);

}
