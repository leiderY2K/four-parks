package com.project.layer.Services.Map;

import org.springframework.stereotype.Service;

@Service
public class MapService {
    public MapService(){}

    public String getParkingsPerCity(String city){
        if(city.equals("Bogota")){
            return "Bogota";
        }else{
            return "Not Bogota";
        }
    }
}
