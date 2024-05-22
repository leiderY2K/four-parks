package com.project.layer.Controllers.Requests;

import com.project.layer.Persistence.Entity.UserId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private UserId userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String username;

    private String password;

    private int attempts;

    private boolean isBlocked;

    private String role;
}
