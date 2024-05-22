package com.project.layer.Persistence.Entity;

import java.util.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVATION", nullable = false)
    private Integer idReservation;

    @Column(name = "STARTDATERES", nullable = false)
    private Date startDateRes;

    @Column(name = "STARTTIMERES", nullable = false)
    private Time startTimeRes;

    @Column(name = "ENDDATERES", nullable = false)
    private Date endDateRes;

    @Column(name = "ENDTIMERES", nullable = false)
    private Time endTimeRes;

    @Column(name = "CREATIONDATERES", nullable = false)
    private Date creationDateRes;

    @Column(name = "TOTALRES", nullable = false)
    private float totalRes;

    @Column(name = "LICENSEPLATE", nullable = false)
    private String licensePlate;

    @Column(name = "FK_IDVEHICLETYPE", nullable = false)
    private String vehicleType;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_IDPARKINGSPACE", referencedColumnName = "IDPARKINGSPACE"),
        @JoinColumn(name = "FK_IDPARKING", referencedColumnName = "FK_IDPARKING"),
        @JoinColumn(name = "FK_IDCITY", referencedColumnName = "FK_IDCITY"),
    })
    private ParkingSpace parkingSpace;

    // Relación con la tabla de clientes
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_CLIENT_IDUSER", referencedColumnName = "IDUSER"),
        @JoinColumn(name = "FK_CLIENT_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User client;

    @Column(name = "FK_IDRESSTATUS", nullable = false)
    private String status;

}
