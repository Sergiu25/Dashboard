package service;

import model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee findEmployeeByName(String name) {
        for (Employee e : employees) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null; // dacă nu există
    }

    public boolean updateEmployee(String name, Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getName().equalsIgnoreCase(name)) {
                employees.set(i, updatedEmployee);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmployee(String name) {
        return employees.removeIf(e -> e.getName().equalsIgnoreCase(name));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }
}
