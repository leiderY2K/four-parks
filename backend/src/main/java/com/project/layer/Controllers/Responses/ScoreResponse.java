package com.project.layer.Controllers.Responses;

import com.project.layer.Persistence.Entity.ScoreSystem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreResponse {
    ScoreSystem scoreSystem;
    String message;
}
