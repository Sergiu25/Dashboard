package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String id;            // în UI îl afișăm formatat (ex: "00001")
    private String name;
    private String department;
    private List<Benefit> benefits = new ArrayList<>();

    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Employee(String id, String name) {
        this(id, name, null);
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public List<Benefit> getBenefits() { return benefits; }

    // Setters
    public void setDepartment(String department) { this.department = department; }

    // Beneficii (opțional)
    public void addBenefit(Benefit benefit) { benefits.add(benefit); }
    public boolean removeBenefitById(int benefitId) {
        return benefits.removeIf(benefit -> benefit.getId() == benefitId);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id +
                ", name=" + name +
                ", department=" + department +
                ", benefits=" + benefits + "}";
    }
}
