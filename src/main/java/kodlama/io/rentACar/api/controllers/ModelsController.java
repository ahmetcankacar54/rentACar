package kodlama.io.rentACar.api.controllers;

import kodlama.io.rentACar.business.abstracts.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/models")
public class ModelsController {

    private ModelService service;


}
