package com.project.layer.Controllers;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Services.Map.MapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    
    private final MapService mapService;


    @GetMapping("/cityList")
    public List<String> getCityList(){
        return mapService.getCityList();
    }

    @GetMapping("/getCity")
    public City getCity(@RequestParam String city){
        return mapService.getCity(city);
    }

    @GetMapping("/getParkings")
    public List<Parking> getParkingsInMap(
        @RequestParam(required = true) String city,
        @RequestParam(required = false) String type
    ) {
        return mapService.getParkingsFilter(city, type);
    }

    @GetMapping("/getParkingByCoordinates")
    public Parking getParkingByCoordinates(@RequestParam float coordinateX, @RequestParam float coordinateY) {
        return mapService.getParkingsPerCoordinates(coordinateX, coordinateY);
    }
    

    
}
