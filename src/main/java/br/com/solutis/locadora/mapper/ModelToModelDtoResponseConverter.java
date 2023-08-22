package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.car.ModelDtoResponse;
import br.com.solutis.locadora.model.entity.car.Model;
import org.modelmapper.AbstractConverter;

public class ModelToModelDtoResponseConverter extends AbstractConverter<Model, ModelDtoResponse> {
    @Override
    protected ModelDtoResponse convert(Model model) {
        ModelDtoResponse modelDtoResponse = new ModelDtoResponse();

        modelDtoResponse.setId(model.getId());
        modelDtoResponse.setCategory(model.getCategory());
        modelDtoResponse.setModel(model.getDescription());
        modelDtoResponse.setManufacturer(model.getManufacturer().getName());

        return modelDtoResponse;
    }
}
