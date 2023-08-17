package br.com.solutis.locadora.service;

import br.com.solutis.locadora.model.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class PersonService extends AbstractService<PersonDto> {
    @Override
    public PersonDto findById(Long id) {
        return null;
    }

    @Override
    public List<PersonDto> findAll(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PersonDto add(PersonDto payload) {
        return null;
    }

    @Override
    public PersonDto update(PersonDto payload) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
