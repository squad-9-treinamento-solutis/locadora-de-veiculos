package br.com.solutis.locadora.model.dto.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CartDtoResponse {

    private Long id;
    private String name;
    private String cpf;
    private List<Long> rents;
}
