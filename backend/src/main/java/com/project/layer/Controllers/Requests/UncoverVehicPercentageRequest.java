package com.project.layer.Controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UncoverVehicPercentageRequest {

    public String uncover;

    public String vehicle;

    public float percentage;
   
}