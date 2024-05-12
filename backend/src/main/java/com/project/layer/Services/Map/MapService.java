package com.project.layer.Services.Map;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
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
   

    public List<Parking> getParkingsFilter(String city, String type, Date date, Time startTime, Time endTime, String scheduleType, String vehicleType){

        if(city == null){
            return null;
        }
        
        date = (date == null) ? Date.valueOf(LocalDate.now()) : date;
        
        List<Parking> parkings = null;
        Time endTemp = null;    
        if(endTime != null && endTime.toString().equals(Time.valueOf("00:00:00").toString()) ){    
            endTemp = Time.valueOf("23:59:59");            
        } else{            
            endTemp = endTime;
        }        
        
        parkings = parkingRepository.queryParkingsByArgs(city, type, startTime, endTemp, scheduleType);
        startTime = (startTime == null) ? Time.valueOf(LocalTime.now()) : startTime;
        
        System.out.println("----------------------- La fecha es: "+ date + " " + startTime + " " +endTime);
        
        for (Parking parking : parkings) {
            endTime = (endTime == null) ? parking.getEndTime() : endTime;
            
            System.out.println("El parqueadero: " + parking.getIdParking());
            
            System.out.println("El tipo de vehiculo: "+vehicleType);
            float busySpaces = reservationRepository.findCountOfBusyParkingSpaces(
                parking.getCity().getIdCity(),
                parking.getIdParking(),
                vehicleType,
                date,
                startTime,
                endTime
            );
            
            System.out.println("Ocupabilidad en el parqueadero " + parking.getIdParking() +" Y vehiculo " +vehicleType+": "+ busySpaces);
            
            parking.setCapacity(parkingspaceRepository.countByParkingAndVehicleType(parking.getIdParking(), parking.getCity().getIdCity(), vehicleType));
            
            float totalSpaces = parking.getCapacity();

            System.out.println("El total de parqueaderos para "+vehicleType+" es: " + totalSpaces);          
            
            if(totalSpaces != 0){
                float percentSpaces = busySpaces/totalSpaces;
                parking.setOcupability(percentSpaces);
            }
            System.out.println("Por lo que el porcentaje de ocupacion es: " + parking.getOcupability());
        }

        return parkings;
    }

    public ParkingResponse getParkingsPerCoordinates(float coordinateX, float coordinateY) {


        Parking parking = parkingRepository.queryParkingByCoordinates(coordinateX, coordinateY);

        if(parking == null){
            return null;
        }

        List<String> vehicleListType = parkingRepository.getTypeVehicleByParking(parking.getIdParking());

        System.out.println(vehicleListType.toString());
        Map<String, Object> tipoVehiculo = new HashMap<>();

        for (String vehicle : vehicleListType) {
            
            Map<String, Integer> vehicleType = new HashMap<>();
    
            if(parking.getParkingType().getIdParkingType().equals("COV") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                vehicleType.put("covered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                    parking.getIdParking(), false, vehicle));
                vehicleType.put("rate-covered", rateRepository.getHourCostByParkingSpace(
                    parking.getIdParking(), parking.getCity().getIdCity(), vehicle, false
                ));
            }
            if(parking.getParkingType().getIdParkingType().equals("UNC") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                vehicleType.put("uncovered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                    parking.getIdParking(), true, vehicle
                ));
                vehicleType.put("rate-uncovered", rateRepository.getHourCostByParkingSpace(
                    parking.getIdParking(), parking.getCity().getIdCity(), vehicle, true
                ));
            }

            tipoVehiculo.put(vehicle, vehicleType);

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
