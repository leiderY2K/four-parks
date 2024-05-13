package com.project.layer.Persistence.Entity;

import java.sql.Time;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Parking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPARKING", nullable = false)
    private Integer idParking;

    @Column(name = "NAMEPARK", nullable = false)
    private String namePark;

    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @Column(name = "OCUPABILITY", nullable = false)
    private Float ocupability;

    @Column(name = "FK_COORDINATESX", nullable = false) // Nueva columna para las coordenadas X
    private Float addressCoordinatesX;

    @Column(name = "FK_COORDINATESY", nullable = false) // Nueva columna para las coordenadas Y
    private Float addressCoordinatesY;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STARTTIME", nullable = false)
    private Time startTime;

    @Column(name = "ENDTIME", nullable = false)
    private Time endTime;

    @ManyToOne
    @JoinColumn(name = "FK_IDCITY")
    private City city;
    
    @ManyToOne
    @JoinColumn(name = "FK_IDSCHEDULE")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "FK_IDPARKINGTYPE")
    private ParkingType parkingType;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "FK_ADMIN_IDUSER", referencedColumnName = "IDUSER"),
            @JoinColumn(name = "FK_ADMIN_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User admin;

}
