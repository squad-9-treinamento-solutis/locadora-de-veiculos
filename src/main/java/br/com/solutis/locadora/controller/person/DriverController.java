package br.com.solutis.locadora.controller.person;

import br.com.solutis.locadora.exception.person.DriverException;
import br.com.solutis.locadora.exception.person.DriverNotFoundException;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.person.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DriverController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@CrossOrigin
public class DriverController {

    private final DriverService driverService;
    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do motorista por id",
            tags = {"id", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(driverService.findById(id), HttpStatus.OK);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Listar todos",
            description = "Retorna as informações de todos os motoristas",
            tags = {"all", "get", "paginated"})
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            return new ResponseEntity<>(driverService.findAll(page, size), HttpStatus.OK);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo motorista",
            description = "Retorna as informações do motorista adicionado",
            tags = {"add", "post"})
    @PostMapping
    public ResponseEntity<?> add(@RequestBody DriverDto payload) {
        try {
            return new ResponseEntity<>(driverService.add(payload), HttpStatus.CREATED);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Atualiza um motorista",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"update", "put"})
    @PutMapping
    public ResponseEntity<?> update(@RequestBody DriverDto payload) {
        try {
            return new ResponseEntity<>(driverService.update(payload), HttpStatus.NO_CONTENT);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga um motorista por id",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"id", "delete"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            driverService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}