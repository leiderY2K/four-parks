package com.project.layer.Services.Payment;

import com.project.layer.Persistence.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pay {

    private TypePay typePay;
    private User user;
    private String amount;
    private String totalPayment;


    public Pay(TypePay typePay) {
        this.typePay = typePay;
    }
}
