package br.com.solutis.locadora.controller.car;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CarController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@CrossOrigin
public class CarController {
}
