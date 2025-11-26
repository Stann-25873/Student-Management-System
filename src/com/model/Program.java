/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author USER
 */


public class Program {
    private int programId;
    private int departmentId;
    private String programName;
    private int durationYears;
    private boolean isActive;

    // CONSTRUCTOR for CREATE operation
    public Program(int departmentId, String programName, int durationYears, boolean isActive) {
        this.departmentId = departmentId;
        this.programName = programName;
        this.durationYears = durationYears;
        this.isActive = isActive;
    }

    // CONSTRUCTOR for READ/UPDATE operations (with ID)
    public Program(int programId, int departmentId, String programName, int durationYears, boolean isActive) {
        this.programId = programId;
        this.departmentId = departmentId;
        this.programName = programName;
        this.durationYears = durationYears;
        this.isActive = isActive;
    }

    // --- GETTERS AND SETTERS ---
    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(int durationYears) {
        this.durationYears = durationYears;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}