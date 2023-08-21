package br.com.solutis.locadora.controller.person;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.cart.CartException;
import br.com.solutis.locadora.exception.cart.CartNotFoundException;
import br.com.solutis.locadora.exception.person.DriverException;
import br.com.solutis.locadora.exception.person.DriverNotFoundException;
import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.model.dto.cart.CartDto;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.cart.CartService;
import br.com.solutis.locadora.service.person.DriverService;
import br.com.solutis.locadora.service.rent.RentService;
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
    private final RentService rentService;

    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do motorista por id"
    )
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
            description = "Retorna as informações de todos os motoristas"
    )
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            return new ResponseEntity<>(driverService.findAll(page, size), HttpStatus.OK);
        } catch (DriverException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo motorista",
            description = "Retorna as informações do motorista adicionado"
    )
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
            description = "Retorna o codigo 204 (No Content)"
    )
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
            description = "Retorna o codigo 204 (No Content)"
    )
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

    @Operation(
            summary = "Lista o carrinho de um motorista",
            description = "Retorna as informações do carrinho do motorista"
    )
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

    @Operation(
            summary = "Criando o aluguel",
            description = "Retorna as informações do carrinho"
    )
    @PostMapping("/{driverId}/carts/rents")
    public ResponseEntity<?> addRent(@PathVariable Long driverId, @RequestBody RentDto payload) {
        try {
            payload.setDriverId(driverId);
            RentDto rentDto = rentService.add(payload);

            cartService.addRentToCartByDriverId(driverId, rentDto.getDriverId());

            return new ResponseEntity<>(rentDto, HttpStatus.OK);
        } catch (CarException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CartException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga o aluguel do carrinho",
            description = "Retorna o codigo 204 (No Content)"
    )
    @DeleteMapping("/{driverId}/carts/rents/{rentId}")
    public ResponseEntity<?> deleteRentFromCart(@PathVariable Long driverId, @PathVariable Long rentId) {
        try {
            cartService.removeRentFromCartByDriverId(driverId, rentId);
            rentService.deleteById(rentId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CartException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Confirma o aluguel do carrinho",
            description = "Retorna as informações do carrinho"
    )
    @PostMapping("/{driverId}/carts/rents/{rentId}/confirm")
    public ResponseEntity<?> confirmRentFromCart(@PathVariable Long driverId, @PathVariable Long rentId) {
        try {
            rentService.confirmRent(rentId);
            CartDto cartDto = cartService.removeRentFromCartByDriverId(driverId, rentId);

            return new ResponseEntity<>(cartDto, HttpStatus.OK);
        } catch (RentNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CarException | RentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (CartException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Lista todos os carrinhos",
            description = "Retorna as informações de todos os carrinhos"
    )
    @GetMapping("/carts")
    public ResponseEntity<?> findAllCarts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            return new ResponseEntity<>(cartService.findAll(page, size), HttpStatus.OK);
        } catch (CartException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}