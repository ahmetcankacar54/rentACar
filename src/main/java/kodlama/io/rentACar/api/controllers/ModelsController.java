package kodlama.io.rentACar.api.controllers;

import kodlama.io.rentACar.business.abstracts.ModelService;
import kodlama.io.rentACar.business.dto.requests.create.CreateModelRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateModelRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateModelResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllModelsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetModelResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateModelResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/models")
public class ModelsController {

    private ModelService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetModelResponse getById(@PathVariable int id) {
        GetModelResponse response = service.getById(id);
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllModelsResponse> getAll() {
        List<GetAllModelsResponse> response = service.getAll();
        return response;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateModelResponse add(@RequestBody CreateModelRequest request) {
        CreateModelResponse response = service.add(request);
        return response;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateModelResponse add(@PathVariable int id, @RequestBody UpdateModelRequest request) {
        UpdateModelResponse response = service.update(id, request);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }


}
