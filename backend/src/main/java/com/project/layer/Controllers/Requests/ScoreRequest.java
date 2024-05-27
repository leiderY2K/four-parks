package com.project.layer.Controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequest {

    public Boolean putEnable;
    
    public Integer targetPoints;

    public Float targetValue;
}
