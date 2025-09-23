package model;
import java.util.ArrayList;
import java.util.List;
public class Employee {
    private String id;
    private String name;
    private List<Benefit> benefits;
    private String departament;
    public Employee(String id, String name, String departament) {
        this.id = id;
        this.name = name;
        this.benefits = benefits;
        this.departament = departament;
    }
    public Employee(String id, String name, List<Benefit> benefits) {
        this.id = id;
        this.name = name;
        this.benefits = new ArrayList<>();
    }
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.benefits = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Benefit> getBenefits() {
        return benefits;
    }
    public void addBenefit(Benefit benefit) {
        benefits.add(benefit);
    }
    public boolean removeBenefitById(int benefitId) {
        return benefits.removeIf(benefit -> benefit.getId() == benefitId);
    }
    public String getDepartment() {
        return departament;
    }
    public void setDepartament(String departament) {
        this.departament = departament;
    }
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + name + ", benefits=" + benefits + "departament=" + departament + "}";
    }

}
