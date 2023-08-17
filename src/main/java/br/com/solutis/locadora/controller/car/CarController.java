package br.com.solutis.locadora.controller.car;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CarController")
@RestController
@RequestMapping("/cars")
public class CarController {
}
