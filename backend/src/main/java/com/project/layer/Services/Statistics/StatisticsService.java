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

import com.project.layer.Controllers.Requests.HourOccupationRequest;
import com.project.layer.Controllers.Requests.UncoverVehicPercentageRequest;
import com.project.layer.Controllers.Requests.VehiclePercentageRequest;
import com.project.layer.Controllers.Requests.DateHourCountRequest;
import com.project.layer.Controllers.Requests.DateSumRequest;
import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.Reservation;
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IVehicleTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final IParkingRepository parkingRepository;
    private final IReservationRepository reservationRepository;
    private final IVehicleTypeRepository vehicleTypeRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;

    public List<HourAveragemRequest> getHourAverage(@RequestParam Date initialDate, @RequestParam Date finalDate,
            @RequestParam int idParking) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        LocalTime hour = LocalTime.of(0, 0);
        List<DateHourCountRequest> prevList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate) + 1;
        for (int i = 0; i < daysBetween; i++) {
            for (int j = 0; j <= 23; j++) {
                DateHourCountRequest auxDHC = new DateHourCountRequest(); // Crear una nueva instancia en cada iteración
                auxDHC.setCount(
                        reservationRepository.getDateHourCount(Date.valueOf(iniDate), Time.valueOf(hour), idParking));
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

        return returnList;
    }

    public List<HourAveragemRequest> getAllHourAverage(Date initialDate, Date finalDate) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        LocalTime hour = LocalTime.of(0, 0);
        List<DateHourCountRequest> prevList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate) + 1;
        for (int i = 0; i < daysBetween; i++) {
            for (int j = 0; j <= 23; j++) {
                DateHourCountRequest auxDHC = new DateHourCountRequest(); // Crear una nueva instancia en cada iteración
                auxDHC.setCount(
                        reservationRepository.getAllDateHourCount(Date.valueOf(iniDate), Time.valueOf(hour)));
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

        return returnList;
    }

    public List<HourAveragemRequest> getCityHourAverage(Date initialDate, Date finalDate, String city) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        LocalTime hour = LocalTime.of(0, 0);
        List<DateHourCountRequest> prevList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate) + 1;
        for (int i = 0; i < daysBetween; i++) {
            for (int j = 0; j <= 23; j++) {
                DateHourCountRequest auxDHC = new DateHourCountRequest(); // Crear una nueva instancia en cada iteración
                auxDHC.setCount(
                        reservationRepository.getCityDateHourCount(Date.valueOf(iniDate), Time.valueOf(hour), city));
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

        return returnList;
    }

    public List<DateSumRequest> getSales(Date initialDate, Date finalDate, int idParking) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        LocalDate auxDate = initialDate.toLocalDate();
        List<DateSumRequest> returnList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate);

        for (int i = 0; i <= daysBetween; i++) {
            DateSumRequest auxDS = new DateSumRequest(); // Crear una nueva instancia en cada iteración
            Float sum = reservationRepository.getSumTotalRes(Date.valueOf(auxDate), idParking);
            auxDS.setSum(sum != null ? sum : 0.0f); // Manejar el caso donde sum es null
            auxDS.setDate(Date.valueOf(auxDate));
            returnList.add(auxDS); // Agregar la instancia a la lista
            auxDate = auxDate.plusDays(1);
        }

        return returnList;
    }

    public List<DateSumRequest> getAllSales(Date initialDate, Date finalDate) {

        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        List<DateSumRequest> returnList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate);
        for (int i = 0; i <= daysBetween; i++) {
            DateSumRequest auxDS = new DateSumRequest(); // Crear una nueva instancia en cada iteración
            Float sum = reservationRepository.getSumTotalRes(Date.valueOf(iniDate));
            auxDS.setSum(sum != null ? sum : 0.0f); // Manejar el caso donde sum es null
            auxDS.setDate(Date.valueOf(iniDate));
            returnList.add(auxDS); // Agregar la instancia a la lista
            iniDate = iniDate.plusDays(1);
        }

        return returnList;
    }

    public List<DateSumRequest> getCitySales(Date initialDate, Date finalDate, String city) {
        LocalDate iniDate = initialDate.toLocalDate();
        LocalDate finDate = finalDate.toLocalDate();
        List<DateSumRequest> returnList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate);
        for (int i = 0; i <= daysBetween; i++) {
            DateSumRequest auxDS = new DateSumRequest(); // Crear una nueva instancia en cada iteración
            Float sum = reservationRepository.getSumTotalRes(Date.valueOf(iniDate), city);
            auxDS.setSum(sum != null ? sum : 0.0f); // Manejar el caso donde sum es null
            auxDS.setDate(Date.valueOf(iniDate));
            returnList.add(auxDS); // Agregar la instancia a la lista
            iniDate = iniDate.plusDays(1);
        }

        return returnList;
    }

    public List<HourOccupationRequest> getOccupation(Date dateFind, int idParking) {
        LocalTime hour = LocalTime.of(0, 0);
        List<HourOccupationRequest> resList = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            final Time aHour = Time.valueOf(hour);
            HourOccupationRequest auxHourOccupation = new HourOccupationRequest(); // Crear una nueva instancia en cada
                                                                                   // iteración
            auxHourOccupation.setOccupation(reservationRepository.occupationHour(dateFind, aHour, idParking)
                    / parkingRepository.getCapacity(idParking));
            auxHourOccupation.setHour(aHour);
            resList.add(auxHourOccupation);
            hour = hour.plusHours(1);
        }
        return resList;
    }

    public List<HourOccupationRequest> getAllOccupation(Date date) {
        LocalTime hour = LocalTime.of(0, 0);
        List<HourOccupationRequest> resList = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            final Time aHour = Time.valueOf(hour);
            HourOccupationRequest auxHourOccupation = new HourOccupationRequest(); // Crear una nueva instancia en cada
                                                                                   // iteración
            auxHourOccupation.setOccupation(
                    reservationRepository.allOccupationHour(date, aHour) / parkingRepository.getCapacity());
            auxHourOccupation.setHour(aHour);
            resList.add(auxHourOccupation);
            hour = hour.plusHours(1);
        }
        return resList;
    }

    public List<HourOccupationRequest> getCityOccupation(Date date, String city) {
        LocalTime hour = LocalTime.of(0, 0);
        List<HourOccupationRequest> resList = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            final Time aHour = Time.valueOf(hour);
            HourOccupationRequest auxHourOccupation = new HourOccupationRequest(); // Crear una nueva instancia en cada
                                                                                   // iteración
            auxHourOccupation.setOccupation(
                    reservationRepository.cityOccupationHour(date, aHour, city) / parkingRepository.getCapacity(city));
            auxHourOccupation.setHour(aHour);
            resList.add(auxHourOccupation);
            hour = hour.plusHours(1);
        }
        return resList;
    }

    public List<VehiclePercentageRequest> getCityVehiclePercentage(String city) {
        List<Reservation> reservations = reservationRepository.allCityRes(city);
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.allCityParkingSpaces(city);
        List<String> vehicleDesc = vehicleTypeRepository.getDescVehicles();
        List<String> mapVehicleList = new ArrayList<>();
        List<VehiclePercentageRequest> resList = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            for (int j = 0; j < parkingSpaces.size(); j++) {
                if (reservations.get(i).getParkingSpace().getParkingSpaceId().getIdParkingSpace()
                        .equals(parkingSpaces.get(j).getParkingSpaceId().getIdParkingSpace())
                        && reservations.get(i).getParkingSpace().getParkingSpaceId().getParking()
                                .equals(parkingSpaces.get(j).getParkingSpaceId().getParking())
                        && reservations.get(i).getParkingSpace().getParkingSpaceId().getParking().getParkingId()
                                .getCity().equals(parkingSpaces.get(j).getParkingSpaceId().getParking().getParkingId()
                                        .getCity())) {
                    mapVehicleList.add(parkingSpaces.get(j).getVehicleType().getDescVehicleType());
                }
            }
        }
        for (int i = 0; i < vehicleDesc.size(); i++) {
            VehiclePercentageRequest aux = new VehiclePercentageRequest();
            aux.setVehicle(vehicleDesc.get(i));
            aux.setPercentage(
                    (float) mapVehicleList.stream().filter(vehicleDesc.get(i)::equals).count() / mapVehicleList.size());
            resList.add(aux);
        }

        return resList;
    }

    public List<UncoverVehicPercentageRequest> getCityIsUncoveredPercentage(String city) {
        List<Reservation> reservations = reservationRepository.allCityRes(city);
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.allCityParkingSpaces(city);
        List<String> vehicleDesc = vehicleTypeRepository.getDescVehicles();
        List<String> vehicleUncovered = new ArrayList<>();
        vehicleUncovered.add("uncovered");
        vehicleUncovered.add("covered");
        List<String> mapUncoveredVehicleList = new ArrayList<>();
        List<UncoverVehicPercentageRequest> resList = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            for (int j = 0; j < parkingSpaces.size(); j++) {
                
                if (reservations.get(i).getParkingSpace().getParkingSpaceId().getIdParkingSpace()
                        .equals(parkingSpaces.get(j).getParkingSpaceId().getIdParkingSpace())
                        && reservations.get(i).getParkingSpace().getParkingSpaceId().getParking()
                                .equals(parkingSpaces.get(j).getParkingSpaceId().getParking())
                        && reservations.get(i).getParkingSpace().getParkingSpaceId().getParking().getParkingId()
                                .getCity().equals(parkingSpaces.get(j).getParkingSpaceId().getParking().getParkingId()
                                        .getCity())) {
                    if (parkingSpaces.get(j).isUncovered()) {                        
                        mapUncoveredVehicleList.add(parkingSpaces.get(j).getVehicleType().getDescVehicleType()+" "+"uncovered");
                    } else {
                        mapUncoveredVehicleList.add(parkingSpaces.get(j).getVehicleType().getDescVehicleType()+" "+"covered");
                    }

                }
            }
        }

        for (int i = 0; i < vehicleDesc.size(); i++) {
            for (int j = 0; j < vehicleUncovered.size(); j++) {
                UncoverVehicPercentageRequest aux = new UncoverVehicPercentageRequest();
                aux.setUncover(vehicleUncovered.get(j));
                aux.setVehicle(vehicleDesc.get(i));
                String auxString = vehicleDesc.get(i)+" "+vehicleUncovered.get(j);
                aux.setPercentage(
                    (float) mapUncoveredVehicleList.stream().filter(auxString::equals).count() / mapUncoveredVehicleList.size());
                    resList.add(aux);
            }
        }

       return resList;
    }

}
