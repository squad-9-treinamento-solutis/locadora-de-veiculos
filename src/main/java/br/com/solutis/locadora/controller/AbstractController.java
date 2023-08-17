package br.com.solutis.locadora.controller;

import br.com.solutis.locadora.model.dto.AbstractDto;
import br.com.solutis.locadora.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
public abstract class AbstractController<T extends AbstractDto> {

    private final CrudService<T> service;

    @Operation(
            summary = "List by id",
            description = "Returns the information of the object by id",
            tags = {"id", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "List all",
            description = "Returns the information of all objects",
            tags = {"all", "get"})
    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Add a new object",
            description = "Returns the information of the object created",
            tags = {"add", "post"})
    @PostMapping
    public ResponseEntity<T> add(@RequestBody T payload) {
        return new ResponseEntity<>(service.add(payload), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an object",
            description = "Returns the information of the object updated",
            tags = {"update", "put"})
    @PutMapping
    public ResponseEntity<T> update(@RequestBody T payload) {
        return new ResponseEntity<>(service.update(payload), HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Delete by id",
            description = "Delete the object by id",
            tags = {"id", "delete"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
