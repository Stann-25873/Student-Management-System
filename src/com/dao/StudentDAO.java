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


import com.model.Student;
import com.util.DBUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    

    public boolean insertStudent(Student student) throws SQLException {
        
        String sql = "INSERT INTO STUDENT (program_id, first_name, last_name, contact_phone, birth_date, registration_date) VALUES (?, ?, ?, ?, ?::DATE, ?)";
        
      
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
          
            pstmt.setInt(1, student.getProgramId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setDate(5, Date.valueOf(student.getBirthDate()));
            pstmt.setDate(6, Date.valueOf(student.getRegistrationDate()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

  
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, program_id, first_name, last_name, contact_phone, birth_date, registration_date FROM STUDENT ORDER BY student_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                
                int studentId = rs.getInt("student_id");
                int programId = rs.getInt("program_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("contact_phone");
                LocalDate birthDate = rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null;
                LocalDate registrationDate = rs.getDate("registration_date") != null ? rs.getDate("registration_date").toLocalDate() : null;
                
                Student student = new Student(studentId, programId, firstName, lastName, phoneNumber, birthDate, registrationDate);
                students.add(student);
            }
        } 
        return students;
    }
    
 
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE STUDENT SET program_id=?, first_name=?, last_name=?, contact_phone=?, birth_date=? WHERE student_id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, student.getProgramId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setDate(5, Date.valueOf(student.getBirthDate()));
            pstmt.setInt(6, student.getStudentId()); // WHERE clause target

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

 
    public boolean deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM STUDENT WHERE student_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

   public List<Student> searchStudents(String searchTerm) throws SQLException {
    List<Student> students = new ArrayList<>();
   
    String sql = "SELECT * FROM public.student WHERE " +
                 "CAST(student_id AS TEXT) ILIKE ? OR " + 
                 "first_name ILIKE ? OR " +
                 "last_name ILIKE ?";
    
    String searchPattern = "%" + searchTerm.trim() + "%";
    
    try (Connection conn = DBUtil.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);
        stmt.setString(3, searchPattern);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                
             
                Date sqlBirthDate = rs.getDate("birth_date");
                Date sqlRegDate = rs.getDate("registration_date");
                
               
                Student s = new Student(
                    rs.getInt("student_id"), 
                    rs.getInt("program_id"), 
                    rs.getString("first_name"), 
                    rs.getString("last_name"), 
                    rs.getString("contact_phone"),
                    sqlBirthDate != null ? sqlBirthDate.toLocalDate() : null, 
                    sqlRegDate != null ? sqlRegDate.toLocalDate() : null 
                );
                students.add(s);
            }
        }
    }
    return students;
}
}
