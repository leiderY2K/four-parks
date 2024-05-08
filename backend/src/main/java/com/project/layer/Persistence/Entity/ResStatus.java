package com.project.layer.Persistence.Entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResStatus {
    PENDING("PEN", "Pendiente"),
    CONFIRMED("CON", "Confirmada"),
    CANCELLED("CAN", "Cancelada"),  
    EXPIRED("EXP", "Expirada"),
    IN_PROGRESS("CUR", "En curso"),
    COMPLETED("COM", "Completada"),
    NO_SHOW("NPR", "No presentado");

    private final String id;
    private final String description;

    // Getters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
