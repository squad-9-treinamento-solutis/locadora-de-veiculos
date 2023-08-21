package br.com.solutis.locadora.controller.rent;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.exception.rent.cart.CartException;
import br.com.solutis.locadora.exception.rent.cart.CartNotFoundException;
import br.com.solutis.locadora.model.dto.rent.CartDto;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.rent.CartService;
import br.com.solutis.locadora.service.rent.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CartController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
@CrossOrigin
public class CartController {
    private final CartService cartService;
    private final RentService rentService;

    @Operation(
            summary = "Lista o carrinho de um motorista",
            description = "Retorna as informações do carrinho do motorista"
    )
    @GetMapping("/{driverId}")
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
            summary = "Lista todos os carrinhos",
            description = "Retorna as informações de todos os carrinhos"
    )
    @GetMapping
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

    @Operation(
            summary = "Criando o aluguel",
            description = "Retorna as informações do carrinho"
    )
    @PostMapping("/{driverId}/rents")
    public ResponseEntity<?> addRent(@PathVariable Long driverId, @RequestBody RentDto payload) {
        try {
            payload.setDriverId(driverId);
            RentDto rentDto = rentService.add(payload);

            cartService.addRentToCartByDriverId(driverId, rentDto.getId());

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
            summary = "Confirma o aluguel do carrinho",
            description = "Retorna as informações do carrinho"
    )
    @PostMapping("/{driverId}/rents/{rentId}/confirm")
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
            summary = "Apaga o aluguel do carrinho",
            description = "Retorna o codigo 204 (No Content)"
    )
    @DeleteMapping("/{driverId}/rents/{rentId}")
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
}
