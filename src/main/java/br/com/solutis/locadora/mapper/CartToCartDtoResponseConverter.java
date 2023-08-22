package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.entity.rent.Cart;
import org.modelmapper.AbstractConverter;
import br.com.solutis.locadora.model.dto.rent.CartDtoResponse;
public class CartToCartDtoResponseConverter extends AbstractConverter<Cart, CartDtoResponse> {
    @Override
    protected CartDtoResponse convert(Cart cart) {
        CartDtoResponse cartDtoResponse = new CartDtoResponse();

        cartDtoResponse.setId(cart.getId());
        cartDtoResponse.setName(cart.getDriver().getName());
        cartDtoResponse.setCpf(cart.getDriver().getName());
        cartDtoResponse.setRents(cart.getRents());

        return cartDtoResponse;
    }
}
