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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Controllers.Requests.ParkingsRequest;
import com.project.layer.Controllers.Requests.ScoreRequest;
import com.project.layer.Controllers.Responses.ClientScoreResponse;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Controllers.Responses.ParkingsResponse;
import com.project.layer.Controllers.Responses.ScoreResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingId;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Authentication.AuthService;
import com.project.layer.Services.JWT.JwtService;
import com.project.layer.Services.Parking.ParkingService;
import com.project.layer.Services.ScoreSystem.ScoreSystemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final AuthService authService;
    private final JwtService jwtService;
    private final ScoreSystemService scoreSystemService;

    @GetMapping("/city/{city}")
    public List<Parking> getParkingsByCity(
        @PathVariable("city") String city,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Date startDate,
        @RequestParam(required = false) Time startTime,
        @RequestParam(required = false) Date endDate,
        @RequestParam(required = false) Time endTime,
        @RequestParam(required = false) String scheduleType,
        @RequestParam(required = false) String vehicleType
    ) {
        return parkingService.getParkingsByCity(city, type, startDate, startTime, endDate, endTime, scheduleType, vehicleType);
    }

    
    @GetMapping("/coordinates/{coordinateX}/{coordinateY}")
    public ParkingResponse getParkingByCoordinates(
        @PathVariable("coordinateX") float coordinateX,
        @PathVariable("coordinateY") float coordinateY,
        @RequestParam(required = false) Date startDate,
        @RequestParam(required = false) Time startTime,
        @RequestParam(required = false) Date endDate,
        @RequestParam(required = false) Time endTime,
        @RequestParam(required = false) String vehicleType

    ) {
        String token = jwtService.getTokenFromRequest(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest());
        
        User client = authService.getUser(jwtService.getUserIdFromToken(token));
        
        ParkingResponse parkingResponse = parkingService.getParkingsByCoordinates(coordinateX, coordinateY, startDate, startTime, endDate, endTime, vehicleType);
    
        parkingResponse.setScoreResponse(scoreSystemService.getScoreSystem(parkingResponse.getParking()));

        parkingResponse.setClientScore(scoreSystemService.getClientScore(parkingResponse.getParking(), client));

        return parkingResponse;
    }

    @GetMapping("/admin/{idDoctype}/{idUser}")
    public ParkingResponse getParkingByAdmin(
        @PathVariable("idDoctype") String idDoctype,
        @PathVariable("idUser") String idUser
    ){
        ParkingResponse parkingResponse = parkingService.getParkingByAdmin(UserId.builder().idDocType(idDoctype).idUser(idUser).build());
    
        parkingResponse.setScoreResponse(scoreSystemService.getScoreSystem(parkingResponse.getParking()));

        parkingResponse.setClientScore(null);

        return parkingResponse;
    }

    @GetMapping("/spot")
    public ParkingsResponse getParkingsBySpot(
        @RequestBody ParkingsRequest spots,
        @RequestParam("distance") float distance
    ){
        return parkingService.getParkingsBySpot(spots.getCoordinates(), distance);
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

    @PostMapping("{idParking}/{idCity}/score-system/toggle")
    public ResponseEntity<ScoreResponse> toggleScoreSystem(
        @PathVariable("idParking") int idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody ScoreRequest scoreRequest
    ){
        ParkingId parkingId = parkingService.getParkingId(idParking, idCity);
        
        ScoreResponse scoreResponse = scoreSystemService.toggleScore(parkingId, scoreRequest);

        if(scoreResponse.getScoreSystem() == null) return new ResponseEntity<>(scoreResponse,HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>(scoreResponse,HttpStatus.ACCEPTED);
    }


    @PutMapping("{idParking}/{idCity}/score-system/modify")
    public ResponseEntity<ScoreResponse> modifyScoreSystem(
        @PathVariable("idParking") int idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody ScoreRequest scoreRequest
    ){

        ScoreResponse scoreResponse = scoreSystemService.modifyParkingScoreTargets(idParking,idCity,scoreRequest);

        if(scoreResponse.getScoreSystem() == null) return new ResponseEntity<>(scoreResponse,HttpStatus.BAD_REQUEST);   
        
        return new ResponseEntity<>(scoreResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("{idParking}/{idCity}/client-score/create")
    public ResponseEntity<ClientScoreResponse> insertClientScoreSystem(
        @PathVariable("idParking") int idParking,
        @PathVariable("idCity") String idCity,
        @RequestBody ScoreRequest scoreRequest
    ){

        String token = jwtService.getTokenFromRequest(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest());
        
        User client = authService.getUser(jwtService.getUserIdFromToken(token));

        Parking parking = parkingService.getParkingById(parkingService.getParkingId(idParking, idCity)); 

        ClientScoreResponse scoreResponse = scoreSystemService.insertClient(client, parking);

        if(scoreResponse.getScoreSystem() == null) return new ResponseEntity<>(scoreResponse,HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>(scoreResponse,HttpStatus.ACCEPTED);
    }
}
