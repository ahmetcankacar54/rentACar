package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.entities.concretes.Brand;

import java.util.List;

public interface BrandService {
    // CRUD Operations

    List<Brand> getALl();
    Brand getById(int id);
    Brand add(Brand brand);
    Brand update(int id, Brand brand);
    void delete(int id);
}
