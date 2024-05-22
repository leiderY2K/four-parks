package com.project.layer.Controllers.Requests;


import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsRequest {
   
    public Date initialDate;

    public Date finalDate;

    public int idParking;

}
