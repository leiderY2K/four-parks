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
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IParkingRepository parkingRepository;
    private final IUserRepository userRepository;

    public String startReservation(StartReservationRequest reservationRequest) {
        
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        
        // Convertir LocalDate a Date
        Date sqlDate = Date.valueOf(currentDate);

        List<ParkingSpace> parkingSpace = parkingRepository.getAvailableParkingSpace(
            reservationRequest.getCityId(),
            reservationRequest.getParkingId(),
            reservationRequest.getDateRes(),
            reservationRequest.getStartTimeRes(),
            reservationRequest.getEndTimeRes()
        );

        if(parkingSpace.isEmpty()){
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
            .vehicleType(parkingSpace.get(0).getIdVehicleType())
            .parkingSpace(parkingSpace.get(0))
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
