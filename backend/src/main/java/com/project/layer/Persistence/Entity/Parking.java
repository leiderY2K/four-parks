package com.project.layer.Persistence.Entity;

import java.sql.Time;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Parking {
    
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ParkingId parkingId;

    @Column(name = "NAMEPARK", nullable = false)
    private String namePark;

    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @Column(name = "OCUPABILITY", nullable = false)
    private Float ocupability;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_COORDINATESX", referencedColumnName = "COORDINATESX"),
        @JoinColumn(name = "FK_COORDINATESY", referencedColumnName = "COORDINATESY")
    })
    private Address address;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STARTTIME", nullable = false)
    private Time startTime;

    @Column(name = "ENDTIME", nullable = false)
    private Time endTime;
    
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