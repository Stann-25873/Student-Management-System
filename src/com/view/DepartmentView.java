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


    private static final Color PRIMARY_COLOR = new Color(51, 136, 128); // Dark Teal
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BACKGROUND_APP_COLOR = new Color(240, 240, 240);
    private static final Color TEXT_COLOR = Color.DARK_GRAY;
     private DepartmentController controller;
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private JTextField txtDepartmentId, txtName, txtHeadOfDept, txtBuildingLocation, txtContactPhone;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JButton btnBackToStudents;

    public DepartmentView() {
        controller = new DepartmentController();

        setTitle("Department Management System (Page 3)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_APP_COLOR);

        initComponents();
        loadDepartmentsToTable();
        addEventListeners();

        setLocationRelativeTo(null);
    }

    private void initComponents() {

        // WEST PANEL (Form and Actions)
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            "Department Details",
            0, 2,
            new Font("Arial", Font.BOLD, 14),
            PRIMARY_COLOR.darker()
        ));
        formPanel.setBackground(CARD_COLOR);
        formPanel.setPreferredSize(new Dimension(350, 0));

        JPanel inputGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        inputGrid.setBackground(CARD_COLOR);
        inputGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        txtDepartmentId = createStyledTextField("");
        txtDepartmentId.setEditable(false);
        txtName = createStyledTextField("e.g., Software Engineering");
        txtHeadOfDept = createStyledTextField("e.g., Dr. Smith");
        txtBuildingLocation = createStyledTextField("e.g., Building A, Room 101");
        txtContactPhone = createStyledTextField("e.g., 123-456-7890");

        inputGrid.add(new JLabel("Department ID:")); inputGrid.add(txtDepartmentId);
        inputGrid.add(new JLabel("Name:")); inputGrid.add(txtName);
        inputGrid.add(new JLabel("Head of Dept:")); inputGrid.add(txtHeadOfDept);
        inputGrid.add(new JLabel("Building Location:")); inputGrid.add(txtBuildingLocation);
        inputGrid.add(new JLabel("Contact Phone:")); inputGrid.add(txtContactPhone);

        formPanel.add(inputGrid, BorderLayout.NORTH);


        // BUTTON PANEL (CRUD Actions)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(CARD_COLOR);

        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE"); 
        btnClear = new JButton("CLEAR FORM"); 

        // Adjusted size (80px) for 4 buttons
        styleButton(btnAdd);
        styleButton(btnUpdate);
        styleButton(btnDelete);
        styleButton(btnClear);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete); 
        buttonPanel.add(btnClear); 

        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.WEST);


        // CENTER PANEL (JTable)
        String[] columnNames = {"ID", "Name", "Head", "Building", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        departmentTable = new JTable(tableModel);
        styleTable(departmentTable); // Apply table style

        JScrollPane scrollPane = new JScrollPane(departmentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            "Registered Departments",
            0, 2,
            new Font("Arial", Font.BOLD, 14),
            PRIMARY_COLOR.darker()
        ));

        add(scrollPane, BorderLayout.CENTER);


        // NORTH PANEL (Navigation)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PRIMARY_COLOR);

        btnBackToStudents = new JButton("<- Back to Student Management (Page 1)");
        styleNavButton(btnBackToStudents);

        navPanel.add(btnBackToStudents);
        add(navPanel, BorderLayout.NORTH);
    }

    private void addEventListeners() {

        btnAdd.addActionListener(e -> addDepartment());
        btnUpdate.addActionListener(e -> updateDepartment());
        
        btnDelete.addActionListener(e -> deleteDepartment()); 
        btnClear.addActionListener(e -> clearForm()); 

        btnBackToStudents.addActionListener(e -> {
            new StudentView().setVisible(true);
            this.dispose();
        });

        departmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && departmentTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        field.setBackground(new Color(240, 240, 240));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        if (!placeholder.isEmpty()) {
            field.setText(placeholder);
            field.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (field.getText().equals(placeholder)) field.setText("");
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (field.getText().isEmpty()) field.setText(placeholder);
                }
            });
        }
        return field;
    }

    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Adjusted width (80px) for 4 buttons.
        button.setPreferredSize(new Dimension(80, 30)); 
    }

    private void styleNavButton(JButton button) {
        // Specific style for the navigation button in the NORTH panel
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY_COLOR.darker());
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(280, 30));
    }

    private void styleTable(JTable table) {
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
    }

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
                txtName.getText().trim(),
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
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDepartment() {
        try {
            int deptId = Integer.parseInt(txtDepartmentId.getText().trim());

            Department updatedDept = new Department(
                deptId,
                txtName.getText().trim(),
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
             JOptionPane.showMessageDialog(this, "Please select a department and ensure ID is valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDepartment() {
        try {
            int deptIdToDelete = Integer.parseInt(txtDepartmentId.getText().trim());

            int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete department ID: " + deptIdToDelete + "? This action may affect related data.",
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

    private void fillFormFromTable() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtDepartmentId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtHeadOfDept.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtBuildingLocation.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtContactPhone.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    private void clearForm() {
        txtDepartmentId.setText("");
        // Reset placeholders
        txtName.setText("e.g., Software Engineering"); 
        txtHeadOfDept.setText("e.g., Dr. Smith");
        txtBuildingLocation.setText("e.g., Building A, Room 101");
        txtContactPhone.setText("e.g., 123-456-7890");
        departmentTable.clearSelection();
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

