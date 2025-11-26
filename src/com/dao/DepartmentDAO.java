/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

/**
 *
 * @author 25873
 */

import com.model.Department;
import com.util.DBUtil; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    
    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection(); 
    }

 
    public boolean addDepartment(Department dept) throws SQLException {
        String sql = "INSERT INTO public.department (department_name, head_of_dept, building_location, contact_phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dept.getDepartmentName());
            stmt.setString(2, dept.getHeadOfDept());
            stmt.setString(3, dept.getBuildingLocation());
            stmt.setString(4, dept.getContactPhone());
            
            return stmt.executeUpdate() > 0;
        }
    }

  
    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM public.department ORDER BY department_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Department dept = new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name"),
                    rs.getString("head_of_dept"),
                    rs.getString("building_location"),
                    rs.getString("contact_phone")
                );
                departments.add(dept);
            }
        }
        return departments;
    }

  
    public boolean updateDepartment(Department dept) throws SQLException {
        String sql = "UPDATE public.department SET department_name = ?, head_of_dept = ?, building_location = ?, contact_phone = ? WHERE department_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dept.getDepartmentName());
            stmt.setString(2, dept.getHeadOfDept());
            stmt.setString(3, dept.getBuildingLocation());
            stmt.setString(4, dept.getContactPhone());
            stmt.setInt(5, dept.getDepartmentId());
            
            return stmt.executeUpdate() > 0;
        }
    }

 
    public boolean deleteDepartment(int departmentId) throws SQLException {
        String sql = "DELETE FROM public.department WHERE department_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, departmentId);
            return stmt.executeUpdate() > 0;
        }
    }
}