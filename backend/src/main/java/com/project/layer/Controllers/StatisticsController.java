package com.project.layer.Controllers;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Controllers.Requests.StatisticsRequest;
import com.project.layer.Services.Parameterization.ParameterizationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    
    private final ParameterizationService parameterizationService;

    @GetMapping("/getStatistics")
    public List<HourAveragemRequest> getStatistics(@RequestBody StatisticsRequest statisticRequest) {
        return parameterizationService.getStatistics(statisticRequest);
    }

}
