package com.project.layer.Controllers.Responses;

import com.project.layer.Persistence.Entity.ClientScore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientScoreResponse {
    
    ClientScore scoreSystem;

    String message;

}
