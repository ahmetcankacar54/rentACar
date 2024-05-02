package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.core.exceptions.BusinessException;
import kodlama.io.rentACar.repository.abstracts.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelBusinessRules {
    private final ModelRepository repository;

    public void checkIfModelExistById(int id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Model.NotExists);
        }
    }

    public
    void checkIfModelExistByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Messages.Model.Exists);
        }
    }
}
