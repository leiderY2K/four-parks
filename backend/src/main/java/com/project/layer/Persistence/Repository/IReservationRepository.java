package com.project.layer.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.layer.Persistence.Entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer>{

    
}