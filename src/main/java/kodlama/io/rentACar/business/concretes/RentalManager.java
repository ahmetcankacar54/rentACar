package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.Common.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.business.abstracts.*;
import kodlama.io.rentACar.business.dto.requests.create.CreateInvoiceRequest;
import kodlama.io.rentACar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllRentalsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateRentalResponse;
import kodlama.io.rentACar.business.rules.RentalBusinessRules;
import kodlama.io.rentACar.entities.concretes.Car;
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
    private final InvoiceService invoiceService;
    private final RentalBusinessRules rules;

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
        rules.checkIfRentalExistsById(id);
        Rental rental = repository.findById(id).orElseThrow();
        GetRentalResponse response = mapper.map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        rules.checkIfCarAvailable(carService.getById(request.getCarId()).getState());
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(0);
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setStartDate(LocalDateTime.now());

        // Create Payment
        CreateRentalPaymentRequest paymentRequest = new CreateRentalPaymentRequest();
        mapper.map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(getTotalPrice(rental));
        paymentService.processRentalPayment(paymentRequest);

        repository.save(rental);
        carService.changeState(request.getCarId(), State.RENTED);
        CreateRentalResponse response = mapper.map(rental, CreateRentalResponse.class);


        // Create Invoice
        Car car = mapper.map(carService.getById(request.getCarId()), Car.class);
        rental.setCar(car);
        createInvoiceRequest(rental, paymentRequest);


        return response;
    }

    @Override
    public UpdateRentalResponse update(int id, UpdateRentalRequest request) {
        rules.checkIfRentalExistsById(id);
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(id);
        rental.setTotalPrice(getTotalPrice(rental));
        repository.save(rental);
        UpdateRentalResponse response = mapper.map(rental, UpdateRentalResponse.class);

        return response;
    }


    @Override
    public void delete(int id) {
        rules.checkIfRentalExistsById(id);
        int carId = repository.findById(id).get().getCar().getId();
        carService.changeState(carId, State.AVAILABLE);
        repository.deleteById(id);
    }

    private void createInvoiceRequest(Rental rental, CreateRentalPaymentRequest paymentRequest) {
        CreateInvoiceRequest invoiceRequest = new CreateInvoiceRequest();

        invoiceRequest.setCardHolder(paymentRequest.getCardHolder());
        invoiceRequest.setModelName(rental.getCar().getModel().getName());
        invoiceRequest.setBrandName(rental.getCar().getModel().getBrand().getName());
        invoiceRequest.setPlate(rental.getCar().getPlate());
        invoiceRequest.setModelYear(rental.getCar().getModelYear());
        invoiceRequest.setDailyPrice(rental.getDailyPrice());
        invoiceRequest.setRentedForDays(rental.getRentedForDays());
        invoiceRequest.setRentedAt(rental.getStartDate());

        invoiceService.add(invoiceRequest);
    }

    private static double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }
}
