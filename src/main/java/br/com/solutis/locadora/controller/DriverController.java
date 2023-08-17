package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.service.AbstractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "DriverController")
@Controller
@RequestMapping("/drivers")
public class DriverController extends AbstractController<DriverDto> {
    public DriverController(AbstractService<DriverDto> service) {
        super(service);
    }
}
