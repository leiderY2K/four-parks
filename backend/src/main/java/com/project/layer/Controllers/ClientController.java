package com.project.layer.Controllers;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.EndReservationRequest;
import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Map.MapService;
import com.project.layer.Services.Reservation.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    
    private final MapService mapService;
    private final ReservationService reservationService;

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
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Date dateRest,
        @RequestParam(required = false) Time startTime,
        @RequestParam(required = false) Time endTime,
        @RequestParam(required = false) String scheduleType
    ) {
        return mapService.getParkingsFilter(city, type, startTime, endTime, scheduleType);
    }

    @GetMapping("/getParkingByCoordinates")
    public ParkingResponse getParkingByCoordinates(@RequestParam float coordinateX, @RequestParam float coordinateY) {
        return mapService.getParkingsPerCoordinates(coordinateX, coordinateY);
    }

    @PostMapping("/startReservation")
    public String startParkingSpace(@RequestBody StartReservationRequest reservationRequest){

        return reservationService.startReservation(reservationRequest);
    }

    @PostMapping("/postReservations")
    public List<Reservation> postReservations(@RequestBody UserId clientId){
        System.out.println(clientId.toString());
        return reservationService.getReservationsByClientId(clientId);
    }

    @PostMapping("/endReservation")
    public String endParkingSpace(@RequestBody EndReservationRequest reservationRequest){

        return reservationService.endReservation(reservationRequest);
    }
    
}
