package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetBrandResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateBrandResponse;
import kodlama.io.rentACar.entities.concretes.Brand;
import kodlama.io.rentACar.repository.abstracts.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private final BrandRepository repository;
    @Override
    public List<GetAllBrandsResponse> getALl() {
        List<Brand> brands = repository.findAll();
        List<GetAllBrandsResponse> response = new ArrayList<>();

        for (Brand brand : brands) {
            response.add(new GetAllBrandsResponse(brand.getId(), brand.getName()));
        }

        return response;
    }

    @Override
    public GetBrandResponse getById(int id) {
        checkIfBrandExist(id);
        Brand brand = repository.findById(id).orElseThrow();

        GetBrandResponse response = new GetBrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());

        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.getName());
        repository.save(brand);

        CreateBrandResponse response = new CreateBrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());

        return response;
    }

    @Override
    public UpdateBrandResponse update(int id, UpdateBrandRequest request) {
        checkIfBrandExist(id);
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(request.getName());
        repository.save(brand);

        UpdateBrandResponse response = new UpdateBrandResponse();
        response.setId(id);
        response.setName(request.getName());
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfBrandExist(id);
        repository.deleteById(id);
    }

    // Business Rules

    private void checkIfBrandExist(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("There is no brand with id : " + id);
    }
}
