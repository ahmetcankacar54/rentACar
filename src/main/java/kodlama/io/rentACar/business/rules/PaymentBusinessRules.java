package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.Common.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.repository.abstracts.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExists(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException(Messages.Payment.NotFound);
        }
    }

    public void checkIfCardExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException(Messages.Payment.CardNumberAlreadyExists);
        }
    }

    public void checkIfBalanceIsEnough(double price, double balance) {
        if (balance < price) {
            throw new RuntimeException(Messages.Payment.InsufficientBalance);
        }
    }

    public void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new RuntimeException(Messages.Payment.NotValid);
        }
    }
}
