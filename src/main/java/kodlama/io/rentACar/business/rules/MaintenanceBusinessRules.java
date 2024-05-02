package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.repository.abstracts.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarService carService;

    public void checkIfMaintenanceExists(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException(Messages.Maintenance.NotExists);
        }
    }

    public void checkIfCarIsNotUnderMaintenance(int carId) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new RuntimeException(Messages.Maintenance.CarNotExists);
        }
    }

    public void checkIfCarIsUnderMaintenance(int carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new RuntimeException(Messages.Maintenance.CarExists);
        }
    }

    public void checkCarAvailabilityForMaintenance(int carId) {
        if (carService.getById(carId).getState().equals(State.RENTED)) {
            throw new RuntimeException(Messages.Maintenance.CarIsRented);
        }
    }

}
