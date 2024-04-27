package kodlama.io.rentACar.api.controllers;

import kodlama.io.rentACar.business.abstracts.RentalService;
import kodlama.io.rentACar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllRentalsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateRentalResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalsController {
    private final RentalService service;

    @GetMapping("/{id}")
    GetRentalResponse getById(@RequestParam int id) {
        return service.getById(id);
    }

    @GetMapping
    List<GetAllRentalsResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateRentalResponse add(@RequestBody CreateRentalRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    UpdateRentalResponse update(@PathVariable int id, @RequestBody UpdateRentalRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id) {
        service.delete(id);
    }
}
