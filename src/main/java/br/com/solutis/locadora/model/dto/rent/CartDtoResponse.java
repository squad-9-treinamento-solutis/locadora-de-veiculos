package br.com.solutis.locadora.model.dto.rent;

import br.com.solutis.locadora.model.entity.rent.Rent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CartDtoResponse {

    private Long id;
    private String name;
    private String cpf;
    private List<Rent> rents;
}
