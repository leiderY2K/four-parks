package com.project.layer.Persistence.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMERACTION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAction {
    @Id
    @Column(name = "IDACTION", nullable = false)
    private int idAction;

    @Column(name = "DESCACTION", nullable = false)
    private String descAction;

    @Column(name = "DATEACTION", nullable = false)
    private Date dateAction;

    @Column(name = "IPUSER", nullable = false)
    private String ipUser;



    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "FK_IDUSER", referencedColumnName = "IDUSER"),
            @JoinColumn(name = "FK_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User userActionId;

}