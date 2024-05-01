package kodlama.io.rentACar.business.dto.requests.update;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
    @NotBlank
    private String cardHolder;
    @NotBlank
    private String modelName;
    @NotBlank
    private String brandName;
    @NotBlank
    private String plate;
    @NotNull
    @Min(value = 1999)
    @Max(value = 2024)
    private int modelYear;
    @NotNull
    private double dailyPrice;
    @NotNull
    private double totalPrice;
    @NotNull
    @Min(value = 1 )
    private int rentedForDays;
    private LocalDateTime rentedAt;
}
