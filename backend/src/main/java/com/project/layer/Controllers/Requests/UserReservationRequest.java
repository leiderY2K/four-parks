package com.project.layer.Controllers.Requests;

import com.project.layer.Persistence.Entity.UserId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationRequest {

    UserId clientId;

    String status;
    
}
