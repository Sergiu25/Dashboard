package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String id;
    private String name;
    private Department department;
    private List<Benefit> benefits;

    public Employee(String id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.benefits = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void addBenefit(Benefit benefit) {
        benefits.add(benefit);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + name + ", department=" + department + "}";
    }
}
