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

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Requests.UserReservationRequest;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.Reservation;
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
        @RequestParam(required = false) Date date,
        @RequestParam(required = false) Time startTime,
        @RequestParam(required = false) Time endTime,
        @RequestParam(required = false) String scheduleType,
        @RequestParam(required = false) String vehicleType
    ) {
        return mapService.getParkingsFilter(city, type, date, startTime, endTime, scheduleType, vehicleType);
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
    public List<Reservation> postReservations(@RequestBody UserReservationRequest urRequest){
        return reservationService.getReservationsByClientId(urRequest);
    }

    @GetMapping("/endReservation")
    public String endReservation(@RequestParam int idReservation){

        return reservationService.checkOutReservation(idReservation);
    }
    
}
