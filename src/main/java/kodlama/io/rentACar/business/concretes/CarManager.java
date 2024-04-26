package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.dto.requests.create.CreateCarRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateCarResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllCarResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateCarResponse;
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

    @Override
    public List<GetAllCarResponse> getAll(boolean includeMaintenance) {
        List<Car> cars = filetCarsByMaintenanceState(includeMaintenance);

        List<GetAllCarResponse> response = cars
                .stream()
                .map(car -> mapper.map(car, GetAllCarResponse.class))
                .toList();

        return response;

    }

    @Override
    public GetCarResponse getById(int id) {
        checkIfCarExistById(id);
        Car car = repository.findById(id).orElseThrow();
        GetCarResponse response = mapper.map(car, GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        Car car = mapper.map(request, Car.class);
        car.setId(0);
        car.setState(State.AVAILABLE);
        Car createdCar = repository.save(car);

        CreateCarResponse response = mapper.map(createdCar, CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {
        checkIfCarExistById(id);
        Car car = repository.findById(id).orElseThrow();
        checkIfCarIsValid(car, request);

        car = mapper.map(request, Car.class);
        car.setId(id);
        Car updatedCar = repository.save(car);
        UpdateCarResponse response = mapper.map(updatedCar, UpdateCarResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        checkIfCarExistById(id);
        repository.deleteById(id);
    }

    @Override
    public void changeState(int id, State state) {
        Car car = repository.findById(id).orElseThrow();
        car.setState(state);
        repository.save(car);
    }


    // Business Rules


    private void checkIfCarExistById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("There is no car belong that id!");
        }
    }

    private void checkIfCarIsValid(Car car, UpdateCarRequest request) {
        checkIfCarInMaintenance(car, request);
        checkIfCarIsRented(car, request);
    }

    private static void checkIfCarIsRented(Car car, UpdateCarRequest request) {
        if ((car.getState() == State.RENTED) && (request.getState() == State.MAINTENANCE)) {
            throw new RuntimeException("The Car is currently occupied cannot send to the maintenance!");
        }
    }

    private static void checkIfCarInMaintenance(Car car, UpdateCarRequest request) {
        if (car.getState() == State.MAINTENANCE && request.getState() == State.MAINTENANCE) {
            throw new RuntimeException("The Car is already in maintenance!");
        }
    }

    private List<Car> filetCarsByMaintenanceState(boolean includeMaintenance) {
        if (includeMaintenance) {

            return repository.findAll();
        } else {

            return repository.findAllByStateIsNot(State.MAINTENANCE);
        }
    }
}
