package kodlama.io.rentACar.api.controllers;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.entities.concretes.Brand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class BrandsController {

    private final BrandService service;

    @GetMapping
    public List<Brand> getAll() {
        return service.getALl();
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand add(@RequestBody Brand brand) {
        return service.add(brand);
    }

    @PutMapping("{id}")
    public Brand update(@PathVariable int id,@RequestBody Brand brand) {
        return service.update(id, brand);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
