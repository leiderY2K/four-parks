package com.project.layer.Services.Parameterization;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.ParkingSpaceRequest;
import com.project.layer.Controllers.Responses.ParkingResponse;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ParkingSpaceId;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IParkingRepository;
import com.project.layer.Persistence.Repository.IParkingSpaceRepository;
import com.project.layer.Persistence.Repository.IRateRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParameterizationService {
    
    private final IParkingRepository parkingRepository;
    private final IParkingSpaceRepository parkingSpaceRepository;
    private final IRateRepository rateRepository;

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

    public List<ParkingSpace> getParkingSpace(int parkingId, String idCity){
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

            ParkingSpace parkingSpace  = ParkingSpace.builder()
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
            psRequest.getAmount()
        );

        return "Se eliminaron correctamente los espacios de los parqueaderos";
    }

    public List<ParkingResponse> searchParking(UserId adminId){

        System.out.println(adminId.getIdDocType()+" xd "+adminId.getIdUser());

        List<ParkingResponse> parkingResponses = new ArrayList<>();

        List<Parking> parkingList = parkingRepository.findByAdminId(adminId.getIdUser(),adminId.getIdDocType());
        for (Parking parking : parkingList) {
            System.out.println(parking.toString());
            List<String> vehicleListType = parkingRepository.getTypeVehicleByParking(parking.getIdParking());

            System.out.println(vehicleListType.toString());
            Map<String, Object> tipoVehiculo = new HashMap<>();

            for (String vehicle : vehicleListType) {
                
                Map<String, Integer> vehicleType = new HashMap<>();
        
                if(parking.getParkingType().getIdParkingType().equals("COV") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                    vehicleType.put("covered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                        parking.getIdParking(), false, vehicle));
                    vehicleType.put("rate-covered", rateRepository.getHourCostByParkingSpace(
                        parking.getIdParking(), parking.getCity().getIdCity(), vehicle, false
                    ));
                }
                if(parking.getParkingType().getIdParkingType().equals("UNC") || parking.getParkingType().getIdParkingType().equals("SEC")) {
                    vehicleType.put("uncovered", parkingRepository.countByCoveredAndParkingAndVehicleType(
                        parking.getIdParking(), true, vehicle
                    ));
                    vehicleType.put("rate-uncovered", rateRepository.getHourCostByParkingSpace(
                        parking.getIdParking(), parking.getCity().getIdCity(), vehicle, true
                    ));
                }
                tipoVehiculo.put(vehicle, vehicleType);
            }

            parkingResponses.add(ParkingResponse.builder().parking(parking).capacity(tipoVehiculo).build());

        }
        return parkingResponses; 
    }

}
