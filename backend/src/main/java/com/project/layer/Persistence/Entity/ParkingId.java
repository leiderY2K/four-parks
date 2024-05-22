package com.project.layer.Persistence.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class ParkingId implements Serializable{

    @Column(name = "IDPARKING")
    private Integer idParking;

    @ManyToOne
    @JoinColumn(name = "FK_IDCITY", referencedColumnName = "IDCITY")
    private City city;
}