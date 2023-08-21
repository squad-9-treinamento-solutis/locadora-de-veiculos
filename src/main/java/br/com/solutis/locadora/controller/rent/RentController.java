package br.com.solutis.locadora.controller.rent;

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
            summary = "Adicionar um seguro",
            description = "Retorna as informações do seguro adicionado",
            tags = {"add", "post"})
    @PostMapping
    public ResponseEntity<?> add(@RequestBody RentDto payload) {
        try {
            return new ResponseEntity<>(rentService.add(payload), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}