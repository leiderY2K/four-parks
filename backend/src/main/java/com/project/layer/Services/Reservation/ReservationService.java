package com.project.layer.Services.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Requests.UserReservationRequest;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ResStatus;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IRateRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.Mail.MailService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
    private final IParkingRepository parkingRepository;
    private final IUserRepository userRepository;
    private final IRateRepository rateRepository;
    private final MailService mailService;

    public List<Reservation> getReservationsByClientId(UserReservationRequest urRequest) {
        return reservationRepository.findAllByClientId(urRequest.getClientId().getIdUser(),
                urRequest.getClientId().getIdDocType(), urRequest.getStatus());
    }

    @Transactional
    public String startReservation(StartReservationRequest reservationRequest) throws MessagingException {

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
        Optional<Parking> parking = parkingRepository.findById(reservation.getParkingSpace().getParkingSpaceId().getIdParking());
        List<String> reserva = Arrays.asList("email",
                Integer.toString(reservation.getIdReservation()),
                reservation.getDateRes().toString(),
                reservation.getStartTimeRes().toString(),
                reservation.getEndTimeRes().toString(),
                reservation.getLicensePlate(),
                client.getFirstName() + " " + client.getLastName(),
                reservationRequest.getClientId().getIdUser(),
                reservationRequest.getClientId().getIdDocType(),
                reservationRequest.getCityId(),
                parking.get().getNamePark(),
                reservationRequest.getVehicleType());

        reservationRepository.save(reservation);
        mailService.sendMail("dmcuestaf@udistrital.edu.co", "[Four-parks] Informaciòn de su reserva", reserva);
        return "¡La reserva se realizo exitosamente!";
    }

    @Transactional
    public String confirmReservation() {

        Time actualTime = Time.valueOf(LocalTime.now().minusMinutes(5));
        Time postActualTime = Time.valueOf(LocalTime.now().plusMinutes(40));
        ;

        List<Reservation> reservations = reservationRepository.findByStartTime(actualTime, postActualTime);

        if (reservations.isEmpty()) {
            return "No se encontró ninguna reserva con el ID proporcionado.";
        }

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
                    reservation.getParkingSpace().isUncovered());2

            float totalCost = totalHours * rate;

            // Se debe realizar el pago
            // -------------------------------------------------------------------------------------------------------------

            // Se debe enviar email de correo
            // -------------------------------------------------------------------------------------------------------
            reservation.setStatus(ResStatus.CONFIRMED.getId());
            reservation.setTotalRes(totalCost);

            reservationRepository.save(reservation);
        }

        return "Se realizo el pago";
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

        return "¡Su reserva ha comenzado!";
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

            System.out.println("--------------------------- Horas transcurridas: " + extraHours);
            reservation.setTotalRes(reservation.getTotalRes() + extraCost);
        }

        // Actualizar campos
        reservation.setStatus(ResStatus.COMPLETED.getId());

        // Guardar cambios
        reservationRepository.save(reservation);

        return "¡Su salida ha sido exitosa!";

    }

}
