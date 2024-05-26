package com.project.layer.Services.ScoreSystem;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.ScoreRequest;
import com.project.layer.Controllers.Responses.ClientScoreResponse;
import com.project.layer.Controllers.Responses.ScoreResponse;
import com.project.layer.Persistence.Entity.ClientScore;
import com.project.layer.Persistence.Entity.ClientScoreId;
import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingId;
import com.project.layer.Persistence.Entity.ScoreSystem;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Repository.IClientScoreRepository;
import com.project.layer.Persistence.Repository.IScoreSystemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreSystemService {

    private final IScoreSystemRepository scoreSystemRepository;
    private final IClientScoreRepository clientScoreRepository;

    @Transactional
    @Modifying
    public ClientScoreResponse insertClient(User client, Parking parking){
                
        ClientScoreId clientScoreId = ClientScoreId.builder()
                                        .client(client)
                                        .parking(parking)
                                    .build();

        if (clientScoreRepository.existsById(clientScoreId))return new ClientScoreResponse(null, "El servicio de fidelización ya estaba previamente creado para este cliente");

        ClientScore clientScore = ClientScore.builder()
                                        .scoreSystemId(clientScoreId)
                                        .scorePoints(0)
                                        .residue(0.0f)
                                    .build();

        clientScoreRepository.save(clientScore);
        
        return new ClientScoreResponse(clientScore, "¡Su sistema de fidelización fue correctamente creado!");
    }

    @Transactional
    @Modifying
    public ScoreResponse insertScore(ParkingId parkingId, ScoreRequest scoreRequest){
        
        if (scoreSystemRepository.existsById(parkingId)) return new ScoreResponse(null, "El sistema de puntaje ya existe para este parqueadero");

        ScoreSystem scoreSystem = ScoreSystem.builder()
                                        .parkingId(parkingId)
                                        .targetPoints(scoreRequest.getTargetPoints())
                                        .targetValue(scoreRequest.getTargetValue())
                                        .build();


        scoreSystemRepository.save(scoreSystem);
        
        return new ScoreResponse(scoreSystem, "¡Su sistema de fidelización fue correctamente creado!");
    }

    @Transactional
    @Modifying
    public ScoreResponse modifyParkingScoreTargets(int parkingId, String idCity, ScoreRequest scoreRequest){
        
        ScoreSystem scoreSystem = scoreSystemRepository.findByParkingId(parkingId, idCity);

        if(scoreSystem == null) return new ScoreResponse(null, "No se encontro un registro de puntos para este parqueadero");

        if (scoreRequest.getTargetPoints() != null) scoreSystem.setTargetPoints(scoreRequest.getTargetPoints());

        if (scoreRequest.getTargetValue() != null) scoreSystem.setTargetValue(scoreRequest.getTargetValue());

        scoreSystemRepository.save(scoreSystem);
        
        return new ScoreResponse(scoreSystem,"¡Se logro realizar la modificación!"); 
    }

    @Transactional
    @Modifying
    public float applyDiscount(User client, Parking parking, float rate, float totalValue) {
        ScoreSystem scoreSystem = scoreSystemRepository.findById(parking.getParkingId()).get();
        ClientScore clientScore = clientScoreRepository.findById(new ClientScoreId(client, parking)).get();

        if(clientScore.getScorePoints() < scoreSystem.getTargetPoints()) return totalValue;

        // Calcular las horas de descuento según los puntos del cliente
        int pointHours = clientScore.getScorePoints() / scoreSystem.getTargetPoints();
        clientScore.setScorePoints(clientScore.getScorePoints() % scoreSystem.getTargetPoints());

        clientScoreRepository.save(clientScore);

        float discount = pointHours * rate;

        return totalValue - discount;
    }
    
    @Transactional
    @Modifying
    public void increaseScore(User client, Parking parking, Float totalValue) {
        ScoreSystem scoreSystem = scoreSystemRepository.findById(parking.getParkingId()).get();
        ClientScore clientScore = clientScoreRepository.findById(new ClientScoreId(client, parking)).get();

        totalValue += clientScore.getResidue();

        int newPoints = (int) (totalValue / scoreSystem.getTargetValue());
        clientScore.setScorePoints(clientScore.getScorePoints() + newPoints);

        clientScore.setResidue(totalValue % scoreSystem.getTargetValue());

        clientScoreRepository.save(clientScore);
    }

    public boolean existsParkingScore(Parking parking) {
        return scoreSystemRepository.existsById(parking.getParkingId());
    }

    public boolean isAfiliated(User client, Parking parking) {
        return clientScoreRepository.existsById(new ClientScoreId(client, parking));
    }
}
