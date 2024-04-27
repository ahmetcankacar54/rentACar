package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllRentalsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateRentalResponse;

import java.util.List;

public interface RentalService {
    List<GetAllRentalsResponse> getAll();
    GetRentalResponse getById(int id);
    CreateRentalResponse add(CreateRentalRequest request);
    UpdateRentalResponse update(int id, UpdateRentalRequest request);
    void delete(int id);
}
