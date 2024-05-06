package kodlama.io.rentACar.business.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotBlank(message = "Card Number cannot be empty!")
    @Length(min = 16, max = 16, message = "Card number must be 16 digits!")
    private String cardNumber;

    @NotBlank(message = "The card holder information cannot be empty!")
    @Length(min = 5)
    private String cardHolder;

    @Min(value = 2023, message = "The expiration date cannot be older then 2024!")
    private int cardExpirationYear;

    @Min(1)
    @Max(12)
    private int cardExpirationMonth;

    @NotBlank(message = "Card CVV cannot be empty!")
    @Length(min = 3, max = 3, message = "Card CVV must be 3 digits!")
    private String cardCvv;
}
