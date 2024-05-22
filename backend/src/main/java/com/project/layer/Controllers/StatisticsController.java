package com.project.layer.Controllers;
import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.DateSumRequest;
import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Services.Parameterization.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    
    private final StatisticsService statisticsService;

    @GetMapping("/average-hour/")
    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        return statisticsService.getHourAverage(initialDate, finalDate, idParking);
    }

    @GetMapping("/sales")
    public List<DateSumRequest> getSales(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        return statisticsService.getSales(initialDate, finalDate, idParking);
    }

}
