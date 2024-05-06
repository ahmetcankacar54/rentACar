package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.core.exceptions.BusinessException;
import kodlama.io.rentACar.entities.concretes.Car;
import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.repository.abstracts.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;
    public void checkIfCarExistById(int id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Car.NotExists);
        }
    }

    public void checkIfCarExistsByPlate(String plate) {
        if (repository.existsByPlate(plate)) {
            throw new BusinessException(Messages.Car.PlateExists);
        }
    }

}
