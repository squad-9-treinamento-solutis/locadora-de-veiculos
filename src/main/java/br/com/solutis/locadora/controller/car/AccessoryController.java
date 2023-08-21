package br.com.solutis.locadora.controller.car;

import br.com.solutis.locadora.exception.car.accessory.AccessoryException;
import br.com.solutis.locadora.exception.car.accessory.AccessoryNotFoundException;
import br.com.solutis.locadora.model.dto.car.AccessoryDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.car.AccessoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AccessoryController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/accessories")
@CrossOrigin
public class AccessoryController {
    private final AccessoryService accessoryService;

    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do acessório por id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(accessoryService.findById(id), HttpStatus.OK);
        } catch (AccessoryNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (AccessoryException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Listar todos",
            description = "Retorna as informações de todos os acessórios"
    )
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            return new ResponseEntity<>(accessoryService.findAll(page, size), HttpStatus.OK);
        } catch (AccessoryException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo acessório",
            description = "Retorna as informações do acessório adicionado"
    )
    @PostMapping
    public ResponseEntity<?> add(@RequestBody AccessoryDto payload) {
        try {
            return new ResponseEntity<>(accessoryService.add(payload), HttpStatus.CREATED);
        } catch (AccessoryException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Atualizar um acessório",
            description = "Retorna o codigo 204 (No Content)"
    )
    @PutMapping
    public ResponseEntity<?> update(@RequestBody AccessoryDto payload) {
        try {
            return new ResponseEntity<>(accessoryService.update(payload), HttpStatus.NO_CONTENT);
        } catch (AccessoryNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (AccessoryException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apagar um acessório por id",
            description = "Retorna o codigo 204 (No Content)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            accessoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AccessoryNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (AccessoryException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
