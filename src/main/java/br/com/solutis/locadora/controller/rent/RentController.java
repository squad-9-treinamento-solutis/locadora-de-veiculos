package br.com.solutis.locadora.controller.rent;

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
@RestController
@RequiredArgsConstructor
@RequestMapping("/rents")
@CrossOrigin
public class RentController {
    private final RentService rentService;

    @Operation(
            summary = "Finalizando o aluguel - Devolução do carro",
            description = "Retorna as informações do aluguel"
    )
    @PostMapping("/{driverId}/rents/{rentId}/finish")
    public ResponseEntity<?> finishRent(
            @PathVariable Long driverId,
            @PathVariable Long rentId
    ) {
        try {
            return new ResponseEntity<>(rentService.finishRent(driverId, rentId), HttpStatus.OK);
        } catch (RentNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(
            summary = "Listando o alugueis finalizados",
            description = "Retorna as informações dos alugueis finalizados"
    )
    @GetMapping("/finished")
    public ResponseEntity<?> finishRent() {
        try {
            return new ResponseEntity<>(rentService.findFinishedRents(), HttpStatus.OK);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Listando o alugueis ativos",
            description = "Retorna as informações dos alugueis ativos"
    )
    @GetMapping("/active")
    public ResponseEntity<?> findActiveRents() {
        try {
            return new ResponseEntity<>(rentService.findActiveRents(), HttpStatus.OK);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
