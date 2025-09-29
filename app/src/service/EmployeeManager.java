package service;

import model.Employee;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private final Gson gson = new Gson();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    //Save the employees in files
    public void saveToFile(String fileName) {
        try(FileWriter writer=new FileWriter(fileName)){
            gson.toJson(employees, writer);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadFromFile(String fileName) {
        try (FileReader reader=new FileReader(fileName)){
            Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
            employees = gson.fromJson(reader,listType);
            if(employees==null){
                employees = new ArrayList<>();
            }
        }catch(IOException e){
            employees = new ArrayList<>();
        }
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

    public boolean deleteEmployee(String id) {
       return employees.removeIf(e->e.getId().equalsIgnoreCase(id));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }
}
