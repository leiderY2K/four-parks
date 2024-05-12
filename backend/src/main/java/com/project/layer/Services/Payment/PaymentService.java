package com.project.layer.Services.Payment;

import com.project.layer.Controllers.Requests.StripeChargeRequest;
import com.project.layer.Controllers.Responses.StripeTokenResponse;
import com.project.layer.Persistence.Entity.Card;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.ICardRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import com.stripe.exception.StripeException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ICardRepository cardRepository;
    //private final IUserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    @Value("${stripe.key.secret}")
    String stripeSecret;

    @Value("${stripe.key.public}")
    static String stripePublic;

    public StripeTokenResponse createCardToken(String userId) {
        // Obtener la tarjeta asociada al usuario
        Optional<Card> cardOptional = cardRepository.findByUserId(userId);
        //Optional<User>  userOptional = userRepository.findByFirstName(userId);

        // Verificar si la tarjeta existe en el Optional
        if (cardOptional.isPresent()) {
            // Obtener la tarjeta del Optional
            Card card = cardOptional.get();
            //User user = userOptional.get();

            // Crear un objeto StripeTokenResponse con los datos de la tarjeta
            StripeTokenResponse request = new StripeTokenResponse();
            request.setCardNumber(card.getSerialCard());
            request.setCvc(card.getCvvCard());
            String expDate = String.valueOf(card.getExpDateCard());
            request.setCardNumber(card.getSerialCard());
            request.setCvc(card.getCvvCard());
            //request.setUsername(user.getFirstName() + " " + user.getLastName());

            String [] parts = expDate.split("-");
            String expYear = parts[1];
            String expMonth = parts[0];

            request.setExpMonth(expYear);
            request.setExpYear(expMonth);

            try {
                Stripe.apiKey = "pk_test_51PDu1i03fxs5UpEOdwy7zscRtuASLC2UR4ydnSA7fbQVhkSBJmU98sc7DuVBljvcqloq2fdPK4T4QdGkI7bzJjJ400FEXUCUko";

                TokenCreateParams params =
                        TokenCreateParams.builder()
                                .setCard(
                                        TokenCreateParams.Card.builder()
                                                .setNumber(request.getCardNumber())
                                                .setExpMonth(request.getExpMonth())
                                                .setExpYear(request.getExpYear())
                                                .setCvc(request.getCvc())
                                                .setName(request.getUsername())
                                                .build()
                                )
                                .build();
                Token token = Token.create(params);
                if (token != null && token.getId() != null) {
                    request.setSuccess(true);
                    request.setToken(token.getId());
                    return request;
                }
            } catch (StripeException e) {
                log.error("StripeService (createCardToken)", e);
                throw new RuntimeException(e.getMessage());
            }
        } else {
            // Manejar el caso donde no se encuentra ninguna tarjeta asociada al usuario
        }
        return null;
    }

    private static String getIntOrDefault(String value, int defaultValue) {
        try {
            return String.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return String.valueOf(defaultValue);
        }
    }

    public StripeChargeRequest charge(StripeChargeRequest chargeRequest) {
        Stripe.apiKey = stripeSecret;

        try {
            chargeRequest.setSuccess(false);
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", "COP");
            chargeParams.put("description", "Payment for id" + chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG", ""));
            chargeParams.put("source", chargeRequest.getStripeToken());
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("id", chargeRequest.getChargeId());
            metadata.putAll(chargeRequest.getAdditionalInfo());
            chargeParams.put("metadata", metadata);
            Charge charge = Charge.create(chargeParams);
            chargeRequest.setMessage(charge.getOutcome().getSellerMessage());

            if (charge.getPaid()) {
                chargeRequest.setChargeId(charge.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;
        } catch (StripeException e) {
            log.error("StripeService (createCardToken)", e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
