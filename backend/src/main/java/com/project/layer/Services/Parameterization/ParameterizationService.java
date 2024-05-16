package com.project.layer.Services.Parameterization;
 
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
 
import com.project.layer.Controllers.Requests.DateHourCountRequest;
import com.project.layer.Controllers.Requests.HourAveragemRequest;
import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Controllers.Requests.StatisticsRequest;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ParkingSpaceId;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IReservationRepository;
 
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class ParameterizationService {
 
    private final IParkingRepository parkingRepository;
    private final IReservationRepository reservationRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
 
    @Transactional
    @Modifying
    public String modifyParking(Parking parkingChanges) {
        Optional<Parking> optionalParking = parkingRepository.findById(parkingChanges.getIdParking());
 
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
 
            // Obtener todas las variables declaradas en la clase Parking
            Field[] fields = Parking.class.getDeclaredFields();
 
            for (Field field : fields) {
                // Hacer que el campo sea accesible, incluso si es privado
                field.setAccessible(true);
 
                try {
                    // Obtener el valor del campo en el objeto parkingChanges
                    Object value = field.get(parkingChanges);
 
                    // Si el valor no es nulo, establecerlo en el objeto parking original
                    if (value != null) {
                        field.set(parking, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    // Manejar la excepción si se produce un error al acceder al campo
                }
            }
 
            parkingRepository.save(parking);
            return "Se realizó la modificación";
        } else {
            return "No se encontró ningún estacionamiento con el ID proporcionado";
        }
    }
 
    public List<ParkingSpace> getParkingSpace(int parkingId, String idCity) {
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAllByParking(parkingId, idCity);
 
        return parkingSpaces;
    }
 
    @Transactional
    @Modifying
    public String insertParkingSpace(ParkingSpaceRequest psRequest) {
 
        int lastIdPSValue = parkingSpaceRepository.countByParking(psRequest.getIdParking(), psRequest.getIdCity());
 
        for (int i = 0; i < psRequest.getAmount(); i++) {
 
            lastIdPSValue++;
 
            ParkingSpaceId pkID = ParkingSpaceId.builder()
                    .idParkingSpace(lastIdPSValue)
                    .idCity(psRequest.getIdCity())
                    .idParking(psRequest.getIdParking())
                    .build();
 
            ParkingSpace parkingSpace = ParkingSpace.builder()
                    .idVehicleType(psRequest.getVehicleType())
                    .isUncovered(psRequest.getIsUncovered())
                    .parkingSpaceId(pkID)
                    .build();
 
            // Guardar el espacio de estacionamiento en la base de datos
            parkingSpaceRepository.save(parkingSpace);
        }
 
        return "Se agregaron correctamente los espacios de los parqueaderos";
    }
 
    @Transactional
    @Modifying
    public String deleteParkingSpace(ParkingSpaceRequest psRequest) {
 
        // Guardar el espacio de estacionamiento en la base de datos
        parkingSpaceRepository.deleteByParking(
                psRequest.getIdParking(),
                psRequest.getIdCity(),
                psRequest.getIsUncovered(),
                psRequest.getVehicleType(),
                psRequest.getAmount());
 
        return "Se eliminaron correctamente los espacios de los parqueaderos";
    }
 
    public List<Parking> searchParking(UserId adminId) {
 
        System.out.println(adminId.getIdDocType() + " xd " + adminId.getIdUser());
 
        List<Parking> parkingList = parkingRepository.findByAdminId(adminId.getIdUser(), adminId.getIdDocType());
        for (Parking parking : parkingList) {
            System.out.println(parking.toString());
        }
        return parkingList;
    }
 
    public List<HourAveragemRequest> getStatistics(StatisticsRequest statisticRequest) {
        LocalDate iniDate = statisticRequest.getInitialDate().toLocalDate().plusDays(1);
        LocalDate finDate = statisticRequest.getFinalDate().toLocalDate().plusDays(1);
        System.out.println("finDate: " + statisticRequest.getFinalDate());
        LocalTime hour = LocalTime.of(0, 0);
        List<DateHourCountRequest> prevList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(iniDate, finDate) + 1;
        System.out.println("total dias: " + daysBetween);
        for (int i = 0; i < daysBetween; i++) {
            for (int j = 0; j <= 23; j++) {
                DateHourCountRequest auxDHC = new DateHourCountRequest(); // Crear una nueva instancia en cada iteración
                auxDHC.setCount(reservationRepository.getDateHourCount(Date.valueOf(iniDate), Time.valueOf(hour), statisticRequest.getIdParking()));
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
 
}