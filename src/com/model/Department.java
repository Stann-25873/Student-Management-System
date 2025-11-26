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

public class Department {
    private int departmentId;
    private String departmentName;
    private String headOfDept;
    private String buildingLocation;
    private String contactPhone;

    // CONSTRUCTOR for CREATE operation (ID is auto-generated)
    public Department(String departmentName, String headOfDept, String buildingLocation, String contactPhone) {
        this.departmentName = departmentName;
        this.headOfDept = headOfDept;
        this.buildingLocation = buildingLocation;
        this.contactPhone = contactPhone;
    }

    // CONSTRUCTOR for READ/UPDATE/DELETE operations (with ID)
    public Department(int departmentId, String departmentName, String headOfDept, String buildingLocation, String contactPhone) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headOfDept = headOfDept;
        this.buildingLocation = buildingLocation;
        this.contactPhone = contactPhone;
    }

    // --- GETTERS AND SETTERS ---
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHeadOfDept() {
        return headOfDept;
    }

    public void setHeadOfDept(String headOfDept) {
        this.headOfDept = headOfDept;
    }

    public String getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(String buildingLocation) {
        this.buildingLocation = buildingLocation;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
