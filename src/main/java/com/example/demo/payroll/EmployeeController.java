package com.example.demo.payroll;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(employee -> {
                    EntityModel<Employee> employeeModel = EntityModel.of(employee,
                            linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                            linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

                    if (employee.getBoss() != null) {
                        employeeModel.add(linkTo(methodOn(EmployeeController.class).one(employee.getBoss().getId()))
                                .withRel("boss"));
                    }

                    return employeeModel;
                })
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<EmployeeDTO> one(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        EmployeeDTO employeeDTO = new EmployeeDTO(employee);

        EntityModel<EmployeeDTO> employeeModel = EntityModel.of(employeeDTO,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

        if (employee.getBoss() != null) {
            employeeModel
                    .add(linkTo(methodOn(EmployeeController.class).one(employee.getBoss().getId())).withRel("boss"));
        }

        return employeeModel;
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/employees/{employeeId}/boss/{bossId}")
    ResponseEntity<?> setBoss(@PathVariable Long employeeId, @PathVariable Long bossId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Employee boss = repository.findById(bossId)
                .orElseThrow(() -> new EmployeeNotFoundException(bossId));

        employee.setBoss(boss);
        repository.save(employee);

        return ResponseEntity.ok().build();
    }
}
