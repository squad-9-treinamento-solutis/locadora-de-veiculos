package br.com.solutis.locadora.controller.rent;

import br.com.solutis.locadora.exception.rent.InsurancePolicyException;
import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.rent.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "RentController")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rents")
@CrossOrigin
public class RentController {
    private final RentService rentService;

    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do aluguel por id",
            tags = {"id", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(rentService.findById(id), HttpStatus.OK);
        } catch (RentNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga um aluguel por id",
            description = "Retorna o codigo 204 (No Content)",
            tags = {"id", "delete"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            rentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
