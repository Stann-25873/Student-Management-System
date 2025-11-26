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

import com.dao.StudentDAO;
import com.model.Student;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StudentController {
    
    private StudentDAO dao;

    public StudentController() {
        this.dao = new StudentDAO();
    }

    
    private void validateStudent(Student student) {
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (student.getProgramId() <= 0) {
            throw new IllegalArgumentException("Program ID must be valid (greater than 0).");
        }
        if (student.getBirthDate() == null || student.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Student must be at least 18 years old and DOB must be valid.");
        }
        // Validation simple du format du téléphone (peut être complexifié avec des Regex)
        if (student.getPhoneNumber() == null || student.getPhoneNumber().length() < 10) {
            throw new IllegalArgumentException("Phone number is required and must be valid.");
        }
    }

    public boolean registerStudent(Student student) throws SQLException {
        validateStudent(student);
        return dao.insertStudent(student);
    }

    public List<Student> getAllStudents() throws SQLException {
        return dao.getAllStudents();
    }

    public boolean updateStudent(Student student) throws SQLException {
        if (student.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID must be valid for update.");
        }
        validateStudent(student);
        return dao.updateStudent(student);
    }

    public boolean deleteStudent(int studentId) throws SQLException {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be valid for deletion.");
        }
        return dao.deleteStudent(studentId);
    }

    public List<Student> searchStudents(String searchTerm) throws SQLException {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // Si le terme est vide ou null, retourne la liste complète.
            return dao.getAllStudents();
        }
        // Délègue la recherche filtrée au DAO
        return dao.searchStudents(searchTerm);
    }
}