package kodlama.io.rentACar.api.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.PaymentService;
import kodlama.io.rentACar.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentsController {
    private final PaymentService service;

    @GetMapping
    List<GetAllPaymentsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    GetPaymentResponse getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    UpdatePaymentResponse update(@PathVariable int id,@Valid @RequestBody UpdatePaymentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id) {
        service.delete(id);
    }
}
