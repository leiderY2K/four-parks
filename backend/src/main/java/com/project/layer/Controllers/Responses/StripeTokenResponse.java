package com.project.layer.Controllers.Responses;


import com.project.layer.Persistence.Entity.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StripeTokenResponse {

    private String cardNumber;
    private String expMonth;
    private String expYear;
    //private String expDateCard;
    private String cvc;
    private String token;
    private String username;
    private boolean success;
    UserId clientId;
}
