/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author 25873
 */

import com.dao.DepartmentDAO;
import com.model.Department;
import java.sql.SQLException;
import java.util.List;

public class DepartmentController {
    private DepartmentDAO dao;

    public DepartmentController() {
        this.dao = new DepartmentDAO(); 
    }
    
    private void validateDepartment(Department dept) {
        if (dept.getDepartmentName() == null || dept.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Department Name cannot be empty.");
        }
        
    }

    public boolean addDepartment(Department dept) throws SQLException {
        validateDepartment(dept);
        return dao.addDepartment(dept);
    }

    public List<Department> getAllDepartments() throws SQLException {
        return dao.getAllDepartments();
    }

    public boolean updateDepartment(Department dept) throws SQLException {
        if (dept.getDepartmentId() <= 0) {
            throw new IllegalArgumentException("Department ID must be valid for update.");
        }
        validateDepartment(dept);
        return dao.updateDepartment(dept);
    }

    public boolean deleteDepartment(int departmentId) throws SQLException {
        if (departmentId <= 0) {
            throw new IllegalArgumentException("Department ID must be valid for deletion.");
        }
        return dao.deleteDepartment(departmentId);
    }
}
