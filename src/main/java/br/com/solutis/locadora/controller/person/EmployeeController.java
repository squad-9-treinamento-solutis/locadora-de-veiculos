package br.com.solutis.locadora.controller.person;


import br.com.solutis.locadora.exception.person.employee.EmployeeException;
import br.com.solutis.locadora.exception.person.employee.EmployeeNotFoundException;
import br.com.solutis.locadora.model.dto.person.EmployeeDto;
import br.com.solutis.locadora.response.ErrorResponse;
import br.com.solutis.locadora.service.person.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "EmployeeController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(
            summary = "Listar por id",
            description = "Retorna as informações do funcionario por id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (EmployeeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Listar todos",
            description = "Retorna as informações de todos os funcionarios"
    )
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            return new ResponseEntity<>(employeeService.findAll(page, size), HttpStatus.OK);
        } catch (EmployeeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Adicionar um novo funcionario",
            description = "Retorna as informações do funcionario adicionado"
    )
    @PostMapping
    public ResponseEntity<?> add(@RequestBody EmployeeDto payload) {
        try {
            EmployeeDto employeeDto = employeeService.add(payload);

            return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
        } catch (EmployeeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Atualiza um funcionario",
            description = "Retorna o codigo 204 (No Content)"
    )
    @PutMapping
    public ResponseEntity<?> update(@RequestBody EmployeeDto payload) {
        try {
            return new ResponseEntity<>(employeeService.update(payload), HttpStatus.NO_CONTENT);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (EmployeeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Apaga um funcionario por id",
            description = "Retorna o codigo 204 (No Content)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            employeeService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (EmployeeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}