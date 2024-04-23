package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer>{
    
}
