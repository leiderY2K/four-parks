package com.project.layer.Persistence.Entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserId implements Serializable {

    private String idUser;
    
    private String idDocTypeFk;
    
}
