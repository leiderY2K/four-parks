package com.project.layer.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Persistence.Entity.City;
import com.project.layer.Services.Map.MapService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/city")
@Controller
@RequiredArgsConstructor
public class CityController {
    
    private final MapService mapService;

    @GetMapping("/list")
    public List<String> getCityList(){
        return mapService.getCityList();
    }

    @GetMapping("{city}")
    public City getCity(@PathVariable("city") String city){
        return mapService.getCity(city);
    }
    
}
