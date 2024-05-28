package com.project.layer.Services.Payment;

import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserDebtRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class DebtHandlerChain extends ElementaryDebt {

    private ElementaryDebt next;

    @Autowired
    private final IUserDebtRepository userDebtRepository;
    @Autowired
    private final IReservationRepository reservationRepository;

    @Override
    public void debt(Pay pay) {
        CreateDebt cd = new CreateDebt(userDebtRepository);
        setNext(cd);

        totalPayment tp = new totalPayment(userDebtRepository, reservationRepository);
        cd.setNext(tp);

        PayOffDebt pd = new PayOffDebt(userDebtRepository);
        tp.setNext(pd);

        next.debt(pay);
    }
}
