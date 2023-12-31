package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.person.driver.DriverNotFoundException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.exception.rent.cart.CartException;
import br.com.solutis.locadora.exception.rent.cart.CartNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.rent.CartDtoResponse;
import br.com.solutis.locadora.model.dto.rent.CartDto;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.model.entity.rent.Cart;
import br.com.solutis.locadora.model.entity.rent.Rent;
import br.com.solutis.locadora.repository.person.DriverRepository;
import br.com.solutis.locadora.repository.rent.CartRepository;
import br.com.solutis.locadora.repository.rent.RentRepository;
import br.com.solutis.locadora.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final RentRepository rentRepository;
    private final DriverRepository driverRepository;
    private final GenericMapper<CartDto, Cart> modelMapper;
    private final GenericMapper<CartDtoResponse,Cart> modelMapperResponse;

    public CartDtoResponse findById(Long id) {
        LOGGER.info("Finding cart with ID: {}", id);

        Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));

        return modelMapperResponse.mapModelToDto(cart, CartDtoResponse.class);
    }

    public CartDtoResponse findByDriverId(Long driverId) {
        LOGGER.info("Finding cart with driver ID: {}", driverId);

        Cart cart = cartRepository.findByDriverId(driverId);

        if (cart == null) {
            LOGGER.error("Cart with driver ID {} not found.", driverId);
            throw new CartNotFoundException(driverId);
        }

        return modelMapperResponse.mapModelToDto(cart, CartDtoResponse.class);
    }

    public PageResponse<CartDtoResponse> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching carts with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Cart> pagedCarts = cartRepository.findAll(paging);

            List<CartDtoResponse> cartDtosResponse = modelMapperResponse.
                    mapList(pagedCarts.getContent(), CartDtoResponse.class);

            PageResponse<CartDtoResponse> pageResponse = new PageResponse<>();
            pageResponse.setContent(cartDtosResponse);
            pageResponse.setCurrentPage(pagedCarts.getNumber());
            pageResponse.setTotalItems(pagedCarts.getTotalElements());
            pageResponse.setTotalPages(pagedCarts.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching insurance policies: {}", e.getMessage());
            throw new CartException("An error occurred while fetching cart.", e);
        }
    }

    public CartDtoResponse add(CartDto payload) {
        try {
            LOGGER.info("Adding a new cart: {}", payload);

            Cart cart = cartRepository
                    .save(modelMapper.mapDtoToModel(payload, Cart.class));

            return modelMapperResponse.mapModelToDto(cart, CartDtoResponse.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while adding a new cart: {}", e.getMessage());
            throw new CartException("An error occurred while adding a new cart.", e);
        }
    }

    public CartDto update(CartDto payload) {
        try {
            LOGGER.info("Updating cart: {}", payload);

            Cart driverCart = cartRepository.findByDriverId(payload.getDriverId());
            if (driverCart.isDeleted()) throw new CartNotFoundException(driverCart.getId());

            Cart cart = cartRepository.save(driverCart);

            return modelMapper.mapModelToDto(cart, CartDto.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while updating cart: {}", e.getMessage());
            throw new CartException("An error occurred while updating cart.", e);
        }
    }

    public void deleteById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();

        try {
            LOGGER.info("Soft deleting cart with ID: {}", id);

            cart.setDeleted(true);

            cartRepository.save(cart);
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting cart: {}", e.getMessage());
            throw new CartException("An error occurred while deleting cart.", e);
        }
    }

    public CartDto addByDriverId(long driverId) {
        try {
            LOGGER.info("Adding cart with driver ID: {}", driverId);

            Driver driver = getDriverById(driverId);

            Cart cart = new Cart();
            cart.setDriver(driver);

            Cart savedCart = cartRepository.save(cart);

            return modelMapper.mapModelToDto(savedCart, CartDto.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while adding cart: {}", e.getMessage());
            throw new CartException("An error occurred while adding cart.", e);
        }
    }

    public void deleteByDriverId(long driverId) {
        try {
            LOGGER.info("Deleting cart with driver ID: {}", driverId);

            Cart cart = cartRepository.findByDriverId(driverId);

            if (cart != null) {
                cart.setDeleted(true);

                cartRepository.save(cart);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting cart: {}", e.getMessage());
            throw new CartException("An error occurred while deleting cart.", e);
        }
    }

    public Rent findRentInCartByDriverIdAndRentId(long driverId, long rentId) {
        try {
            LOGGER.info("Finding rent with ID {} in cart with driver ID: {}", rentId, driverId);

            Cart cart = cartRepository.findByDriverId(driverId);
            Rent rent = getRentById(rentId);

            if (cart.getRents().contains(rent)) {
                return rent;
            }

            throw new RentNotFoundException(rentId);
        } catch (Exception e) {
            LOGGER.error("An error occurred while finding rent in cart: {}", e.getMessage());
            throw new CartException("An error occurred while finding rent in cart.", e);
        }
    }

    public CartDto addRentToCartByDriverId(long driverId, long rentId) {
        try {
            LOGGER.info("Adding rent with ID {} to cart with driver ID: {}", rentId, driverId);

            Cart cart = cartRepository.findByDriverId(driverId);
            Rent rent = getRentById(rentId);
            cart.getRents().add(rent);

            Cart updatedCart = cartRepository.save(cart);

            return modelMapper.mapModelToDto(updatedCart, CartDto.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while adding rent to cart: {}", e.getMessage());
            throw new CartException("An error occurred while adding rent to cart.", e);
        }
    }

    public CartDtoResponse removeRentFromCartByDriverId(long driverId, long rentId) {
        try {
            LOGGER.info("Removing rent with ID {} from cart with driver ID: {}", rentId, driverId);

            Cart cart = cartRepository.findByDriverId(driverId);
            Rent rent = getRentById(rentId);
            rent.setCart(null);
            cart.getRents().remove(rent);

            rentRepository.save(rent);
            Cart updatedCart = cartRepository.save(cart);

            return modelMapperResponse.mapModelToDto(updatedCart, CartDtoResponse.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while removing rent from cart: {}", e.getMessage());
            throw new CartException("An error occurred while removing rent from cart.", e);
        }
    }

    private Driver getDriverById(long driverId) {
        return driverRepository.findById(driverId).orElseThrow(() -> {
            LOGGER.error("Driver with ID {} not found.", driverId);
            return new DriverNotFoundException(driverId);
        });
    }

    private Rent getRentById(long rentId) {
        return rentRepository.findById(rentId).orElseThrow(() -> {
            LOGGER.error("Rent with ID {} not found.", rentId);
            return new RentNotFoundException(rentId);
        });
    }
}
