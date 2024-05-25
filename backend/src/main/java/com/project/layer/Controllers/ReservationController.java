package com.project.layer.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.layer.Services.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Responses.ReservationResponse;
import com.project.layer.Persistence.Entity.ResStatus;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Audit.AuditService;
import com.project.layer.Services.IpRequest.RequestService;
import com.project.layer.Services.Mail.MailService;
import com.project.layer.Services.Reservation.ReservationService;
import com.project.layer.Services.ScoreSystem.ScoreSystemService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;
    @Autowired
    private final MailService mailService;
    @Autowired
    private final ScoreSystemService scoreSystemService;
    @Autowired
    private final AuditService auditService;
    private final RequestService requestService;

    @Autowired
    private final PaymentService paymentService;
    private String token;

    @GetMapping("/client/{idDocType}/{idUser}")
    public List<Reservation> getReservationsByClient(
            @PathVariable(value = "idDocType") String idDocType,
            @PathVariable(value = "idUser") String idUser,
            @RequestParam(value = "status", required = false) String status) {
        return reservationService
                .getReservationsByClientId(UserId.builder().idDocType(idDocType).idUser(idUser).build(), status);
    }

    @PostMapping("/start")
    public ResponseEntity<ReservationResponse> start(@RequestBody StartReservationRequest reservationRequest,
            HttpServletRequest ipUser) throws MessagingException {

        ReservationResponse reservationResponse = reservationService.startReservation(reservationRequest);

        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // se envia el correo electronico de confirmacion con los detalles de la resreva
        mailService.sendMail(
                reservationResponse.getReservation().getClient().getEmail(),
                "[Four-parks] Información de su reserva",
                getReservationMailParameters(reservationResponse.getReservation(), "Reserve"));

        if (reservationService.isReservationNearStarting(reservationResponse.getReservation())) {
            makePayment(reservationResponse.getReservation());
        }

        // Es almacenada la accion realizada por el usuario en este caso realizo la
        // reserva
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Realizo reserva",
                requestService.getClientIp(ipUser)));

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    // se ejecuta a la media hora antes de que empiece la reserva
    @Scheduled(cron = "0 50 * * * *")
    public void confirm() {
        List<Reservation> reservations = reservationService.getNearStartingReservations();

        for (Reservation reservation : reservations) {
            makePayment(reservation);
        }

    }

    public void makePayment(Reservation reservation) {
//        revisar con cristian para ver como seria la logica
//
//
//         Validar si se ajusta el costo de la reserva o que pex
//        reservationService.setTotalRes(reservation, scoreSystemService.applyDiscount(
//                reservation.getClient(),
//                reservation.getParkingSpace(),
//                reservation.getTotalRes()));

        // Aqui va la parte del pago
        String userId = String.valueOf(reservation.getClient().getUserId().getIdUser());
        token = paymentService.createCardToken(userId);
        paymentService.charge(token, reservation.getTotalRes());
        System.out.println("usted pago: " + reservation.getTotalRes());


        // Si el pago sale bien, el estado cambia a confirmado
        reservationService.setStatus(reservation, ResStatus.CONFIRMED.getId());
        // Es almacenada la accion realizada por el usuario
        auditService.setAction(reservationService.getUserAction(reservation.getClient().getUserId().getIdUser(),
                reservation.getClient().getUserId().getIdDocType(),
                "Pago auomatico",
                "8.8.8.8"));

//        scoreSystemService.increaseScore(
//                reservation.getClient(),
//                reservation.getParkingSpace().getParkingSpaceId().getParking(),
//                reservation.getTotalRes());

        // Se debe enviar los correos pertinentes
        // mailService.sendMail(reservation.getClient().getEmail(), "Reserva
        // Confirmada",);
    }

    @PutMapping("{id}/check-in")
    public ResponseEntity<ReservationResponse> checkIn(@PathVariable("id") Integer idReservation,
    HttpServletRequest ipUser) {

        ReservationResponse reservationResponse = reservationService.checkInReservation(idReservation);

        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // Es almacenada la accion realizada por el usuario
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Hizo check-in",
                requestService.getClientIp(ipUser)));

        return new ResponseEntity<>(reservationResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(@PathVariable("id") Integer idReservation,
            HttpServletRequest ipUser) throws MessagingException {
        ReservationResponse reservationResponse = reservationService.cancelReservation(idReservation);

        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // Se debe hacer el cobro, la variable totalRes se seteo para que costara lo de
        // la cancelación
        String userId = reservationResponse.getReservation().getClient().getUserId().getIdUser();
        token = paymentService.createCardToken(userId);
        paymentService.charge(token, reservationResponse.getReservation().getTotalRes());
        System.out.println("usted pago: " + reservationResponse.getReservation().getTotalRes());

        reservationService.setStatus(reservationResponse.getReservation(), ResStatus.CANCELLED.getId());

        // Es almacenada la accion realizada por el usuario
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Cancelo la reserva",
                requestService.getClientIp(ipUser)));

        // Se envian los correos
        mailService.sendMail(
                reservationResponse.getReservation().getClient().getEmail(),
                "[Four-parks] Información de su reserva",
                getReservationMailParameters(reservationResponse.getReservation(), "Reserve_Cancellation"));

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    @PutMapping("{id}/check-out")
    public ResponseEntity<ReservationResponse> checkOut(@PathVariable("id") Integer idReservation,
            HttpServletRequest ipUser) {

        ReservationResponse reservationResponse = reservationService.checkOutReservation(idReservation);

        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        float extraCost = reservationService.getReservationsExtraCost(reservationResponse.getReservation());
        if (extraCost != 0) {
            // Aqui se hace el pago utilizando reservationResponse.getExtraCost()
        }

        reservationService.setStatus(reservationResponse.getReservation(), ResStatus.COMPLETED.getId());

        // Es almacenada la accion realizada por el usuario
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Hizo check-out",
                requestService.getClientIp(ipUser)));

        // Se envian los correos

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    @Scheduled(cron = "0 59 * * * *")
    public void autoCheckOut() {
        List<Reservation> reservations = reservationService.automaticCheckOut();

        for (Reservation reservation : reservations) {
            float extraCost = reservationService.getReservationsExtraCost(reservation);
            if (extraCost != 0) {
                // Aqui se hace el pago utilizando el extra cost

                // Es almacenada la accion realizada por el usuario revisar con cristian depronto no es necesario
                auditService.setAction(reservationService.getUserAction(
                        reservation.getClient().getUserId().getIdUser(),
                        reservation.getClient().getUserId().getIdDocType(),
                        "check-out automatico",
                        "8.8.8.8"));
            }
        }

        reservationService.setStatus(null, ResStatus.COMPLETED.getId());
    }

    private List<String> getReservationMailParameters(Reservation reservation, String template) {
        List<String> list = new ArrayList<>();

        // Plantilla para enviar los detalles de la reserva
        if (template.equals("Reserve")) {
            list = Arrays.asList(template,
                    "ID: " + Integer.toString(reservation.getIdReservation()), // Id de la reservación
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getNamePark(), // Nombre del
                                                                                                  // parqueadero
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getAddress().getDescAddress(), // Dirección
                    reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(), // Nombre del
                                                                                                          // cliente
                    reservation.getStartDateRes().toString(), // Fecha de la reserva
                    reservation.getStartTimeRes().toString(), // Tiempo de inicio de la reserva
                    reservation.getEndTimeRes().toString(), // Tiempo de terminación de la reserva
                    Integer.toString(reservation.getParkingSpace().getParkingSpaceId().getIdParkingSpace()), // Id del
                                                                                                             // espacio
                                                                                                             // de
                                                                                                             // parqueadero
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getCity().getName(), // Ciudad
                                                                                                                       // de
                                                                                                                       // la
                                                                                                                       // reserva
                    reservation.getParkingSpace().getVehicleType().getDescVehicleType(), // Tipo de vehiculo
                    reservation.getLicensePlate()// Placa del automovil
            );
        }

        // Plantilla para confirmar la cancelacion de la reserva
        if (template.equals("Reserve_Cancellation")) {
            list = Arrays.asList(template,
                    "ID: " + Integer.toString(reservation.getIdReservation()), // Id de la reservación
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getNamePark(), // Nombre del
                                                                                                  // parqueadero
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getAddress().getDescAddress(), // Dirección
                    reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(), // Nombre del
                                                                                                          // cliente
                    reservation.getStartDateRes().toString(), // Fecha de la reserva
                    Integer.toString(reservation.getParkingSpace().getParkingSpaceId().getIdParkingSpace()), // Id del
                                                                                                             // espacio
                                                                                                             // de
                                                                                                             // parqueadero
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getCity().getName()// Ciuad
                                                                                                                     // de
                                                                                                                     // la
                                                                                                                     // reserva
            // falta poner la parte del monto de la tarifa por cancelacion
            );
        }

        return list;
    }

}
