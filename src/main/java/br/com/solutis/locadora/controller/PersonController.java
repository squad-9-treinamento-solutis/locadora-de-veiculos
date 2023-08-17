package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.PersonDto;
import br.com.solutis.locadora.service.AbstractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "PersonController")
@Controller
@RequestMapping("/persons")
public class PersonController extends AbstractController<PersonDto> {
    public PersonController(AbstractService<PersonDto> service) {
        super(service);
    }
}
