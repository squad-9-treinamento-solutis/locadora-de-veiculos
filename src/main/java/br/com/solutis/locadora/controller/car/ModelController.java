package br.com.solutis.locadora.controller.car;

import br.com.solutis.locadora.exception.car.ModelException;
import br.com.solutis.locadora.exception.car.ModelNotFoundException;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.car.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ModelController")
@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
@CrossOrigin
public class ModelController {
    private final ModelService modelService;


    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do modelo do carro por id",
            tags = {"id", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(modelService.findById(id), HttpStatus.OK);
        } catch (ModelNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ModelException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Listar todos os modelos de carros",
            description = "Retorna as informações de todos os modelos de carros",
            tags = {"all", "get", "paginated"})
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            return new ResponseEntity<>(modelService.findAll(page, size), HttpStatus.OK);
        } catch (ModelException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo modelo de carro",
            description = "Retorna as informações do novo modelo de carro adicionado",
            tags = {"add", "post"})
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ModelDto payload) {
        try {
            return new ResponseEntity<>(modelService.add(payload), HttpStatus.CREATED);
        } catch (ModelException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Atualiza um modelo de carro",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"update", "put"})
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ModelDto payload) {
        try {
            return new ResponseEntity<>(modelService.update(payload), HttpStatus.NO_CONTENT);
        } catch (ModelException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga um modelo por id",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"id", "delete"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            modelService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ModelException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
