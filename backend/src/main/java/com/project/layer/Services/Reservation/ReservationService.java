package com.project.layer.Services.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.project.layer.Persistence.Entity.*;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Controllers.Responses.ReservationResponse;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IRateRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
    private final IRateRepository rateRepository;
    private final IUserRepository userRepository;

    public List<Reservation> getReservationsByClientId(UserId userId, String status) {
        return reservationRepository.findAllByClientId(
            userId.getIdUser(),
            userId.getIdDocType(),
            status
        );
    }

    @Transactional
    @Modifying
    public ReservationResponse startReservation(User client, StartReservationRequest reservationRequest) throws MessagingException {
        
        LocalDate actualDate = LocalDate.now();
        LocalTime actualTime = LocalTime.now();

        Time startTime = Time.valueOf(reservationRequest.getStartTimeRes().minusMinutes(59));
        Time endTime = Time.valueOf(reservationRequest.getEndTimeRes().plusMinutes(59));
        
        if(reservationRequest.getStartDateRes().equals(actualDate) && reservationRequest.getStartTimeRes().isBefore(actualTime)){
            return new ReservationResponse(null, "Se esta intentando hacer una reserva antes de la fecha actual");
        }

        List<Reservation> busyParkingSpacesInReservations = reservationRepository.findBusyParkingSpaces(
                reservationRequest.getCityId(),
                reservationRequest.getParkingId(),
                reservationRequest.getVehicleType(),
                reservationRequest.getStartDateRes(),
                startTime,
                reservationRequest.getEndDateRes(),
                endTime
            );

        // Filtrar y contar las reservas activas del cliente específico
        long clientActiveReservations = busyParkingSpacesInReservations.stream()
        .filter(reservation -> 
            reservation.getClient().getUserId().getIdUser().equals(client.getUserId().getIdUser()) &&
            reservation.getClient().getUserId().getIdDocType().equals(client.getUserId().getIdDocType()) &&
                    (reservation.getStatus().equals(ResStatus.PENDING.name()) || reservation.getStatus().equals(ResStatus.CONFIRMED.name())|| reservation.getStatus().equals(ResStatus.IN_PROGRESS.name()))
        )
        .count();

        System.out.println("---------------------------------------- Reservas del cliente: " + clientActiveReservations);

        // Si el cliente ya tiene tres reservas activas, lanzar una excepción o manejar el error adecuadamente
        if (clientActiveReservations >= 3) {
            return new ReservationResponse(null,"El cliente ya tiene tres reservas activas.");
        }

        // Obtener los IDs de los espacios de estacionamiento ocupados
        List<Integer> busyParkingSpacesIds = busyParkingSpacesInReservations.stream()
            .map(reservation -> reservation.getParkingSpace().getParkingSpaceId().getIdParkingSpace())
            .collect(Collectors.toList());

        System.out.println("Lista de espacios ocupados " + busyParkingSpacesIds.toString());
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAllByParking(
                reservationRequest.getParkingId(),
                reservationRequest.getCityId(),
                reservationRequest.getVehicleType(),
                reservationRequest.getIsUncovered()
            );

        // Buscar un espacio de estacionamiento libre y compatible
        Optional<ParkingSpace> selectedParkingSpace = parkingSpaces.stream()
        .filter(parkingSpace -> 
                !busyParkingSpacesIds.contains(parkingSpace.getParkingSpaceId().getIdParkingSpace()) &&
                parkingSpace.getVehicleType().getIdVehicleType().equals(reservationRequest.getVehicleType()))
        .findFirst();

        if (selectedParkingSpace.isPresent()) {
            System.out.println("Espacio libre: " + selectedParkingSpace.get().toString());
        } else {
            return new ReservationResponse(null,"¡No se encontró espacio libre!");
        }

        float rate = rateRepository.getHourCostByParkingSpace(
                    selectedParkingSpace.get().getParkingSpaceId().getParking().getParkingId().getIdParking(),
                    selectedParkingSpace.get().getParkingSpaceId().getParking().getParkingId().getCity().getIdCity(),
                    reservationRequest.getVehicleType(),
                    reservationRequest.getIsUncovered()
            );

        // Combinar fechas y horas en LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.of(reservationRequest.getStartDateRes(), reservationRequest.getStartTimeRes());
        LocalDateTime endDateTime = LocalDateTime.of(reservationRequest.getEndDateRes(), reservationRequest.getEndTimeRes());

        // Calcular la duración en segundos
        long totalSeconds = Duration.between(startDateTime, endDateTime).getSeconds();

        // Se hace de esta manera ya que en la BD el cobro se hace por horas
        float totalHours = totalSeconds / 3600.0f;
        
        float totalCost = totalHours * rate;

        System.out.println("el costo es: " + totalCost);

        Reservation reservation = Reservation.builder()
                .startDateRes(Date.valueOf(reservationRequest.getStartDateRes()))
                .startTimeRes(Time.valueOf(reservationRequest.getStartTimeRes()))
                .endDateRes(Date.valueOf(reservationRequest.getEndDateRes()))
                .endTimeRes(Time.valueOf(reservationRequest.getEndTimeRes()))
                .creationDateRes(Date.valueOf(actualDate))
                .totalRes(totalCost)
                .licensePlate(reservationRequest.getLicensePlate())
                .client(client)
                .parkingSpace(selectedParkingSpace.get())
                .status(ResStatus.PENDING.getId())
                .build();



        reservationRepository.save(reservation);

        return new ReservationResponse(reservation,"¡La reserva se realizo exitosamente!");
    }

    public boolean isReservationNearStarting(Reservation reservation) {
        LocalTime targetTime = reservation.getStartTimeRes().toLocalTime();
        LocalTime localTime = LocalTime.now(); 
        Date targetDate = Date.valueOf(LocalDate.now());
        System.out.println(targetTime + " " + localTime);
        System.out.println("El tiempo supuesto:"+ChronoUnit.SECONDS.between(localTime, targetTime));
        if (
            reservation.getStartDateRes().equals(targetDate)
            && ChronoUnit.SECONDS.between(localTime, targetTime) < 1800
        ) {                
            return true;
        }

        return false;
    }

    public List<Reservation> getNearStartingReservations() {

        LocalTime targetTime = (LocalTime.now().getHour() == 23) ? targetTime = LocalTime.of(0,0,0): LocalTime.of(LocalTime.now().getHour()+1, 0, 0);
        
        System.out.println(targetTime);

        Date sqlDate = Date.valueOf(LocalDate.now());
        List<Reservation> reservations = reservationRepository.findByStartTime(targetTime, sqlDate, ResStatus.PENDING.getId());

        System.out.println("------------------------- Las reservas a punto de iniciar:");

        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
        
        return reservations;
    }

    @Transactional
    @Modifying
    public ReservationResponse setStatus(Reservation reservation, String status){
        reservation.setStatus(status);

        reservationRepository.save(reservation);

        return new ReservationResponse(reservation, "¡La reserva ahora se encuentra en estado: " + status);
    }

    @Transactional
    @Modifying
    public ReservationResponse checkInReservation(int idReservation) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return new ReservationResponse(null, "La reserva no fue encontrada");
        }

        Reservation reservation = optionalReservation.get();
        reservation.setStatus(ResStatus.IN_PROGRESS.getId());

        reservationRepository.save(reservation);
        return new ReservationResponse(reservation, "¡Su reserva se encuentra activa!");
    }

    @Transactional
    @Modifying
    public ReservationResponse cancelReservation(int idReservation) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return new ReservationResponse(null,"La reserva no fue encontrada");
        }

        Reservation reservation = optionalReservation.get();

        if (!reservation.getStatus().equals(ResStatus.PENDING.getId())) {
            return new ReservationResponse(null,"¡La reserva no puede ser cancelada!");
        }

        int cancellationCost = rateRepository.getCancellationCostByParkingSpace(
                reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getIdParking(),
                reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getCity().getIdCity(),
                reservation.getParkingSpace().getVehicleType().getIdVehicleType(),
                reservation.getParkingSpace().isUncovered());

        System.out.println("Costo de tarifa: " + cancellationCost);
        reservation.setTotalRes(cancellationCost);

        reservationRepository.save(reservation);
        
        return new ReservationResponse(reservation,"¡Su reserva fue cancelada!");
    }

    @Transactional
    @Modifying
    public ReservationResponse checkOutReservation(int idReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isEmpty()) {
            return new ReservationResponse(null,"No se encontró ninguna reserva con el ID proporcionado.");
        }

        return new ReservationResponse(optionalReservation.get(),"¡Su salida ha sido exitosa!");
    }

    @Transactional
    @Modifying
    public List<Reservation> automaticCheckOut() {

        LocalTime targetTime = LocalTime.of(LocalTime.now().getHour()+1, 0, 0);

        Date targetDate = Date.valueOf(LocalDate.now());

        return reservationRepository.findByEndTime(targetTime, targetDate, ResStatus.CONFIRMED.getId());

    }
    
    public Float getReservationsExtraCost(Reservation reservation){

        long extraSeconds;

        LocalTime endTime = reservation.getEndTimeRes().toLocalTime();

        if (Time.valueOf(LocalTime.now()).after(reservation.getEndTimeRes())) {
            System.out.println("Se hace cobro por pasarse de tiempo");

            extraSeconds = Duration.between(endTime, LocalTime.now()).getSeconds();

            // Se hace de esta manera ya que en la BD el cobro se hace por horas
            float extraHours = extraSeconds / 3600.0f;

            float rate = rateRepository.getHourCostByParkingSpace(
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getIdParking(),
                    reservation.getParkingSpace().getParkingSpaceId().getParking().getParkingId().getCity().getIdCity(),
                    reservation.getParkingSpace().getVehicleType().getIdVehicleType(),
                    reservation.getParkingSpace().isUncovered());

            float extraCost = extraHours * rate;

            System.out.println("--------------------------- Horas transcurridas: " + extraHours);
            reservation.setTotalRes(reservation.getTotalRes() + extraCost);

            // Guardar cambios
            reservationRepository.save(reservation);

            return extraCost;
        }

        return 0.0f;

    }

    @Modifying
    @Transactional
    public void setTotalRes(Reservation reservation, float applyDiscount) {
        reservation.setTotalRes(applyDiscount);
        reservationRepository.save(reservation);
    }

    @Transactional
    public UserAction getUserAction(String userId, String docType, String descAction, String ipUser) {

        // obtenemos la fecha actual en la que se hizo el registro
        LocalDate currentDate = LocalDate.now();

        // Convertir LocalDate a java.sql.Date
        Date dateAction = Date.valueOf(currentDate);

        // obtenemos la informacion del usuario

        //Traemos los datos del usuario
        User user = userRepository.findByUserId(userId, docType)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        UserAction userAction = UserAction.builder()
                .dateAction(dateAction)
                .descAction(descAction)
                .ipUser(ipUser)
                .userActionId(user)
                .build();
        return userAction;
    }

}
