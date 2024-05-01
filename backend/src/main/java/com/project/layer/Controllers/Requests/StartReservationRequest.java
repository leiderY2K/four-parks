package com.project.layer.Controllers.Requests;

import java.sql.Timestamp;

import com.project.layer.Persistence.Entity.UserId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartReservationRequest {

    private Timestamp startTimeRes;

    private Timestamp endTimeRes;

    private String licensePlate;

    private String reservationCol;

    private UserId clientId;

    private String cityId;

    private String parkingId;

    private String parkingSpaceId;

}
