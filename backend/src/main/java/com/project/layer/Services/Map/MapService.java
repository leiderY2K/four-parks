package com.project.layer.Services.Map;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Repository.ICityRepository;
import com.project.layer.Persistence.Repository.IParkingRepository;

@Service
public class MapService {
    
    @Autowired
    IParkingRepository parkingRepository;

    @Autowired
    ICityRepository cityRepository;


    public MapService(){}

    public List<Parking> getParkingsFilter(String city, String type , Time startTime, Time endTime, String scheduleType){

        List<Parking> parkings = null;
        Time endTemp = null;    
        if(endTime != null && endTime.toString().equals(Time.valueOf("00:00:00").toString()) ){    
            endTemp = Time.valueOf("23:59:59");            
        } else{            
            endTemp = endTime;
        }        

        if(city==null){
            return null;
        }else{
            parkings = parkingRepository.queryParkingsByArgs(city, type, startTime, endTemp, scheduleType);
        }                
        return parkings;
    }

    public ParkingResponse getParkingsPerCoordinates(float coordinateX, float coordinateY) {


        Parking parking = parkingRepository.queryParkingByCoordinates(coordinateX, coordinateY);

        if(parking == null){
            return null;
        }

        List<String> vehiculos = parkingRepository.getTypeVehicleByParking(parking.getIdParking());

        System.out.println(vehiculos.toString());
        Map<String, Object> tipoVehiculo = new HashMap<>();

        for (String vehiculo : vehiculos) {
            
            Map<String, Integer> capacity = new HashMap<>();
    
            System.out.println(parking.toString());
            if(parking.getParkingType().getIdParkingType().equals("COV") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                capacity.put("covered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                    parking.getIdParking(), true, vehiculo));
            }
            if(parking.getParkingType().getIdParkingType().equals("UNC") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                capacity.put("uncovered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                    parking.getIdParking(), false, vehiculo));
            }

            tipoVehiculo.put(vehiculo, capacity);

        }


        return ParkingResponse.builder()
                .parking(parking)
                .capacity(tipoVehiculo)
                .build();
    }

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
