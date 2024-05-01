package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.repository.abstracts.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    public void checkIfRentalExistsById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("There is no Rental with that id!");
        }
    }

    public void checkIfCarAvailable(State state) {
        if (!state.equals(State.AVAILABLE)) {
            throw new RuntimeException("The car is not available!");
        }
    }
}
