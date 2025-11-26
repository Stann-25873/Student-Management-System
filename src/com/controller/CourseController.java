/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author USER
 */

import com.dao.CourseDAO;
import com.model.Course;
import java.sql.SQLException;
import java.util.List;

public class CourseController {
    private CourseDAO dao;

    public CourseController() {
        this.dao = new CourseDAO();
    }

    private void validateCourse(Course course) {
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course Name cannot be empty.");
        }
        if (course.getProgramId() <= 0) {
            throw new IllegalArgumentException("Program ID must be valid (greater than 0).");
        }
        
        int credits = course.getCredits();
        if (credits < 1 || credits > 10) {
            throw new IllegalArgumentException("Credits must be between 1 and 10.");
        }
    }

    public boolean addCourse(Course course) throws SQLException {
        validateCourse(course);
        return dao.addCourse(course);
    }

    public List<Course> getAllCourses() throws SQLException {
        return dao.getAllCourses();
    }

    public boolean updateCourse(Course course) throws SQLException {
        if (course.getCourseId() <= 0) {
            throw new IllegalArgumentException("Course ID must be valid for update.");
        }
        validateCourse(course);
        return dao.updateCourse(course);
    }

    public boolean deleteCourse(int courseId) throws SQLException {
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be valid for deletion.");
        }
        return dao.deleteCourse(courseId);
    }
}
