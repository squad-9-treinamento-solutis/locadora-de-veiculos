package br.com.solutis.locadora.controller.rent;

import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.model.dto.rent.RentDto;
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
            summary = "Adicionar um novo aluguel",
            description = "Retorna as informações do aluguel adicionado"
    )
    @PostMapping
    public ResponseEntity<?> add(@RequestBody RentDto payload) {

        return new ResponseEntity<>(rentService.add(payload), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Finaliza o aluguel",
            description = "Retorna as informações do aluguel finalizado"
    )
    @PostMapping("/{id}/finish")
    public ResponseEntity<?> finishRental(@PathVariable Long id) {
        try {
            RentDto finishedRent = rentService.finishRental(id);
            return new ResponseEntity<>(rentService.add(finishedRent), HttpStatus.OK);
        } catch (RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}