package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.business.dto.requests.create.CreateModelRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateModelRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateModelResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllModelsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetModelResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateModelResponse;

import java.util.List;


public interface ModelService {
    // CRUD Operations

    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(int id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(int id, UpdateModelRequest request);
    void delete(int id);
}
