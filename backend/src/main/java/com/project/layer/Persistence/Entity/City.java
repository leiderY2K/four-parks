package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @Column(name = "IDCITY", nullable = false)
    private String idCity;
    

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "B_TOP", nullable = false)
    private Float bTop;

    @Column(name = "B_BOTTOM", nullable = false)
    private Float bBottom;

    @Column(name = "B_LEFT", nullable = false)
    private Float bLeft;

    @Column(name = "B_RIGHT", nullable = false)
    private Float bRight;
    
    @Column(name = "X_CENTER", nullable = false)
    private String xCenter;
   
    @Column(name = "Y_CENTER", nullable = false)
    private String yCenter;

}
