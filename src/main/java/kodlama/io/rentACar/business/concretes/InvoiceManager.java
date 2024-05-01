package kodlama.io.rentACar.business.concretes;


import kodlama.io.rentACar.business.abstracts.InvoiceService;
import kodlama.io.rentACar.business.dto.requests.create.CreateInvoiceRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateInvoiceRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateInvoiceResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllInvoicesResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetInvoiceResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateInvoiceResponse;
import kodlama.io.rentACar.entities.concretes.Invoice;
import kodlama.io.rentACar.repository.abstracts.InvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Getter
@Setter
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();
        List<GetAllInvoicesResponse> response = invoices
                .stream()
                .map(invoice -> mapper.map(invoice, GetAllInvoicesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetInvoiceResponse getById(int id) {
        checkIfInvoiceExists(id);
        Invoice invoice = repository.findById(id).orElseThrow();
        GetInvoiceResponse response = mapper.map(invoice, GetInvoiceResponse.class);
        return response;
    }


    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(0);
        invoice.setTotalPrice(getTotalPrice(invoice));
        repository.save(invoice);
        CreateInvoiceResponse response = mapper.map(invoice, CreateInvoiceResponse.class);

        return response;
    }


    @Override
    public UpdateInvoiceResponse update(int id, UpdateInvoiceRequest request) {
        checkIfInvoiceExists(id);
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(id);
        repository.save(invoice);
        UpdateInvoiceResponse response = mapper.map(invoice, UpdateInvoiceResponse.class);

        return response;
    }

    @Override
    public void delete(int id) {
        checkIfInvoiceExists(id);
        repository.deleteById(id);

    }

    // Business Rules


    private void checkIfInvoiceExists(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Can't find the invoice!");
        }
    }

    private static double getTotalPrice(Invoice invoice) {
        return invoice.getDailyPrice() * invoice.getRentedForDays();
    }

}
