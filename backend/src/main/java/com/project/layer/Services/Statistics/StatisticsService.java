package com.project.layer.Services.Statistics;
 
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.layer.Controllers.Requests.DateHourCountRequest;
import com.project.layer.Controllers.Requests.DateSumRequest;
import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Persistence.Repository.IReservationRepository;

import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final IReservationRepository reservationRepository;
  
   
  
    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        LocalDate iniDate = initialDate.toLocalDate().plusDays(1);
        LocalDate finDate = finalDate.toLocalDate().plusDays(1);
        System.out.println("finDate: " + finalDate);
        LocalTime hour = LocalTime.of(0, 0);
        List<DateHourCountRequest> prevList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate) + 1;
        System.out.println("total dias: " + daysBetween);
        for (int i = 0; i < daysBetween; i++) {
            for (int j = 0; j <= 23; j++) {
                DateHourCountRequest auxDHC = new DateHourCountRequest(); // Crear una nueva instancia en cada iteración
                auxDHC.setCount(
                        reservationRepository.getDateHourCount(Date.valueOf(iniDate), Time.valueOf(hour), 2));
                auxDHC.setDate(Date.valueOf(iniDate));
                auxDHC.setHour(Time.valueOf(hour));
                prevList.add(auxDHC); // Agregar la instancia a la lista
                hour = hour.plusHours(1);
            }
            hour = LocalTime.of(0, 0);
            iniDate = iniDate.plusDays(1);
        }

        List<HourAveragemRequest> returnList = new ArrayList<>();
        hour = LocalTime.of(0, 0); // Reiniciar hour
        for (int i = 0; i <= 23; i++) {
            HourAveragemRequest auxAverage = new HourAveragemRequest();
            final Time aHour = Time.valueOf(hour);
            List<DateHourCountRequest> auxList = prevList.stream().filter(obj -> obj.getHour().equals(aHour))
                    .collect(Collectors.toList());
            int plus = auxList.stream().mapToInt(DateHourCountRequest::getCount).sum();
            float average = (float) plus / auxList.size();
            String timeString = aHour.toString();
            String[] parts = timeString.split(":");
            String hours = parts[0];
            String minutes = parts[1];
            String auxHour = hours + ":" + minutes;
            auxAverage.setHour(auxHour);
            auxAverage.setAverage(average);
            returnList.add(auxAverage);
            hour = hour.plusHours(1);
        }
        System.out.println(returnList);

        return returnList;
    }

    public List<DateSumRequest> getSales(Date initialDate, Date finalDate, int idParking) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        LocalDate auxDate = initialDate.toLocalDate();
        System.out.println("INITIAL DATE: " + initialDate);
        System.out.println("FINAL DATE: " + finalDate); // Corrigido para imprimir la fecha final correcta
        List<DateSumRequest> returnList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate);
        System.out.println("total dias: " + daysBetween);
    
        for (int i = 0; i <= daysBetween; i++) {
            DateSumRequest auxDS = new DateSumRequest(); // Crear una nueva instancia en cada iteración
            Float sum = reservationRepository.getSumTotalRes(Date.valueOf(auxDate), idParking);
            auxDS.setSum(sum != null ? sum : 0.0f); // Manejar el caso donde sum es null
            auxDS.setDate(Date.valueOf(auxDate));
            returnList.add(auxDS); // Agregar la instancia a la lista
            System.out.println("INITIAL DATE: " + initialDate);
            auxDate = auxDate.plusDays(1);
            System.out.println("AUX DATE: " + auxDate);
            System.out.println(returnList);
        }
    
        return returnList;
    }

}
