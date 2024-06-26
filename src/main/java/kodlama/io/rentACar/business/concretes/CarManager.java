package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.dto.requests.create.CreateCarRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateCarResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllCarsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateCarResponse;
import kodlama.io.rentACar.business.rules.CarBusinessRules;
import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.entities.concretes.Car;
import kodlama.io.rentACar.repository.abstracts.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private final CarRepository repository;
    private final ModelMapper mapper;
    private final CarBusinessRules rules;

    @Override
    public List<GetAllCarsResponse> getAll(boolean includeMaintenance) {
        List<Car> cars = filterCarsByMaintenanceState(includeMaintenance);

        List<GetAllCarsResponse> response = cars
                .stream()
                .map(car -> mapper.map(car, GetAllCarsResponse.class))
                .toList();

        return response;

    }

    @Override
    public GetCarResponse getById(int id) {
        rules.checkIfCarExistById(id);
        Car car = repository.findById(id).orElseThrow();
        GetCarResponse response = mapper.map(car, GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        rules.checkIfCarExistsByPlate(request.getPlate());
        Car car = mapper.map(request, Car.class);
        car.setId(0);
        car.setState(State.AVAILABLE);
        Car createdCar = repository.save(car);

        CreateCarResponse response = mapper.map(createdCar, CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {
        rules.checkIfCarExistById(id);
        rules.checkIfCarExistsByPlate(request.getPlate());
        Car car = repository.findById(id).orElseThrow();

        car = mapper.map(request, Car.class);
        car.setId(id);
        Car updatedCar = repository.save(car);
        UpdateCarResponse response = mapper.map(updatedCar, UpdateCarResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfCarExistById(id);
        repository.deleteById(id);
    }

    @Override
    public void changeState(int id, State state) {
        Car car = repository.findById(id).orElseThrow();
        car.setState(state);
        repository.save(car);
    }


    public List<Car> filterCarsByMaintenanceState(boolean includeMaintenance) {
        if (includeMaintenance) {
            return repository.findAll();
        }
        return repository.findAllByStateIsNot(State.MAINTENANCE);
    }

}
