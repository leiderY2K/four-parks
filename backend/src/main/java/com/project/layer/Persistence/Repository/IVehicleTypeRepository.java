package com.project.layer.Persistence.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.VehicleType;

@Repository
public interface IVehicleTypeRepository extends JpaRepository<VehicleType,String>{
    @Query(value="SELECT IDVEHICLETYPE",nativeQuery=true)
    List<String> getIdVehicles();
}
