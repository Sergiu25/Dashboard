package service;

import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    // ===================== ADD =====================
    // Lăsăm DB să genereze singur ID-ul (AUTOINCREMENT)
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, department) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getDepartment());

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
                int rawId = rs.getInt("id"); // AUTOINCREMENT ID din DB
                String formattedId = String.format("%05d", rawId); // ex: 00001
                employees.add(new Employee(
                        formattedId,
                        rs.getString("name"),
                        rs.getString("department")
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

            int numericId = Integer.parseInt(formattedId); // "00001" -> 1
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
