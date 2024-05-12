package com.project.layer.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Services.Parameterization.ParameterizationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final ParameterizationService parameterizationService;

    @PostMapping("/modifyParking")
    public ResponseEntity<String> modifyParking(@RequestBody Parking parkingRequest){
        return ResponseEntity.ok(parameterizationService.modifyParking(parkingRequest));
    }

    @PostMapping("/insertParkingSpace") 
    public ResponseEntity<String> insertParkingSpace(@RequestBody ParkingSpaceRequest pkRequest ){
        return ResponseEntity.ok(parameterizationService.insertParkingSpace(pkRequest));
    }
    
    @PostMapping("/deleteParkingSpace") 
    public ResponseEntity<String> deleteParkingSpace(@RequestBody ParkingSpaceRequest pkRequest ){
        return ResponseEntity.ok(parameterizationService.deleteParkingSpace(pkRequest));
    }

}
