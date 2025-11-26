/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 * @author 25873
 */

import com.controller.StudentController;
import com.model.Student;
import com.view.DepartmentView; 
import com.view.CourseView;    
import com.view.ProgramView;
import com.view.SearchView;      
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class StudentView extends JFrame {
    
    
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); 
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE; 
    private JPanel navPanel;
    private JButton btnManagePrograms;
    private JButton btnManageDepartments;
    private JButton btnSearchFilter;
    private JButton btnLogOut;
    private JButton btnManageCourses; 
    private StudentController controller;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
  
    private JTextField txtFirstName, txtLastName, txtPhone, txtDOB, txtProgramId, txtStudentId;
    
    
    private JButton btnRegister, btnUpdate, btnDelete, btnClear;

    public StudentView() {
        
        controller = new StudentController();


        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(BACKGROUND_COLOR);

      
        initComponents();
        
       
        loadStudentsToTable();

      
        addEventListeners();
        
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        
        
        // PANNEAU PRINCIPAL WEST (Formulaire et Actions)
        
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Registration "));
        formPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel inputGrid = new JPanel(new GridLayout(6, 2, 10, 10)); 
        inputGrid.setBackground(BACKGROUND_COLOR);
        
        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20);
        txtPhone = new JTextField(20);
        txtDOB = new JTextField("YYYY-MM-DD"); 
        txtProgramId = new JTextField(20);
        txtStudentId = new JTextField(20);
        txtStudentId.setEditable(false); 

        inputGrid.add(new JLabel("First Name:")); inputGrid.add(txtFirstName);
        inputGrid.add(new JLabel("Last Name:")); inputGrid.add(txtLastName);
        inputGrid.add(new JLabel("Phone (###-###-####):")); inputGrid.add(txtPhone);
        inputGrid.add(new JLabel("DOB (YYYY-MM-DD):")); inputGrid.add(txtDOB);
        inputGrid.add(new JLabel("Program ID:")); inputGrid.add(txtProgramId);
        inputGrid.add(new JLabel("Selected Student ID:")); inputGrid.add(txtStudentId);
        
        formPanel.add(inputGrid, BorderLayout.NORTH);
        
        // PANNEAU DES BOUTONS (Actions CRUD)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnRegister = new JButton("REGISTER");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnClear = new JButton("CLEAR FORM");
    
        
        // Style des boutons CRUD
        styleButton(btnRegister);
        styleButton(btnUpdate);
        styleButton(btnDelete);
        styleButton(btnClear);
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        formPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(formPanel, BorderLayout.WEST);


        String[] columnNames = {"ID", "First Name", "Last Name", "Phone", "DOB", "Reg Date", "Program ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students List"));
        
        add(scrollPane, BorderLayout.CENTER);
        

        navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setBackground(PRIMARY_COLOR);
        
        // Initialisation des boutons de navigation
        btnManagePrograms = new JButton("Manage Program ");
        btnManageDepartments = new JButton("Manage Department ");
        btnManageCourses = new JButton("Manage Courses"); 
        btnSearchFilter = new JButton("Search/Filter ");
        btnLogOut = new JButton("Log Out"); 
        
     
        styleButton(btnManagePrograms);
        styleButton(btnManageDepartments);
        styleButton(btnManageCourses); 
        styleButton(btnSearchFilter);
        styleLogoutButton(btnLogOut);
        
        navPanel.add(btnManagePrograms);
        navPanel.add(btnManageDepartments);
        navPanel.add(btnManageCourses); 
        navPanel.add(btnSearchFilter);
        navPanel.add(btnLogOut);
        
        add(navPanel, BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
       
        
        btnRegister.addActionListener(e -> registerStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());
        btnManagePrograms.addActionListener(e -> {
            new ProgramView().setVisible(true);
            this.dispose();
        });
        btnManageDepartments.addActionListener(e -> {
            new DepartmentView().setVisible(true);
            this.dispose();
        });
        btnManageCourses.addActionListener(e -> {
            new CourseView().setVisible(true);
            this.dispose();
        });
        btnSearchFilter.addActionListener(e -> {
            new SearchView().setVisible(true);
           this.dispose();
       });
        btnLogOut.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to log out?", 
                "Confirm Log Out", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                new LoginRegisterView().setVisible(true);
                this.dispose(); 
            }
        });

        
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR); 
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
    }
    private void styleLogoutButton(JButton button) {
        button.setBackground(new Color(204, 51, 0)); // Rouge-Orange
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
    }
     
    private void loadStudentsToTable() {
        try {
            List<Student> students = controller.getAllStudents(); 
            tableModel.setRowCount(0); 
            
            for (Student s : students) {
                Object[] rowData = {
                    s.getStudentId(), 
                    s.getFirstName(), 
                    s.getLastName(), 
                    s.getPhoneNumber(),
                    s.getBirthDate(),
                    s.getRegistrationDate(),
                    s.getProgramId()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error loading students. Check DB connection/credentials: " + ex.getMessage(), 
                "Database Connection Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    private void registerStudent() {
        try {
            
            int programId = Integer.parseInt(txtProgramId.getText().trim());
            LocalDate dob = LocalDate.parse(txtDOB.getText().trim());
            
            Student newStudent = new Student(
                programId, 
                txtFirstName.getText().trim(), 
                txtLastName.getText().trim(), 
                txtPhone.getText().trim(),
                dob
            );

            boolean success = controller.registerStudent(newStudent);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE); 
                loadStudentsToTable(); 
                clearForm();
            } else {
                 JOptionPane.showMessageDialog(this, "Registration failed: No rows affected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE); 
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date format error. Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Check Program ID and all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStudent() {
        try {
           
            int studentId = Integer.parseInt(txtStudentId.getText().trim());
            int programId = Integer.parseInt(txtProgramId.getText().trim());
            LocalDate dob = LocalDate.parse(txtDOB.getText().trim());
            
            Student updatedStudent = new Student(
                studentId,
                programId, 
                txtFirstName.getText().trim(), 
                txtLastName.getText().trim(), 
                txtPhone.getText().trim(),
                dob,
                LocalDate.now() 
            );
            
            boolean success = controller.updateStudent(updatedStudent);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentsToTable(); 
                clearForm();
            } else {
                 JOptionPane.showMessageDialog(this, "Update failed. Student ID not found or data unchanged.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during update: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        try {
          
            int studentIdToDelete = Integer.parseInt(txtStudentId.getText().trim());
            
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete student ID: " + studentIdToDelete + "?", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = controller.deleteStudent(studentIdToDelete);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadStudentsToTable(); 
                    clearForm();
                } else {
                     JOptionPane.showMessageDialog(this, "Deletion failed. Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table or enter a valid ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during deletion: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fillFormFromTable() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtStudentId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtDOB.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtProgramId.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }
    
    private void clearForm() {
        txtStudentId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtDOB.setText("YYYY-MM-DD");
        txtProgramId.setText("");
        studentTable.clearSelection();
    }
}
    
  /*  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    /**
     * @param args the command line arguments
     */
  /*  public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         *
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form *
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentView().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

