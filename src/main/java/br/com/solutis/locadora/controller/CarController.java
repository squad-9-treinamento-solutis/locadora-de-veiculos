package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.CarDto;
import br.com.solutis.locadora.service.AbstractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "CarController")
@Controller
@RequestMapping("/cars")
public class CarController extends AbstractController<CarDto> {
    public CarController(AbstractService<CarDto> service) {
        super(service);
    }
}
