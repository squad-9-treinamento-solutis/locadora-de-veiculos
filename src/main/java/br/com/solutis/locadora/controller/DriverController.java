package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverController extends AbstractController<DriverDto>{
    public DriverController(CrudService<DriverDto> service) {
        super(service);
    }
}
