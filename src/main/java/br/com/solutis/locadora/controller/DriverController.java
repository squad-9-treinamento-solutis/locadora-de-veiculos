package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.service.AbstractService;

public class DriverController extends AbstractController<DriverDto>{
    public DriverController(AbstractService<DriverDto> service) {
        super(service);
    }
}
