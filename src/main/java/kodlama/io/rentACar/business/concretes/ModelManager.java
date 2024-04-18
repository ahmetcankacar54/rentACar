package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.ModelService;
import kodlama.io.rentACar.entities.concretes.Model;
import kodlama.io.rentACar.repository.abstracts.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {

    private ModelRepository repository;
    @Override
    public Model add(Model model) {
        return repository.save(model);
    }
}
