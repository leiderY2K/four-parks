package com.project.layer.Services.Payment;
import com.project.layer.Persistence.Repository.IReservationRepository;
import com.project.layer.Persistence.Repository.IUserDebtRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class totalPayment extends ElementaryDebt {

    @Autowired
    private final IUserDebtRepository userDebtRepository;
    @Autowired
    private final IReservationRepository reservationRepository;

    @Override
    public void debt(Pay pay) {
        if (pay.getTypePay().equals(TypePay.CURRENT)) {
            String userId = pay.getUser().getUserId().getIdUser();
            String idDocType = pay.getUser().getUserId().getIdDocType();

            List<Integer> userDebts = userDebtRepository.findDebtByUserId(userId, idDocType);
            float reservationAmount = reservationRepository.findReservationAmountByUserId(userId);

            float totalPayment;
            if (!userDebts.isEmpty()) {
                System.out.println("$$$$$$$$$$$$$$el usuario debe$$$$$$$$$$");
                Integer userDebt = userDebts.get(0);
                totalPayment = userDebt + reservationAmount;
            } else {
                // Si no hay deuda, el total de pago es solo el monto de la reservación
                System.out.println("############el usuario no debe#############");
                totalPayment = reservationAmount;
            }

            pay.setTotalPayment(String.valueOf(totalPayment));
        }else {
            System.out.println("se ejecuto, pero nah");
            next.debt(pay);
        }
    }

}
