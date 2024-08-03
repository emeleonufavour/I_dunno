package com.example.demo.payroll;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Employee {
    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    private Employee boss;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
    private Set<Employee> subordinates = new HashSet<>();

    Employee() {
    }

    Employee(String name, String role) {

        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public Employee getBoss() {
        return this.boss;
    }

    public Set<Employee> getSubordinates() {
        return this.subordinates;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
        subordinate.setBoss(this);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
