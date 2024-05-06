package kodlama.io.rentACar.business.dto.requests.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kodlama.io.rentACar.Common.constants.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
    @Min(0)
    private int modelId;
    @NotNull
    @Min(1996)
    @Max(2024)
    private int modelYear;
    @Pattern(regexp = Regex.Plate, message = "Plate number must match the pattern!")
    private String plate;
    @NotNull
    @Min(1)
    @Max(100000)
    private double dailyPrice;
}
