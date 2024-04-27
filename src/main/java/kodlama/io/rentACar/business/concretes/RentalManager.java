package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.Core.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.abstracts.PaymentService;
import kodlama.io.rentACar.business.abstracts.RentalService;
import kodlama.io.rentACar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllRentalsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateRentalResponse;
import kodlama.io.rentACar.entities.concretes.Rental;
import kodlama.io.rentACar.entities.enums.State;
import kodlama.io.rentACar.repository.abstracts.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapper mapper;
    private final CarService carService;
    private final PaymentService paymentService;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = repository.findAll();
        List<GetAllRentalsResponse> response = rentals
                .stream()
                .map(rental -> mapper.map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(int id) {
        checkIfRentalExistsById(id);
        Rental rental = repository.findById(id).orElseThrow();
        GetRentalResponse response = mapper.map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        checkIfCarAvailable(request.getCarId());
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(0);
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setStartDate(LocalDateTime.now());

        CreateRentalPaymentRequest paymentRequest = new CreateRentalPaymentRequest();
        mapper.map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(getTotalPrice(rental));
        paymentService.processRentalPayment(paymentRequest);

        repository.save(rental);
        carService.changeState(request.getCarId(), State.RENTED);
        CreateRentalResponse response = mapper.map(rental, CreateRentalResponse.class);

        return response;
    }


    @Override
    public UpdateRentalResponse update(int id, UpdateRentalRequest request) {
        checkIfRentalExistsById(id);
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(id);
        rental.setTotalPrice(getTotalPrice(rental));
        repository.save(rental);
        UpdateRentalResponse response = mapper.map(rental, UpdateRentalResponse.class);

        return response;
    }


    @Override
    public void delete(int id) {
        checkIfRentalExistsById(id);
        int carId = repository.findById(id).get().getCar().getId();
        carService.changeState(carId, State.AVAILABLE);
        repository.deleteById(id);
    }

    // Business Rules


    private static double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }


    private void checkIfRentalExistsById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("There is no Rental with that id!");
        }
    }

    private void checkIfCarAvailable(int carId) {
        if (!carService.getById(carId).getState().equals(State.AVAILABLE)) {
            throw new RuntimeException("The car is not available!");
        }
    }
}
