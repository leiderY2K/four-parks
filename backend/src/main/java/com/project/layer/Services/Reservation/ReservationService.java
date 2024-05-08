package com.project.layer.Services.Reservation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.EndReservationRequest;
import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
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

    @Transactional
    public String startReservation(StartReservationRequest reservationRequest) {
        
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        
        // Convertir LocalDate a Date
        Date sqlDate = Date.valueOf(currentDate);

        System.out.println("La fecha: "+reservationRequest.getDateRes());

        List<Integer> busyParkingSpaces = reservationRepository.findBusyParkingSpaces(
            reservationRequest.getCityId(),
            reservationRequest.getParkingId(),
            reservationRequest.getVehicleType(),
            reservationRequest.getDateRes(),
            reservationRequest.getStartTimeRes(),
            reservationRequest.getEndTimeRes()
        );

        System.out.println(busyParkingSpaces.toString());
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
            .build();

        reservationRepository.save(reservation);
        return "¡La reserva se realizo exitosamente!";
    }

    public String endReservation(EndReservationRequest reservationRequest) {
        return "El precio final de su pago es:";
    }

    public List<Reservation> getReservationsByClientId(UserId clientPK) {
        return reservationRepository.findAllByClientId(clientPK.getIdUser(), clientPK.getIdDocType());
    }
    
}
