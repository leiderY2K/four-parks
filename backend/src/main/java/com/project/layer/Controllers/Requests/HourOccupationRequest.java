package com.project.layer.Controllers.Requests;


import java.sql.Time;
import java.sql.Date;


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
public class HourOccupationRequest {

    public Time hour;

    public float occupation;

    public String parkings;

   
}