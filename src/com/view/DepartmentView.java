/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 *@author 25873
 */

import com.controller.DepartmentController;
import com.model.Department;
import com.view.StudentView; 
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class DepartmentView extends JFrame {
    
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); // Violet Profond
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private DepartmentController controller;
    private JTable deptTable;
    private DefaultTableModel tableModel;
    private JTextField txtDeptId, txtDeptName, txtHeadOfDept, txtBuildingLocation, txtContactPhone;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JButton btnBackToStudents; 

    public DepartmentView() {
        controller = new DepartmentController();

        setTitle("Department Management System (Page 3)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        loadDepartmentsToTable();
        addEventListeners();
        
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
       
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Department Details"));
        formPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel inputGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        inputGrid.setBackground(BACKGROUND_COLOR);
        
        txtDeptId = new JTextField(20);
        txtDeptId.setEditable(false);
        txtDeptName = new JTextField(20);
        txtHeadOfDept = new JTextField(20);
        txtBuildingLocation = new JTextField(20);
        txtContactPhone = new JTextField(20);

        inputGrid.add(new JLabel("Department ID:")); inputGrid.add(txtDeptId);
        inputGrid.add(new JLabel("Name:")); inputGrid.add(txtDeptName);
        inputGrid.add(new JLabel("Head of Dept:")); inputGrid.add(txtHeadOfDept);
        inputGrid.add(new JLabel("Building Location:")); inputGrid.add(txtBuildingLocation);
        inputGrid.add(new JLabel("Contact Phone:")); inputGrid.add(txtContactPhone);
        
        formPanel.add(inputGrid, BorderLayout.NORTH);
        
        // PANNEAU DES BOUTONS (Actions CRUD)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnClear = new JButton("CLEAR FORM");
        
        styleButton(btnAdd);
        styleButton(btnUpdate);
        styleButton(btnDelete);
        styleButton(btnClear);
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        formPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(formPanel, BorderLayout.WEST);

        // PANNEAU CENTER (JTable)
        String[] columnNames = {"ID", "Name", "Head", "Building", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        deptTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(deptTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Departments"));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // PANNEAU NORD (Bouton de retour)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PRIMARY_COLOR);
        
        btnBackToStudents = new JButton("<- Back to Student Management (Page 1)");
        styleButton(btnBackToStudents);
        
        navPanel.add(btnBackToStudents);
        add(navPanel, BorderLayout.NORTH);
    }
    
    private void addEventListeners() {
        // --- Listeners CRUD ---
        btnAdd.addActionListener(e -> addDepartment());
        btnUpdate.addActionListener(e -> updateDepartment());
        btnDelete.addActionListener(e -> deleteDepartment());
        btnClear.addActionListener(e -> clearForm());

        // --- Listener de Navigation ---
        btnBackToStudents.addActionListener(e -> {
            // Suppose que StudentView est le nom de la classe de la page 1
            new StudentView().setVisible(true);
            this.dispose();
        });

        // --- Listener de Sélection de Table ---
        deptTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && deptTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    // --- LOGIQUE CRUD ---
    private void loadDepartmentsToTable() {
        try {
            List<Department> departments = controller.getAllDepartments();
            tableModel.setRowCount(0); 
            
            for (Department dept : departments) {
                Object[] rowData = {
                    dept.getDepartmentId(),
                    dept.getDepartmentName(),
                    dept.getHeadOfDept(),
                    dept.getBuildingLocation(),
                    dept.getContactPhone()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error loading departments: " + ex.getMessage(), 
                "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addDepartment() {
        try {
            Department newDept = new Department(
                txtDeptName.getText().trim(), 
                txtHeadOfDept.getText().trim(), 
                txtBuildingLocation.getText().trim(), 
                txtContactPhone.getText().trim()
            );

            if (controller.addDepartment(newDept)) {
                JOptionPane.showMessageDialog(this, "Department added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDepartmentsToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Addition failed: No rows affected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateDepartment() {
        try {
            int deptId = Integer.parseInt(txtDeptId.getText().trim());
            
            Department updatedDept = new Department(
                deptId,
                txtDeptName.getText().trim(), 
                txtHeadOfDept.getText().trim(), 
                txtBuildingLocation.getText().trim(), 
                txtContactPhone.getText().trim()
            );
            
            if (controller.updateDepartment(updatedDept)) {
                JOptionPane.showMessageDialog(this, "Department updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDepartmentsToTable(); 
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. ID not found or data unchanged.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Please select a department from the table.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteDepartment() {
        try {
            int deptIdToDelete = Integer.parseInt(txtDeptId.getText().trim());
            
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete department ID: " + deptIdToDelete + "?", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                if (controller.deleteDepartment(deptIdToDelete)) {
                    JOptionPane.showMessageDialog(this, "Department deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadDepartmentsToTable(); 
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a department from the table.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
             JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- UTILS ---
    private void fillFormFromTable() {
        int selectedRow = deptTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtDeptId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtDeptName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            // Les champs Head, Building et Phone peuvent être NULL dans la DB, gérer le cas où getValueAt renvoie null
            txtHeadOfDept.setText(tableModel.getValueAt(selectedRow, 2) != null ? tableModel.getValueAt(selectedRow, 2).toString() : "");
            txtBuildingLocation.setText(tableModel.getValueAt(selectedRow, 3) != null ? tableModel.getValueAt(selectedRow, 3).toString() : "");
            txtContactPhone.setText(tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "");
        }
    }
    
    private void clearForm() {
        txtDeptId.setText("");
        txtDeptName.setText("");
        txtHeadOfDept.setText("");
        txtBuildingLocation.setText("");
        txtContactPhone.setText("");
        deptTable.clearSelection();
    }
    
    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR); 
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
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
     *
    public static void main(String args[]) {
        * Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(DepartmentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DepartmentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DepartmentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DepartmentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        * Create and display the form *
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DepartmentView().setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

