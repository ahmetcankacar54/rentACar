package kodlama.io.rentACar.repository.abstracts;

import kodlama.io.rentACar.entities.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
