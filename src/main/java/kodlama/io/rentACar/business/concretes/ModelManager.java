package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.ModelService;
import kodlama.io.rentACar.business.dto.requests.create.CreateModelRequest;
import kodlama.io.rentACar.business.dto.requests.update.UpdateModelRequest;
import kodlama.io.rentACar.business.dto.responses.create.CreateModelResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetAllModelsResponse;
import kodlama.io.rentACar.business.dto.responses.get.GetModelResponse;
import kodlama.io.rentACar.business.dto.responses.update.UpdateModelResponse;
import kodlama.io.rentACar.entities.concretes.Model;
import kodlama.io.rentACar.repository.abstracts.ModelRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {

    private final ModelRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<GetAllModelsResponse> getAll() {
        List<Model> models = repository.findAll();
        List<GetAllModelsResponse> response = models
                .stream()
                .map(model -> mapper.map(model, GetAllModelsResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetModelResponse getById(int id) {
        checkIfModelExistById(id);
        Model model = repository.findById(id).orElseThrow();
        GetModelResponse response = mapper.map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        checkIfModelExistByName(request);
        Model model = mapper.map(request, Model.class);
        repository.save(model);
        CreateModelResponse response = mapper.map(model, CreateModelResponse.class);
        return response;
    }

    @Override
    public UpdateModelResponse update(int id, UpdateModelRequest request) {
        checkIfModelExistById(id);
        Model model = mapper.map(request, Model.class);
        model.setId(id);
        repository.save(model);

        UpdateModelResponse response = mapper.map(model, UpdateModelResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfModelExistById(id);
        repository.deleteById(id);
    }


    private void checkIfModelExistById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("There is no model with that id");
        }
    }


    private void checkIfModelExistByName(CreateModelRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("There is a model name with the same name you provided!");
        }
    }

}