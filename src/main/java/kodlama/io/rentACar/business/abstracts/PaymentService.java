package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.Core.dto.CreateRentalPaymentRequest;
import kodlama.io.rentACar.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdatePaymentResponse;

import java.util.List;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetPaymentResponse getById(int id);
    CreatePaymentResponse add(CreatePaymentRequest request);
    UpdatePaymentResponse update(int id, UpdatePaymentRequest request);
    void delete(int id);
    void processRentalPayment(CreateRentalPaymentRequest request);
}
