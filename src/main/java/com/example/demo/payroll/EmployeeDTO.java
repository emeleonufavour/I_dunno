package com.example.demo.payroll;

import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String role;
    private EmployeeDTO boss;
    private Set<EmployeeDTO> subordinates;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.role = employee.getRole();
        if (employee.getBoss() != null) {
            this.boss = new EmployeeDTO(employee.getBoss(), false);
        }
        this.subordinates = employee.getSubordinates().stream()
                .map(sub -> new EmployeeDTO(sub, false))
                .collect(Collectors.toSet());
    }

    private EmployeeDTO(Employee employee, boolean includeSubordinates) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.role = employee.getRole();
        if (includeSubordinates) {
            this.subordinates = employee.getSubordinates().stream()
                    .map(sub -> new EmployeeDTO(sub, false))
                    .collect(Collectors.toSet());
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public EmployeeDTO getBoss() {
        return boss;
    }

    public Set<EmployeeDTO> getSubordinates() {
        return subordinates;
    }
}
