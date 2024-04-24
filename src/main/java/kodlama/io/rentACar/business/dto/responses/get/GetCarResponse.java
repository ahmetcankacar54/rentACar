package kodlama.io.rentACar.business.dto.responses.get;

import kodlama.io.rentACar.constants.CarStates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {
    private int id;
    private int modelId;
    private int modelYear;
    private String plate;
    private CarStates state;
    private double dailyPrice;
}
