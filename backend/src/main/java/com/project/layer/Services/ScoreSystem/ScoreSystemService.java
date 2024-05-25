package com.project.layer.Services.ScoreSystem;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.Parking;
import com.project.layer.Persistence.Entity.ParkingSpace;
import com.project.layer.Persistence.Entity.ScoreSystem;
import com.project.layer.Persistence.Entity.ScoreSystemId;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Repository.IRateRepository;
import com.project.layer.Persistence.Repository.IScoreSystemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreSystemService {

    IScoreSystemRepository scoreSystemRepository;
    IRateRepository rateRepository;


//    @Transactional
//    @Modifying
//    public void increaseScore(User client, Parking parking, Float totalRes) {
//        ScoreSystem scoreSystem = scoreSystemRepository.findById(new ScoreSystemId(client, parking)).get();
//
//        while (totalRes < scoreSystem.getTargetValue()) {
//            totalRes -= scoreSystem.getTargetValue();
//            scoreSystem.setScorePoints(scoreSystem.getScorePoints() + 1);
//        }
//
//        scoreSystemRepository.save(scoreSystem);
//    }

    @Transactional
    @Modifying
    public float applyDiscount(User client, ParkingSpace parkingSpace, float totalRes) {
        ScoreSystem scoreSystem = scoreSystemRepository.findById(new ScoreSystemId(client, parkingSpace.getParkingSpaceId().getParking())).get();
        
        if(scoreSystem.getScorePoints() < scoreSystem.getTargetPoints()) return totalRes;

        int points = scoreSystem.getScorePoints();

        float rate = rateRepository.getHourCostByParkingSpace(
            parkingSpace.getParkingSpaceId().getParking().getParkingId().getIdParking(),
            parkingSpace.getParkingSpaceId().getParking().getParkingId().getCity().getIdCity(),
            parkingSpace.getVehicleType().getIdVehicleType(),
            parkingSpace.isUncovered()
        );

        while (scoreSystem.getScorePoints() < scoreSystem.getTargetPoints()){
            scoreSystem.setResidue(scoreSystem.getResidue() - scoreSystem.getTargetPoints());
            points ++;
        }

        scoreSystemRepository.save(scoreSystem);

        float discount = points * rate;

        return totalRes - discount;
    }
}
