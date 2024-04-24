package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.abstracts.MaintenanceService;
import kodlama.io.rentACar.business.dto.requests.create.CreateMaintenanceRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateMaintenanceRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateMaintenanceResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllMaintenanceResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetMaintenanceResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateMaintenanceResponse;
import kodlama.io.rentACar.entities.concretes.Maintenance;
import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.repository.abstracts.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapper mapper;
    private final CarService carService;
    @Override
    public List<GetAllMaintenanceResponse> getALl() {
        List<Maintenance> maintenances = repository.findAll();
        List<GetAllMaintenanceResponse> response = maintenances
                .stream()
                .map(maintenance -> mapper.map(maintenance, GetAllMaintenanceResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetMaintenanceResponse getById(int id) {
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(int carId) {
        Maintenance maintenance = repository.findByCarIdAndIsCompletedIsFalse(carId);
        checkMaintenanceIsTrue(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        carService.changeState(carId, State.AVAILABLE);
        GetMaintenanceResponse response = mapper.map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        checkMaintenanceIsFalse(request);
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(0);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        repository.save(maintenance);
        carService.changeState(request.getCarId(), State.MAINTENANCE);
        CreateMaintenanceResponse response = mapper.map(maintenance, CreateMaintenanceResponse.class);

        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(int id, UpdateMaintenanceRequest request) {
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);
        UpdateMaintenanceResponse response = mapper.map(maintenance, UpdateMaintenanceResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    // Business Rules



    private void checkMaintenanceIsFalse(CreateMaintenanceRequest request) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(request.getCarId())) {
            throw new RuntimeException("The car is already in maintenance!");
        }
    }

    private void checkMaintenanceIsTrue(int carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new RuntimeException("The car is already in maintenance!");
        }
    }
}
