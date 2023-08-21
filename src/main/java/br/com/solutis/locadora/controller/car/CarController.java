package br.com.solutis.locadora.controller.car;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.dto.car.CarDtoResponse;
import br.com.solutis.locadora.model.entity.car.Accessory;
import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.car.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "CarController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@CrossOrigin
public class CarController {
    private final CarService carService;

    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do carro por id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(carService.findById(id), HttpStatus.OK);
        } catch (CarNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Listar todos",
            description = "Retorna as informações de todos os carros"
    )
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            return new ResponseEntity<>(carService.findAll(page, size), HttpStatus.OK);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo carro",
            description = "Retorna as informações do carro adicionado"
    )
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CarDto payload) {
        try {
            return new ResponseEntity<>(carService.add(payload), HttpStatus.CREATED);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Atualiza um carro",
            description = "Retorna o codigo 204 (No Content)"
    )
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CarDto payload) {
        try {
            return new ResponseEntity<>(carService.update(payload), HttpStatus.NO_CONTENT);
        } catch (CarNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga um carro por id",
            description = "Retorna o codigo 204 (No Content)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            carService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CarNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "lista carros por filtro",
            description = "Retorna os carros de acordo com o filtro"
    )
    @GetMapping("/filtered")
    public ResponseEntity<List<CarDtoResponse>> findCarsByFilters(
            @RequestParam(value = "category", required = false) ModelCategoryEnum category,
            @RequestParam(value = "accessory", required = false) Accessory accessory,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "rented") Boolean rented) {
        List<CarDtoResponse> cars = carService.findCarsByFilters(category, accessory, model, rented);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
