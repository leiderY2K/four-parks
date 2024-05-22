package com.project.layer.Controllers.Responses;

import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserAuthentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    User user;
    UserAuthentication userAuthentication;
    String message;
}
