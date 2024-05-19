package com.project.layer.Services.Map;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Repository.ICityRepository;
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IRateRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapService {
    
    @Autowired
    IParkingRepository parkingRepository;
    @Autowired
    IParkingSpaceRepository parkingspaceRepository;
    @Autowired
    ICityRepository cityRepository;  
    @Autowired
    IRateRepository rateRepository;
    @Autowired
    IReservationRepository reservationRepository;


    public List<String> getCityList() {
        List<String> cityList =cityRepository.queryCityList();
        
        if(cityList == null){
            return null;
        }
        
        return cityList;
        
    }

    public City getCity(String cityName) {
        City city = cityRepository.queryCityByName(cityName);

        if(city == null){
            return null;
        }

        return city;
    }

}
