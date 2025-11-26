/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author 25873
 */

public class Course {
    private int courseId;
    private int programId;
    private String courseName;
    private int credits;
    private String semesterOffered;

   
    public Course(int programId, String courseName, int credits, String semesterOffered) {
        this.programId = programId;
        this.courseName = courseName;
        this.credits = credits;
        this.semesterOffered = semesterOffered;
    }


    public Course(int courseId, int programId, String courseName, int credits, String semesterOffered) {
        this.courseId = courseId;
        this.programId = programId;
        this.courseName = courseName;
        this.credits = credits;
        this.semesterOffered = semesterOffered;
    }

    
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(String semesterOffered) {
        this.semesterOffered = semesterOffered;
    }
}