package com.project.layer.Services.Map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Repository.IParkingRepository;

@Service
public class MapService {
    
    @Autowired
    IParkingRepository parkingRepository;

    public MapService(){}

    public List<Parking> getParkingsPerCity(String city){
        return parkingRepository.findAll();
    }
}
