package br.com.solutis.locadora.controller.car;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.model.dto.car.CarDto;
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
            description = "Retorna as informações do carro por id",
            tags = {"id", "get"})
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
            description = "Retorna as informações de todos os carros",
            tags = {"all", "get", "paginated"})
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
            description = "Retorna as informações do carro adicionado",
            tags = {"add", "post"})
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
            description = "Retorna o codigo 204 (No Content)",
            tags = {"update", "put"})
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
            description = "Retorna o codigo 204 (No Content)",
            tags = {"id", "delete"})
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
            summary = "lista carros disponiveis por intervalo de data",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"all", "get"})
    @GetMapping("/available")
    public ResponseEntity<List<CarDto>> getAvailableCarsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<CarDto> availableCars = carService.findAvailableCarsByDateRange(startDate, endDate);
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }
}
