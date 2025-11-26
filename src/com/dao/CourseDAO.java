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

import com.model.Course;
import com.util.DBUtil; // Utilisation de DBUtil
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection(); 
    }

    // --- CREATE ---
    public boolean addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO public.course (program_id, course_name, credits, semester_offered) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, course.getProgramId());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            stmt.setString(4, course.getSemesterOffered());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // --- READ ALL ---
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM public.course ORDER BY course_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getInt("program_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits"),
                    rs.getString("semester_offered")
                );
                courses.add(course);
            }
        }
        return courses;
    }

    // --- UPDATE ---
    public boolean updateCourse(Course course) throws SQLException {
        String sql = "UPDATE public.course SET program_id = ?, course_name = ?, credits = ?, semester_offered = ? WHERE course_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, course.getProgramId());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            stmt.setString(4, course.getSemesterOffered());
            stmt.setInt(5, course.getCourseId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // --- DELETE ---
    public boolean deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM public.course WHERE course_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }
}