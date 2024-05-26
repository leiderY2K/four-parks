package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.ClientScore;
import com.project.layer.Persistence.Entity.ClientScoreId;


@Repository
public interface IClientScoreRepository extends JpaRepository<ClientScore,ClientScoreId>{

 
    
}
