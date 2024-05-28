package com.project.layer.Controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.DateSumRequest;
import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Controllers.Requests.HourOccupationRequest;
import com.project.layer.Controllers.Requests.UncoverVehicPercentageRequest;
import com.project.layer.Controllers.Requests.VehiclePercentageRequest;
import com.project.layer.Services.Statistics.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/average-hour")
    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        return statisticsService.getHourAverage(initialDate, finalDate, idParking);
    }

    @GetMapping("/all-average-hour")
    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate) {
        return statisticsService.getAllHourAverage(initialDate, finalDate);
    }

    @GetMapping("/city-average-hour")
    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam String city) {
        return statisticsService.getCityHourAverage(initialDate, finalDate, city);
    }

    @GetMapping("/sales")
    public List<DateSumRequest> getSales(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        return statisticsService.getSales(initialDate, finalDate, idParking);
    }

    @GetMapping("/all-sales")
    public List<DateSumRequest> getAllSales(@RequestParam Date initialDate, @RequestParam Date finalDate) {
        return statisticsService.getAllSales(initialDate, finalDate);
    }

    @GetMapping("/city-sales")
    public List<DateSumRequest> getCitySales(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam String city) {
        return statisticsService.getCitySales(initialDate, finalDate, city);
    }

    @GetMapping("/occupation")
    public List<HourOccupationRequest> getOccupation(@RequestParam Date date, @RequestParam int idParking) {
        return statisticsService.getOccupation(date, idParking);
    }

    @GetMapping("/all-occupation")
    public List<HourOccupationRequest> getAllOccupation(@RequestParam Date date) {
        return statisticsService.getAllOccupation(date);
    }

    @GetMapping("/city-occupation")
    public List<HourOccupationRequest> getCityOccupation(@RequestParam Date date, @RequestParam String city) {
        return statisticsService.getCityOccupation(date, city);
    }

    @GetMapping("/city-vehicle-percentage")
    public List<VehiclePercentageRequest> getCityVehiclePercentage(@RequestParam String city){
        return statisticsService.getCityVehiclePercentage(city);
    }       

    @GetMapping("/city-isuncovered-percentage")
    public List<UncoverVehicPercentageRequest> getCityIsUncoveredPercentage(@RequestParam String city){
        return statisticsService.getCityIsUncoveredPercentage(city);
    }    
}
