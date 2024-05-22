package com.project.layer.Controllers;

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
    public ResponseEntity<String> createCardToken(@RequestBody Map<String, String> json) {
        String userId = json.get("userId");
        String response = paymentService.createCardToken(userId);

        return ResponseEntity.ok(response);

    }
//
//    @PostMapping("/charge")
//    @ResponseBody
//    public StripeChargeRequest charge(@RequestBody StripeChargeRequest response){
//
//        return paymentService.charge(response);
//    }

}
