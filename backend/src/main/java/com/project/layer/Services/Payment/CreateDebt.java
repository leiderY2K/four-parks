package com.project.layer.Services.Payment;

import com.project.layer.Persistence.Entity.UserDebt;
import com.project.layer.Persistence.Entity.UserDebtId;
import com.project.layer.Persistence.Repository.IUserDebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class CreateDebt extends ElementaryDebt {

    @Autowired
    private final IUserDebtRepository userDebtRepository;

    @Override
    public void debt(Pay pay) {
        if(pay.getTypePay().equals(TypePay.DEBT)){
            UserDebtId userDebtId = new UserDebtId();
            userDebtId.setClientDebt(pay.getUser());

            UserDebt userDebt = new UserDebt();
            userDebt.setDebt(userDebtId);
            userDebt.setDebtAmount(Math.round(Float.parseFloat(pay.getAmount())));
            userDebt.setOwes(true);
            userDebtRepository.save(userDebt);
            System.out.println("&&&&&&&&&&&&&&& deuda anotada en tabla");

        }else{
            System.out.println("No debt was foun");
            next.debt(pay);
        }

    }
}
