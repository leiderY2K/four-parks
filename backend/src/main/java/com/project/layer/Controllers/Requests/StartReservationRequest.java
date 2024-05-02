package com.project.layer.Controllers.Requests;

import java.sql.Date;
import java.sql.Time;

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

    private Date dateRes;

    private Time startTimeRes;

    private Time endTimeRes;

    private String licensePlate;

    private UserId clientId;

    private String cityId;

    private String parkingId;

}
