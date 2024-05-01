package com.project.layer.Persistence.Entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVATION", nullable = false)
    private String idReservation;

    @Column(name = "STARTTIMERES", nullable = false)
    private Timestamp startTimeRes;

    @Column(name = "ENDTIMERES", nullable = false)
    private Timestamp endTimeRes;

    @Column(name = "TOTALRES", nullable = false)
    private Integer totalRes;

    @Column(name = "LICENSEPLATE", nullable = false)
    private String licensePlate;

    @Column(name = "RESERVATIONCOL", nullable = false)
    private String reservationCol;

    // Relación con la tabla de clientes
    @ManyToOne
    @JoinColumn(name = "FK_CLIENT_IDUSER", referencedColumnName = "IDUSER", insertable = false, updatable = false)
    @JoinColumn(name = "FK_CLIENT_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE", insertable = false, updatable = false)
    private Client client;

}
