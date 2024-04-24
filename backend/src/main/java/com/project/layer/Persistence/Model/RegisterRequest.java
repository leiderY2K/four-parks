package com.project.layer.Persistence.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    String idUser;
    String idDocTypte;
    String user;
    String password;
    String firtsname;
    String lastname;
}
