package com.project.layer.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.layer.Services.Payment.*;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Responses.ReservationResponse;
import com.project.layer.Persistence.Entity.ResStatus;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Authentication.AuthService;
import com.project.layer.Services.JWT.JwtService;
import com.project.layer.Services.Audit.AuditService;
import com.project.layer.Services.IpRequest.RequestService;
import com.project.layer.Services.Mail.MailService;
import com.project.layer.Services.Parking.ParkingService;
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
    private final JwtService jwtService;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ParkingService parkingService;
    @Autowired
    private final AuditService auditService;
    @Autowired
    private final RequestService requestService;
    @Autowired
    private final PaymentService paymentService;
    String token;

    @Autowired
    private final DebtHandlerChain debtHandlerChain;

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

        String token = jwtService.getTokenFromRequest(
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        User client = authService.getUser(jwtService.getUserIdFromToken(token));

        ReservationResponse reservationResponse = reservationService.startReservation(client, reservationRequest);

        Reservation reservation = reservationResponse.getReservation();

        if (reservation == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // se envia el correo electronico de confirmacion con los detalles de la resreva
        mailService.sendMail(
                client.getEmail(),
                "[Four-parks] Información de su reserva",
                getReservationMailParameters(reservationResponse.getReservation(), "Reserve"));
        
       //Metodo para confirmar la reserva cuando ya se ha pasado los 30 min 
        if (reservationService.isReservationNearStarting(reservation)) {
            makePayment(reservation);
        }

        // Es almacenada la accion realizada por el usuario en este caso realizo la reserva
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Realizo reserva",
                requestService.getClientIp(ipUser)));

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    // se ejecuta a la media hora antes de que empiece la reserva (cerca a la media hora)
    @Scheduled(cron = "0 30 * * * *")
    public void confirm() throws MessagingException {
        List<Reservation> reservations = reservationService.getNearStartingReservations();

        for (Reservation reservation : reservations) {
            makePayment(reservation);
        }
    }

    //Metodo para realizarel un pago
    public void makePayment(Reservation reservation) throws MessagingException { // revisar con cristian para ver como seria la logica

        if (scoreSystemService.isEnabled(reservation.getParkingSpace().getParkingSpaceId().getParking())) {
            if (scoreSystemService.isAfiliated(reservation.getClient(),
                    reservation.getParkingSpace().getParkingSpaceId().getParking())) {
                reservationService.setTotalRes(reservation, scoreSystemService.applyDiscount(
                        reservation.getClient(),
                        reservation.getParkingSpace().getParkingSpaceId().getParking(),
                        parkingService.getRateByParkingSpace(reservation.getParkingSpace()),
                        reservation.getTotalRes()));
            } else {
                System.out.println("Si necesita que se afilie:");
                scoreSystemService.insertClient(reservation.getClient(),
                        reservation.getParkingSpace().getParkingSpaceId().getParking());
            }
        }

        // Aqui va la parte del pago

        String userId = String.valueOf(reservation.getClient().getUserId().getIdUser());
        Pay pay = new Pay(TypePay.CURRENT);
        pay.setUser(reservation.getClient());

        // Llama al manejador de la cadena de responsabilidad
        debtHandlerChain.debt(pay);

        float totalPayment = Float.parseFloat(pay.getTotalPayment());
        System.out.println("%%%%%%%%%%%%%%%%%%El usuario debe: " + totalPayment);
        token = paymentService.createCardToken(userId);
        paymentService.charge(token, totalPayment);
        System.out.println("usted pago: " + totalPayment);

        System.out.println("Se ha limpiado la deuda ###################################");

        // Si el pago sale bien, el estado cambia a confirmado
        Pay pa = new Pay(TypePay.PAYOFF);
        pa.setUser(reservation.getClient());

        // Llama al manejador de la cadena de responsabilidad
        debtHandlerChain.debt(pa);
        reservationService.setStatus(reservation, ResStatus.CONFIRMED.getId());

        //De tener un sistema de puntuaciòn el  programa agegara puntos al cliente

        if (scoreSystemService.isEnabled(reservation.getParkingSpace().getParkingSpaceId().getParking())) {
            scoreSystemService.increaseScore(
                    reservation.getClient(),
                    reservation.getParkingSpace().getParkingSpaceId().getParking(),
                    reservation.getTotalRes());
        }

        // Se debe enviar los correos pertinentes
        mailService.sendMail(reservation.getClient().getEmail(),
                // reservationResponse.getReservation().getClient().getEmail(),
                "[Four-parks] Gracias por tu compra",
                getReservationMailParameters(reservation, "Purchase"));

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

        //
        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // Aqui va la parte del pago
        String userId = reservationResponse.getReservation().getClient().getUserId().getIdUser();
        Pay pay = new Pay(TypePay.CURRENT);
        pay.setUser(reservationResponse.getReservation().getClient());

        // Llama al manejador de la cadena de responsabilidad
        debtHandlerChain.debt(pay);

        float totalPayment = Float.parseFloat(pay.getTotalPayment());
        System.out.println("%%%%%%%%%%%%%%%%%%El usuario debe: " + totalPayment);
        token = paymentService.createCardToken(userId);
        paymentService.charge(token, totalPayment);
        System.out.println("usted pago: " + totalPayment);

        System.out.println("Se ha limpiado la deuda ###################################");

        // Si el pago sale bien, el estado cambia a confirmado
        Pay pa = new Pay(TypePay.PAYOFF);
        pa.setUser(reservationResponse.getReservation().getClient());

        reservationService.setStatus(reservationResponse.getReservation(), ResStatus.CANCELLED.getId());

        // Es almacenada la accion realizada por el usuario
        auditService.setAction(reservationService.getUserAction(
                reservationResponse.getReservation().getClient().getUserId().getIdUser(),
                reservationResponse.getReservation().getClient().getUserId().getIdDocType(),
                "Cancelo la reserva",
                requestService.getClientIp(ipUser)));

        // Se envian los correos

        mailService.sendMail("dmcuestaf@udistrital.edu.co",
                // reservationResponse.getReservation().getClient().getEmail(),
                "[Four-parks] Reserva cancelada",
                getReservationMailParameters(reservationResponse.getReservation(), "Reserve_Cancellation"));

        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    @PutMapping("{id}/check-out")
    public ResponseEntity<ReservationResponse> checkOut(@PathVariable("id") Integer idReservation,
            HttpServletRequest ipUser) {

        ReservationResponse reservationResponse = reservationService.checkOutReservation(idReservation);


        if (reservationResponse.getReservation() == null)
            return new ResponseEntity<>(reservationResponse, HttpStatus.BAD_REQUEST);

        // si la persona se paso por x min, se le agregara en su tabla deuda el valor de
        // lo que se paso
        float extraCost = reservationService.getReservationsExtraCost(reservationResponse.getReservation());
        if (extraCost != 0) {
            // Aqui se hace el pago utilizando reservationResponse.getExtraCost()
            // en este caso el valor se agregara a deuda en la tabla userdebt
            Pay pay = new Pay(TypePay.DEBT);
            pay.setUser(reservationResponse.getReservation().getClient());
            pay.setAmount(String.valueOf(extraCost)); // Establecer el valor de extraCost
            // Llama al manejador de la cadena de responsabilidad
            debtHandlerChain.debt(pay);

            // String userId =
            // reservationResponse.getReservation().getClient().getUserId().getIdUser();
            // token = paymentService.createCardToken(userId);
            // paymentService.charge(token, extraCost);
            // System.out.println("usted pago: " + extraCost);

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
    //se realiza el check-out automatico
    @Scheduled(cron = "0 59 * * * *")
    public void autoCheckOut() {
        List<Reservation> reservations = reservationService.automaticCheckOut();

        for (Reservation reservation : reservations) {
            float extraCost = reservationService.getReservationsExtraCost(reservation);
            if (extraCost != 0) {
                // Aqui se agrega extracost a deuda
                Pay pay = new Pay(TypePay.DEBT);
                pay.setUser((User) reservations); // no probado
                pay.setAmount(String.valueOf(extraCost)); // Establecer el valor de extraCost
                // Llama al manejador de la cadena de responsabilidad
                debtHandlerChain.debt(pay);
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
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getCity().getName(), // Ciuad
                                                                                                                       // de
                                                                                                                       // la
                                                                                                                       // reserva
                    Float.toString(reservation.getTotalRes())// monto de la tarifa por cancelacion
            );
        }
        // Plantilla para confirmar la cancelacion de la reserva
        if (template.equals("Purchase")) {
            list = Arrays.asList(template,
                    "ID: " + Integer.toString(reservation.getIdReservation()), // Id de la reservación
                    reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(), // Nombre del
                                                                                                          // cliente
                    reservation.getStartDateRes().toString(), // Fecha de la reserva
                
                    Float.toString(reservation.getTotalRes())// monto de la tarifa por cancelacion
            );
        }

        return list;
    }

}
