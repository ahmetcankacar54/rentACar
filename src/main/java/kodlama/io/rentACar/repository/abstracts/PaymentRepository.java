package kodlama.io.rentACar.repository.abstracts;

import kodlama.io.rentACar.entities.concretes.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
    boolean existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
      String cardNumber,
      String cardHolder,
      int cardExpirationYear,
      int cardExpirationMonth,
      String cardCvv
    );

}
