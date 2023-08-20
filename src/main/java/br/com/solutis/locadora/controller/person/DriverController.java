package br.com.solutis.locadora.controller.person;


import br.com.solutis.locadora.exception.cart.CartException;
import br.com.solutis.locadora.exception.cart.CartNotFoundException;
import br.com.solutis.locadora.exception.person.DriverException;
import br.com.solutis.locadora.exception.person.DriverNotFoundException;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.cart.CartService;
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
    private final CartService cartService;

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
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
            DriverDto driverDto = driverService.add(payload);

            cartService.addByDriverId(driverDto.getId());

            return new ResponseEntity<>(driverDto, HttpStatus.CREATED);
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
            cartService.deleteByDriverId(id);

            driverService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DriverNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{driverId}/carts")
    public ResponseEntity<?> findCartByDriverId(@PathVariable Long driverId) {
        try {
            return new ResponseEntity<>(cartService.findByDriverId(driverId), HttpStatus.OK);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CartException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}