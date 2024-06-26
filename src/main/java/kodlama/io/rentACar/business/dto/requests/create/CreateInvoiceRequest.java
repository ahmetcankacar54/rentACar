package kodlama.io.rentACar.business.dto.requests.create;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import kodlama.io.rentACar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
    @NotBlank
    private String cardHolder;
    @NotBlank
    private String modelName;
    @NotBlank
    private String brandName;
    @NotBlank
    private String plate;
    @Min(value = 1996)
    @Max(value = 2024)
    private int modelYear;
    private double dailyPrice;
    private double totalPrice;
    @Min(value = 1 )
    private int rentedForDays;
    private LocalDateTime rentedAt;
}

