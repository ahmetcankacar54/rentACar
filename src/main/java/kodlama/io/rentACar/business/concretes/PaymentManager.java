package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.Common.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.adapters.FakePosServiceAdapter;
import kodlama.io.rentACar.business.abstracts.PaymentService;
import kodlama.io.rentACar.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdatePaymentResponse;
import kodlama.io.rentACar.business.rules.PaymentBusinessRules;
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
    private final PaymentBusinessRules rules;

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
        rules.checkIfPaymentExists(id);
        Payment payment = repository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        rules.checkIfCardExists(request.getCardNumber());
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(0);
        repository.save(payment);
        CreatePaymentResponse response = mapper.map(payment, CreatePaymentResponse.class);

        return response;
    }


    @Override
    public UpdatePaymentResponse update(int id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);
        UpdatePaymentResponse response = mapper.map(payment, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }

    @Override
    public void processRentalPayment(CreateRentalPaymentRequest request) {
        rules.checkIfPaymentIsValid(request);
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        double balance = payment.getBalance();
        double price = request.getPrice();
        rules.checkIfBalanceIsEnough(price, balance);
        posService.pay(); // fake pos service
        payment.setBalance(balance - price);
        repository.save(payment);
    }

}
