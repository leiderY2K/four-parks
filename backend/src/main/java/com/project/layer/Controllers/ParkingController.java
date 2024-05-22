package com.project.layer.Controllers;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingId;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Parking.ParkingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/city/{city}")
    public List<Parking> getParkingbyCity(
        @PathVariable("city") String city,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Date startDate,
        @RequestParam(required = false) Time startTime,
        @RequestParam(required = false) Time endTime,
        @RequestParam(required = false) String scheduleType,
        @RequestParam(required = false) String vehicleType
    ) {
        return parkingService.getParkingsFilter(city, type, startDate, startTime, endTime, scheduleType, vehicleType);
    }

    @GetMapping("/admin/{idDoctype}/{idUser}")
    public ParkingResponse getParkingByAdmin(
        @PathVariable("idDoctype") String idDoctype,
        @PathVariable("idUser") String idUser
    ){
        return parkingService.searchParking(UserId.builder().idDocType(idDoctype).idUser(idUser).build());
    }

    @GetMapping("/coordinates/{coordinateX}/{coordinateY}")
    public ParkingResponse getParkingByCoordinates(
        @PathVariable("coordinateX") float coordinateX,
        @PathVariable("coordinateY") float coordinateY
    ) {
        return parkingService.getParkingsByCoordinates(coordinateX, coordinateY);
    }
    
    @PostMapping("{idParking}/{idCity}/parking-space/insert") 
    public ResponseEntity<String> insertParkingSpace(
        @PathVariable("idParking") Integer idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody ParkingSpaceRequest pkRequest
    ) {
        return new ResponseEntity<>(
            parkingService.insertParkingSpace(ParkingId.builder().idParking(idParking).city(City.builder().idCity(idCity).build()).build(), pkRequest),
            HttpStatus.OK
        );
    }

    @PutMapping("{idParking}/{idCity}")
    public ResponseEntity<String> update(
        @PathVariable("idParking") Integer idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody @Valid Parking parkingRequest
    ) {
        return new ResponseEntity<>(
            parkingService.modifyParking(ParkingId.builder().idParking(idParking).city(City.builder().idCity(idCity).build()).build(), parkingRequest),
            HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("{idParking}/{idCity}/parking-space/delete") 
    public ResponseEntity<String> deleteParkingSpace(
        @PathVariable("idParking") Integer idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody ParkingSpaceRequest pkRequest
    ) {
        return new ResponseEntity<>(
            parkingService.deleteParkingSpace(ParkingId.builder().idParking(idParking).city(City.builder().idCity(idCity).build()).build(), pkRequest),
            HttpStatus.OK
        );
    }
}
