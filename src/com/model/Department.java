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

public class Department {
    private int departmentId;
    private String departmentName;
    private String headOfDept;
    private String buildingLocation;
    private String contactPhone;

    
    public Department(String departmentName, String headOfDept, String buildingLocation, String contactPhone) {
        this.departmentName = departmentName;
        this.headOfDept = headOfDept;
        this.buildingLocation = buildingLocation;
        this.contactPhone = contactPhone;
    }

 
    public Department(int departmentId, String departmentName, String headOfDept, String buildingLocation, String contactPhone) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headOfDept = headOfDept;
        this.buildingLocation = buildingLocation;
        this.contactPhone = contactPhone;
    }

  
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
