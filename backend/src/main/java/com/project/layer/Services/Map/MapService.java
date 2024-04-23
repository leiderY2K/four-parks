package com.project.layer.Services.Map;

import java.util.Collections;
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
        List<Parking> parkings = parkingRepository.queryParkingsByCity(city); 

        if(parkings.isEmpty()){
            return Collections.emptyList();
        }
        
        return parkings;
    }

    public Parking getParkingsPerCity(String coordinateX, String coordinateY) {
        Parking parking = (Parking) parkingRepository.queryParkingByCoordinates(coordinateX, coordinateY);

        if(parking == null){
            return null;
        }

        return parking;
    }
}
