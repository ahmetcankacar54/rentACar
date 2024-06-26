package kodlama.io.rentACar.api.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetBrandResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandsController {

    private final BrandService service;

    @GetMapping
    public List<GetAllBrandsResponse> getAll() {
        return service.getALl();
    }

    @GetMapping("/{id}")
    public GetBrandResponse getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest request) {
        return service.add(request);
    }

    @PutMapping("{id}")
    public UpdateBrandResponse update(@PathVariable int id, @RequestBody UpdateBrandRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
