package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.InsurancePolicyDto;
import br.com.solutis.locadora.service.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "InsurancePolicyController")
@RestController
@RequestMapping("/insurances")
public class InsurancePolicyController extends AbstractController<InsurancePolicyDto>{

    public InsurancePolicyController(CrudService<InsurancePolicyDto> service) {
        super(service);
    }

}
