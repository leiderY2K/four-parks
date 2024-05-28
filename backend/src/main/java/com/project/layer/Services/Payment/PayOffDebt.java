package com.project.layer.Services.Payment;

import com.project.layer.Persistence.Repository.IUserDebtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PayOffDebt extends ElementaryDebt {

    @Autowired
    private final IUserDebtRepository userDebtRepository;

    @Override
    public void debt(Pay pay) {
        if (pay.getTypePay().equals(TypePay.PAYOFF)) {

            // Llamar al método del repositorio para actualizar el registro
            userDebtRepository.updateUserDebtTransactional(0, false, pay.getUser());
            System.out.println("Se ha limpiado la deuda");
        } else {
            System.out.println("No debt was found");
            if (hasNext()) {
                next.debt(pay);
            }
        }
    }
}