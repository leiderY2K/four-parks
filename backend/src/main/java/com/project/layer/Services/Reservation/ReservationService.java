package com.project.layer.Services.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.EndReservationRequest;
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
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
    private final IUserRepository userRepository;
    private final IRateRepository rateRepository;

    @Transactional
    public String startReservation(StartReservationRequest reservationRequest) {
        
        Date sqlDate = Date.valueOf(LocalDate.now());

        System.out.println("La fecha: "+reservationRequest.getDateRes());

        Time startTime = Time.valueOf(reservationRequest.getStartTimeRes().toLocalTime().minusMinutes(59));
        Time entTime = Time.valueOf(reservationRequest.getEndTimeRes().toLocalTime().plusMinutes(59));
        List<Integer> busyParkingSpaces = reservationRepository.findBusyParkingSpaces(
            reservationRequest.getCityId(),
            reservationRequest.getParkingId(),
            reservationRequest.getVehicleType(),
            reservationRequest.getDateRes(),
            startTime,
            entTime
        );

        System.out.println("Numero de parqueaderos ocupados en ese intervalo de tiempo: " + busyParkingSpaces.toString());
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAllByParking(reservationRequest.getParkingId(), reservationRequest.getCityId());

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

        if(!wasFoundAnyParkingSpace){
            return  "No hay espacios disponibles";
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

        reservationRepository.save(reservation);
        return "¡La reserva se realizo exitosamente!";
    }

    public String checkInReservation(EndReservationRequest reservationRequest) {
        return "El precio final de su pago es:";
    }

    public String cancelReservation(int idReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus(ResStatus.CANCELLED.getId());
        }
        return "El precio final de su pago es:";
    }

    public String checkOutReservation(int idReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);
    
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            System.out.println("La reserva es: " + reservation.toString());
            
            long totalSeconds; 
            
            LocalTime startTime = reservation.getStartTimeRes().toLocalTime();
            LocalTime endTime = reservation.getEndTimeRes().toLocalTime();
    
            if (Time.valueOf(LocalTime.now()).after(reservation.getEndTimeRes())) {
                System.out.println("Aqui ya se acabo la reserva");
                totalSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
            }else{
                totalSeconds = Duration.between(startTime, endTime).getSeconds();
                System.out.println("Aun no se ha acabado la reserva");
            }
    
            float totalHours = totalSeconds / 3600.0f;
    
            int rate = rateRepository.getHourCostByParkingSpace(
                reservation.getParkingSpace().getParkingSpaceId().getIdParking(),
                reservation.getParkingSpace().getParkingSpaceId().getIdCity(),
                reservation.getVehicleType(), 
                reservation.getParkingSpace().isUncovered()
            );
    
            float totalCost = totalHours * rate;
    
            System.out.println("--------------------------- Horas transcurridas: " + totalHours);
    
            // Actualizar campos
            reservation.setStatus(ResStatus.COMPLETED.getId());
            reservation.setTotalRes(totalCost);
    
            // Guardar cambios
            reservationRepository.save(reservation);
    
            return "El precio final de su pago es:" + totalCost;
        } else {
            return "No se encontró ninguna reserva con el ID proporcionado.";
        }
    }

    public List<Reservation> getReservationsByClientId(UserReservationRequest urRequest) {
        return reservationRepository.findAllByClientId(urRequest.getClientId().getIdUser(), urRequest.getClientId().getIdDocType(),urRequest.getStatus() );
    }
    
}
