package com.project.layer.Controllers;

import com.project.layer.Controllers.Requests.StripeChargeRequest;
import com.project.layer.Controllers.Responses.StripeTokenResponse;
import com.project.layer.Services.Payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/card/token")
    public ResponseEntity<StripeTokenResponse> createCardToken(@RequestBody Map<String, String> json){
        String userId = json.get("userId");
        StripeTokenResponse response = paymentService.createCardToken(userId);
        if (response != null && response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeRequest charge(@RequestBody StripeChargeRequest response){

        return paymentService.charge(response);
    }
}
