package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.entities.concretes.Brand;
import kodlama.io.rentACar.repository.abstracts.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private final BrandRepository repository;
    @Override
    public List<Brand> getALl() {
        return repository.findAll();
    }

    @Override
    public Brand getById(int id) {
        checkIfBrandExist(id);
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Brand add(Brand brand) {
        return repository.save(brand);
    }

    @Override
    public Brand update(int id, Brand brand) {
        checkIfBrandExist(id);
        brand.setId(id);
        return repository.save(brand);
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
