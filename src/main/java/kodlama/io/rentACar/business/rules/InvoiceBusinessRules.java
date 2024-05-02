package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.core.exceptions.BusinessException;
import kodlama.io.rentACar.repository.abstracts.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceBusinessRules {
    private final InvoiceRepository repository;

    public void checkIfInvoiceExists(int id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Invoice.NotFound);
        }
    }
}
