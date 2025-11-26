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


import java.time.LocalDate;

public class Student {
    private int studentId;
    private int programId;
    private String firstName;
    private String lastName;
    private String phoneNumber; 
    private LocalDate birthDate;
    private LocalDate registrationDate;
    
   
    public Student(int pId, String first, String last, String phone, LocalDate dob) {
        this.programId = pId;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phone;
        this.birthDate = dob;
        this.registrationDate = LocalDate.now();
    }
    
    public Student(int id, int pId, String first, String last, String phone, LocalDate dob, LocalDate regDate) {
        this.studentId = id;
        this.programId = pId;
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phone;
        this.birthDate = dob;
        this.registrationDate = regDate;
    }
    
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
}
