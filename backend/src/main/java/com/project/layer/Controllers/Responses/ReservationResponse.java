package com.project.layer.Controllers.Responses;

import com.project.layer.Persistence.Entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
    private Reservation reservation;
    private String message;
}
