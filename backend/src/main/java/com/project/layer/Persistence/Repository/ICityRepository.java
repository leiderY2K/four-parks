package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer>{

    @Query(
        value = "SELECT * FROM CITY c WHERE c.NAME = :city",
        nativeQuery = true
    )
    City queryCityByName(@Param("city") String city);

    @Query(
        value = "SELECT NAME FROM CITY",
        nativeQuery = true
    )
    List<String> queryCityList();
    
}
