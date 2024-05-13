package com.project.layer.Services.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.project.layer.Services.Payment.PaymentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Requests.UserReservationRequest;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ResStatus;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IRateRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
    private final IUserRepository userRepository;
    private final IRateRepository rateRepository;

    private final PaymentService paymentService;
    private String token;

    public List<Reservation> getReservationsByClientId(UserReservationRequest urRequest) {
        return reservationRepository.findAllByClientId(urRequest.getClientId().getIdUser(),
                urRequest.getClientId().getIdDocType(), urRequest.getStatus());
    }

    @Transactional
    public String startReservation(StartReservationRequest reservationRequest) {

        Date sqlDate = Date.valueOf(LocalDate.now());

        System.out.println("La fecha: " + reservationRequest.getDateRes());

        Time startTime = Time.valueOf(reservationRequest.getStartTimeRes().toLocalTime().minusMinutes(59));
        Time entTime = Time.valueOf(reservationRequest.getEndTimeRes().toLocalTime().plusMinutes(59));
        List<Integer> busyParkingSpaces = reservationRepository.findBusyParkingSpaces(
                reservationRequest.getCityId(),
                reservationRequest.getParkingId(),
                reservationRequest.getVehicleType(),
                reservationRequest.getDateRes(),
                startTime,
                entTime);

        System.out
                .println("Numero de parqueaderos ocupados en ese intervalo de tiempo: " + busyParkingSpaces.toString());
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAllByParking(reservationRequest.getParkingId(),
                reservationRequest.getCityId());

        ParkingSpace selectedParkingSpace = null;
        boolean wasFoundAnyParkingSpace = false;
        System.out.println("Espacio libre!");

        for (ParkingSpace parkingSpace : parkingSpaces) {
            if (!busyParkingSpaces.contains(parkingSpace.getParkingSpaceId().getIdParkingSpace()) &&
                    parkingSpace.getIdVehicleType().equals(reservationRequest.getVehicleType())) {
                selectedParkingSpace = parkingSpace;
                System.out.println(selectedParkingSpace.toString());
                wasFoundAnyParkingSpace = true;
                break;
            }
        }

        if (!wasFoundAnyParkingSpace) {
            return "No hay espacios disponibles";
        }

        User client = userRepository.getReferenceById(reservationRequest.getClientId());

        Reservation reservation = Reservation.builder()
                .dateRes(reservationRequest.getDateRes())
                .startTimeRes(reservationRequest.getStartTimeRes())
                .endTimeRes(reservationRequest.getEndTimeRes())
                .creationDateRes(sqlDate)
                .totalRes(0)
                .licensePlate(reservationRequest.getLicensePlate())
                .client(client)
                .vehicleType(reservationRequest.getVehicleType())
                .parkingSpace(selectedParkingSpace)
                .status(ResStatus.PENDING.getId())
                .build();



        String userId = reservationRequest.getClientId().getIdUser();

        //float totalCost = 8653;

        reservationRepository.save(reservation);
        token = paymentService.createCardToken(userId);
        //paymentService.charge(token, totalCost);
        //confirmReservation();

        return "¡La reserva se realizo exitosamente!"+" su token es: " + token;
    }

    @Transactional
    @Scheduled(cron = "0 30 * * * *")
    public void confirmReservation() {

        Time hour = Time.valueOf(LocalTime.now());
        String sHour = hour.toString();
        int intHour = 0;
        if (sHour.substring(0, 2).equals("23")) {
            intHour = 0;
        } else if (sHour.charAt(0) == '0') {
            intHour = Integer.parseInt(sHour.substring(1, 2)) + 1;
        } else {
            intHour = Integer.parseInt(sHour.substring(0, 2)) + 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, intHour); 
        calendar.set(Calendar.MINUTE, 0); 
        calendar.set(Calendar.SECOND, 0); 
        calendar.set(Calendar.MILLISECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();
        hour = new Time(timeInMillis);
        Date sqlDate = Date.valueOf(LocalDate.now());
        List<Reservation> reservations = reservationRepository.findByStartTime(hour, sqlDate, ResStatus.PENDING.getId());

        for (Reservation reservation : reservations) {
            long totalSeconds;

            LocalTime startTime = reservation.getStartTimeRes().toLocalTime();
            LocalTime endTime = reservation.getEndTimeRes().toLocalTime();

            totalSeconds = Duration.between(startTime, endTime).getSeconds();
            System.out.println("Aun no se ha acabado la reserva");

            // Se hace de esta manera ya que en la BD el cobro se hace por horas
            float totalHours = totalSeconds / 3600.0f;

            int rate = rateRepository.getHourCostByParkingSpace(
                    reservation.getParkingSpace().getParkingSpaceId().getIdParking(),
                    reservation.getParkingSpace().getParkingSpaceId().getIdCity(),
                    reservation.getVehicleType(),
                    reservation.getParkingSpace().isUncovered());

            float totalCost = totalHours * rate;

            // Se debe realizar el pago
            paymentService.charge(token, totalCost);

            // -------------------------------------------------------------------------------------------------------------

            // Se debe enviar email de correo
            // -------------------------------------------------------------------------------------------------------

            reservation.setStatus(ResStatus.CONFIRMED.getId());
            reservation.setTotalRes(totalCost);

            reservationRepository.save(reservation);
        }
        
    }

    @Transactional
    public String checkInReservation(int idReservation) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return "La reserva no fue encontrada";
        }

        Reservation reservation = optionalReservation.get();
        reservation.setStatus(ResStatus.IN_PROGRESS.getId());

        reservationRepository.save(reservation);

        return "¡Su reserva fue cancelada!";
    }

    @Transactional
    public String cancelReservation(int idReservation) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return "La reserva no fue encontrada";
        }

        Reservation reservation = optionalReservation.get();

        if (!(reservation.getStatus().equals(ResStatus.PENDING.getId())
                || reservation.getStatus().equals(ResStatus.CONFIRMED.getId()))) {
            return "¡La reserva no puede ser cancelada!";
        }

        int cancellationCost = rateRepository.getCancellationCostByParkingSpace(
                reservation.getParkingSpace().getParkingSpaceId().getIdParking(),
                reservation.getParkingSpace().getParkingSpaceId().getIdCity(),
                reservation.getVehicleType(),
                reservation.getParkingSpace().isUncovered());

        System.out.println("Costo de tarifa: " + cancellationCost);

        // Se debe realizar el cargo por cancelación
        paymentService.charge(token, cancellationCost);
        // -------------------------------------------------------------------------------------------------------------

        reservation.setStatus(ResStatus.CANCELLED.getId());

        reservationRepository.save(reservation);

        return "¡Su reserva fue cancelada!";
    }

    @Transactional
    public String checkOutReservation(int idReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return "No se encontró ninguna reserva con el ID proporcionado.";
        }

        Reservation reservation = optionalReservation.get();
        System.out.println("La reserva es: " + reservation.toString());

        long extraSeconds;

        LocalTime endTime = reservation.getEndTimeRes().toLocalTime();

        if (Time.valueOf(LocalTime.now()).after(reservation.getEndTimeRes())) {
            System.out.println("Se hace cobro por pasarse de tiempo");

            extraSeconds = Duration.between(endTime, LocalTime.now()).getSeconds();

            // Se hace de esta manera ya que en la BD el cobro se hace por horas
            float extraHours = extraSeconds / 3600.0f;

            int rate = rateRepository.getHourCostByParkingSpace(
                    reservation.getParkingSpace().getParkingSpaceId().getIdParking(),
                    reservation.getParkingSpace().getParkingSpaceId().getIdCity(),
                    reservation.getVehicleType(),
                    reservation.getParkingSpace().isUncovered());

            float extraCost = extraHours * rate;

            // Realizar pago automatico por minutos extra
            paymentService.charge(token, extraCost);


            System.out.println("--------------------------- Horas transcurridas: " + extraHours);
            reservation.setTotalRes(reservation.getTotalRes() + extraCost);
        }

        // Actualizar campos
        reservation.setStatus(ResStatus.COMPLETED.getId());

        // Guardar cambios
        reservationRepository.save(reservation);

        return "¡Su salida ha sido exitosa!";

    }

    @Transactional
    @Scheduled(cron = "0 59 * * * *")
    public void automaticCheckOut() {

        Time hour = Time.valueOf(LocalTime.now());
        String sHour = hour.toString();
        int intHour = 0;
        if (sHour.substring(0, 2).equals("23")) {
            intHour = 0;
        } else if (sHour.charAt(0) == '0') {
            intHour = Integer.parseInt(sHour.substring(1, 2)) + 1;
        } else {
            intHour = Integer.parseInt(sHour.substring(0, 2)) + 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, intHour); 
        calendar.set(Calendar.MINUTE, 0); 
        calendar.set(Calendar.SECOND, 0); 
        calendar.set(Calendar.MILLISECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();
        hour = new Time(timeInMillis);
        Date sqlDate = Date.valueOf(LocalDate.now());
        List<Reservation> reservations = reservationRepository.findByEndTime(hour, sqlDate, ResStatus.CONFIRMED.getId());

        for (Reservation reservation : reservations) {
            long totalSeconds;

            LocalTime endTime = reservation.getEndTimeRes().toLocalTime();

            totalSeconds = Duration.between(endTime, LocalTime.now()).getSeconds();
            System.out.println("Aun no se ha acabado la reserva");

            // Se hace de esta manera ya que en la BD el cobro se hace por horas
            float totalMinutes = totalSeconds / 60.0f;

            int rate = rateRepository.getHourCostByParkingSpace(
                    reservation.getParkingSpace().getParkingSpaceId().getIdParking(),
                    reservation.getParkingSpace().getParkingSpaceId().getIdCity(),
                    reservation.getVehicleType(),
                    reservation.getParkingSpace().isUncovered());

            float totalCost = totalMinutes * (rate/60);

            // Se debe realizar el pago
            // -------------------------------------------------------------------------------------------------------------

            // Se debe enviar email de correo
            // -------------------------------------------------------------------------------------------------------

            reservation.setStatus(ResStatus.COMPLETED.getId());
            reservation.setTotalRes(reservation.getTotalRes()+totalCost);

            reservationRepository.save(reservation);
        }

    }

}
