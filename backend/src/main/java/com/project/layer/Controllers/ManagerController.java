package com.project.layer.Controllers;
import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Controllers.Requests.searchParkingRequest;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Services.Parameterization.ParameterizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ParameterizationService parameterizationService;

    @PostMapping("/searchParking")
    public ResponseEntity<Optional<Parking>> searchParking(@RequestBody searchParkingRequest spRequest ){
        Optional<Parking> parking = parameterizationService.searchParking(spRequest);
        return ResponseEntity.ok(parking);
    }
    
}
