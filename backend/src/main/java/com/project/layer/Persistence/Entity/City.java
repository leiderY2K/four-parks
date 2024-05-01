package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @Column(name = "IDCITY", nullable = false)
    private String idCity;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "B_TOP", nullable = false)
    private String bTop;

    @Column(name = "B_BOTTOM", nullable = false)
    private String bBottom;

    @Column(name = "B_LEFT", nullable = false)
    private String bLeft;

    @Column(name = "B_RIGHT", nullable = false)
    private String bRight;

}
