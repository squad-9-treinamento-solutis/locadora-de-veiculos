package br.com.solutis.locadora.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DriverController")
@RestController
@RequestMapping("/drivers")
public class DriverController {
}
