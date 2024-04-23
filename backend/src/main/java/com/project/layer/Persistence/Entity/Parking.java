package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parking {
    
    @Id
    @Column(name = "IDPARKING", nullable = false)
    private int idParking;

    @Column(name = "NAMEPARK", nullable = false)
    private String namePark;

    @Column(name = "", nullable = false)
    private int capacity;

    @OneToOne
    @JoinColumn(name = "ADDRESS_IDADDRESS")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "PARKINGTYPE_IDPARKINGTYPE")
    private ParkingType parkingType;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "CITY_IDCITY")
    private City city;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_IDSCHEDULE")
    private Schedule schedule;
}
