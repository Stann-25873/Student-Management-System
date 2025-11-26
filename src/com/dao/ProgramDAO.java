/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

/**
 *
 * @author USER
 */



import com.model.Program;
import com.util.DBUtil; // Utilisation de DBUtil
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramDAO {

    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection(); 
    }

    // --- CREATE ---
    public boolean addProgram(Program program) throws SQLException {
        // La colonne is_active a une valeur par défaut 'true', mais nous l'incluons pour être explicite
        String sql = "INSERT INTO public.program (department_id, program_name, duration_years, is_active) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, program.getDepartmentId());
            stmt.setString(2, program.getProgramName());
            stmt.setInt(3, program.getDurationYears());
            stmt.setBoolean(4, program.isActive());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // --- READ ALL ---
    public List<Program> getAllPrograms() throws SQLException {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM public.program ORDER BY program_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Program program = new Program(
                    rs.getInt("program_id"),
                    rs.getInt("department_id"),
                    rs.getString("program_name"),
                    rs.getInt("duration_years"),
                    rs.getBoolean("is_active")
                );
                programs.add(program);
            }
        }
        return programs;
    }

    // --- UPDATE ---
    public boolean updateProgram(Program program) throws SQLException {
        String sql = "UPDATE public.program SET department_id = ?, program_name = ?, duration_years = ?, is_active = ? WHERE program_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, program.getDepartmentId());
            stmt.setString(2, program.getProgramName());
            stmt.setInt(3, program.getDurationYears());
            stmt.setBoolean(4, program.isActive());
            stmt.setInt(5, program.getProgramId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // --- DELETE ---
    public boolean deleteProgram(int programId) throws SQLException {
        String sql = "DELETE FROM public.program WHERE program_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, programId);
            return stmt.executeUpdate() > 0;
        }
    }
}