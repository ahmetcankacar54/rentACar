package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.Common.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.adapters.FakePosServiceAdapter;
import kodlama.io.rentACar.business.abstracts.PaymentService;
import kodlama.io.rentACar.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdatePaymentResponse;
import kodlama.io.rentACar.entities.concretes.Payment;
import kodlama.io.rentACar.repository.abstracts.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapper mapper;
    private final FakePosServiceAdapter posService;
    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = repository.findAll();
        List<GetAllPaymentsResponse> response = payments
                .stream()
                .map(payment -> mapper.map(payment, GetAllPaymentsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetPaymentResponse getById(int id) {
        checkIfPaymentExists(id);
        Payment payment = repository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        checkIfCardExists(request);
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(0);
        repository.save(payment);
        CreatePaymentResponse response = mapper.map(payment, CreatePaymentResponse.class);

        return response;
    }


    @Override
    public UpdatePaymentResponse update(int id, UpdatePaymentRequest request) {
        checkIfPaymentExists(id);
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);
        UpdatePaymentResponse response = mapper.map(payment, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        checkIfPaymentExists(id);
        repository.deleteById(id);
    }

    @Override
    public void processRentalPayment(CreateRentalPaymentRequest request) {
        checkIfPaymentIsValid(request);
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        double balance = payment.getBalance();
        double price = request.getPrice();
        checkIfBalanceIsEnough(price, balance);
        posService.pay(); // fake pos service
        payment.setBalance(balance - price);
        repository.save(payment);
    }


    // Business Rules


    private void checkIfPaymentExists(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment cannot be found!");
        }
    }

    private void checkIfCardExists(CreatePaymentRequest request) {
        if (repository.existsByCardNumber(request.getCardNumber())) {
            throw new RuntimeException("The card number is already exist!");
        }
    }

    private void checkIfBalanceIsEnough(double price, double balance) {
        if (balance < price) {
            throw new RuntimeException("Insufficient balance!");
        }
    }

    private void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new RuntimeException("Card information is wrong!");
        }
    }
}
