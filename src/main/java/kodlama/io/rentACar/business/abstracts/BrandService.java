package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetBrandResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateBrandResponse;
import kodlama.io.rentACar.entities.concretes.Brand;

import java.util.List;

public interface BrandService {
    // CRUD Operations

    List<GetAllBrandsResponse> getALl();
    GetBrandResponse getById(int id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(int id, UpdateBrandRequest request);
    void delete(int id);
}
