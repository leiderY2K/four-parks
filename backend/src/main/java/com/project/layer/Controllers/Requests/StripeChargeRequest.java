package com.project.layer.Controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeChargeRequest {
    private String StripeToken;
    private String username;
    private double amount;
    private boolean success;
    private String message;
    private String chargeId;
    private Map<String, Object> additionalInfo = new HashMap<>();
}
