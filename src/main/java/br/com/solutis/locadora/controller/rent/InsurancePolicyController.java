package br.com.solutis.locadora.controller.rent;

import br.com.solutis.locadora.exception.rent.InsurancePolicyException;
import br.com.solutis.locadora.exception.rent.InsurancePolicyNotFoundException;
import br.com.solutis.locadora.model.dto.rent.InsurancePolicyDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.rent.InsurancePolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "InsurancePolicyController")
@RequiredArgsConstructor
@RestController
@RequestMapping("/insurances")
@CrossOrigin
public class InsurancePolicyController {
    private final InsurancePolicyService insurancePolicyService;

    @Operation(
            summary = "Find by id",
            description = "Returns the information of a insurance policy by id",
            tags = {"id", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(insurancePolicyService.findById(id), HttpStatus.OK);
        } catch (InsurancePolicyNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InsurancePolicyException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Find all",
            description = "Returns the information of all insurance policies",
            tags = {"all", "get"})
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(insurancePolicyService.findAll(), HttpStatus.OK);
        } catch (InsurancePolicyException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Add a new insurance policy",
            description = "Returns the information of the insurance policy added",
            tags = {"add", "post"})
    @PostMapping
    public ResponseEntity<?> add(@RequestBody InsurancePolicyDto payload) {
        try {
            return new ResponseEntity<>(insurancePolicyService.add(payload), HttpStatus.CREATED);
        } catch (InsurancePolicyException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update a insurance policy",
            description = "Returns the information of the insurance policy updated",
            tags = {"update", "put"})
    @PutMapping
    public ResponseEntity<?> update(@RequestBody InsurancePolicyDto payload) {
        try {
            return new ResponseEntity<>(insurancePolicyService.update(payload), HttpStatus.NO_CONTENT);
        } catch (InsurancePolicyException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a insurance policy",
            description = "Delete a insurance policy by id",
            tags = {"id", "delete"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            insurancePolicyService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InsurancePolicyException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
