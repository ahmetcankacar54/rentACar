package kodlama.io.rentACar.repository.abstracts;

import kodlama.io.rentACar.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    // Custom queries

    boolean existsByNameIgnoreCase(String name);
}
