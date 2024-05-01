package com.project.layer.Services.Reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.EndReservationRequest;
import com.project.layer.Controllers.Requests.StartReservationRequest;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;

    public ParkingSpace startParkingSpace(StartReservationRequest reservationRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startParkingSpace'");
    }

    public ParkingSpace endParkingSpace(EndReservationRequest reservationRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endParkingSpace'");
    }

    public List<Reservation> getReservationsByClientId(UserId clientPK) {
        return reservationRepository.findAllByClientId(clientPK.getIdUser(), clientPK.getIdDocType());
    }
    
}
