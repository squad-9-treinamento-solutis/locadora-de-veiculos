package br.com.solutis.locadora.model.dto.rent;


import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class RentDtoResponse {

    private Long id;

    private String nameDriver;

    private String cpf;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate finishedDate;

    private BigDecimal value;

    private boolean confirmed;

    private boolean finished;

    private BigDecimal franchiseValue;

    private boolean thirdPartyCoverage;

    private boolean naturalPhenomenaCoverage;

    private boolean theftCoverage;

    private String plate;

    private String color;

    private String model;

    private ModelCategoryEnum category;

    private String manufacturer;

}
