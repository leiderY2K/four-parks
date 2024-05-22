package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ScoreSystem;
import com.project.layer.Persistence.Entity.ScoreSystemId;


@Repository
public interface IScoreSystemRepository extends JpaRepository<ScoreSystem,ScoreSystemId>{
    
}
