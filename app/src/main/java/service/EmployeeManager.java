package service;

import model.Department;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    // ===================== ADD =====================
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, department) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getDepartment().name()); // salvează enum ca text

            int rows = pstmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===================== GET ALL =====================
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, name, department FROM employees ORDER BY id";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int rawId = rs.getInt("id");
                String formattedId = String.format("%05d", rawId);

                Department dept = Department.valueOf(rs.getString("department"));

                employees.add(new Employee(
                        formattedId,
                        rs.getString("name"),
                        dept
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded employees: " + employees.size());
        return employees;
    }

    // ===================== DELETE =====================
    public boolean deleteEmployee(String formattedId) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int numericId = Integer.parseInt(formattedId);
            pstmt.setInt(1, numericId);

            int rows = pstmt.executeUpdate();
            System.out.println("Deleted rows: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
