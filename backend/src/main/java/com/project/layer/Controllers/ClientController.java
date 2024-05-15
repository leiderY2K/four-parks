package com.project.layer.Controllers;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

//import com.project.layer.Services.Payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.ReservationRequest;
import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Requests.UserReservationRequest;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.City;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Services.Mail.MailService;
import com.project.layer.Services.Map.MapService;
import com.project.layer.Services.Reservation.ReservationService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    
    private final MapService mapService;
    private final ReservationService reservationService;
 //private final PaymentService paymentService;

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
    
    @PostMapping("/postReservations")
    public List<Reservation> postReservations(@RequestBody UserReservationRequest urRequest){
        return reservationService.getReservationsByClientId(urRequest);
    }

    @PostMapping("/startReservation")
    public String startParkingSpace(@RequestBody StartReservationRequest reservationRequest) throws MessagingException{
        return reservationService.startReservation(reservationRequest);
    }
    
    // No hay end point para confirmar por lo que es progrado, front no tiene nada que ver aqui

    @PostMapping("/checkIn")
    public String checkInReservation(@RequestBody ReservationRequest idReservation){
        return reservationService.checkInReservation(idReservation.getIdReservation());
    }

    @PostMapping("/cancelReservation")
    public String cancelReservation(@RequestBody ReservationRequest idReservation){
        return reservationService.cancelReservation(idReservation.getIdReservation());
    }

    @GetMapping("/checkOut")
    public String endReservation(@RequestParam ReservationRequest idReservation){
        return reservationService.checkOutReservation(idReservation.getIdReservation());
    }
    
}
