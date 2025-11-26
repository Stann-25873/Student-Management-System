/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 * @author USER
 */



import com.controller.ProgramController;
import com.model.Program;
import com.view.StudentView;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ProgramView extends JFrame {
    
    // Constantes de couleur pour le thème Violet-Blanc
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); // Violet Profond
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    private ProgramController controller;
    private JTable programTable;
    private DefaultTableModel tableModel;
    
    // Champs de formulaire
    private JTextField txtProgramId, txtDeptId, txtProgramName, txtDuration;
    private JCheckBox chkIsActive;
    
    // Boutons d'action
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JButton btnBackToStudents;

    public ProgramView() {
        controller = new ProgramController();

        setTitle("Program Management System (Page 2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        loadProgramsToTable();
        addEventListeners();
        
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        // PANNEAU WEST (Formulaire et Actions)
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Program Details"));
        formPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel inputGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        inputGrid.setBackground(BACKGROUND_COLOR);
        
        txtProgramId = new JTextField(20);
        txtProgramId.setEditable(false);
        txtDeptId = new JTextField(20);
        txtProgramName = new JTextField(20);
        txtDuration = new JTextField(20);
        chkIsActive = new JCheckBox("Is Active", true);
        chkIsActive.setBackground(BACKGROUND_COLOR);

        inputGrid.add(new JLabel("Program ID:")); inputGrid.add(txtProgramId);
        inputGrid.add(new JLabel("Department ID (FK):")); inputGrid.add(txtDeptId);
        inputGrid.add(new JLabel("Program Name:")); inputGrid.add(txtProgramName);
        inputGrid.add(new JLabel("Duration (Years):")); inputGrid.add(txtDuration);
        inputGrid.add(new JLabel("Status:")); inputGrid.add(chkIsActive);
        
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
        String[] columnNames = {"ID", "Dept ID", "Name", "Duration (Yrs)", "Active"};
        tableModel = new DefaultTableModel(columnNames, 0);
        programTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(programTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Programs"));
        
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
        btnAdd.addActionListener(e -> addProgram());
        btnUpdate.addActionListener(e -> updateProgram());
        btnDelete.addActionListener(e -> deleteProgram());
        btnClear.addActionListener(e -> clearForm());

        // --- Listener de Navigation ---
        btnBackToStudents.addActionListener(e -> {
            new StudentView().setVisible(true);
            this.dispose();
        });

        // --- Listener de Sélection de Table ---
        programTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && programTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    // --- LOGIQUE CRUD ---
    private void loadProgramsToTable() {
        try {
            List<Program> programs = controller.getAllPrograms();
            tableModel.setRowCount(0); 
            
            for (Program program : programs) {
                Object[] rowData = {
                    program.getProgramId(),
                    program.getDepartmentId(),
                    program.getProgramName(),
                    program.getDurationYears(),
                    program.isActive()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error loading programs: " + ex.getMessage(), 
                "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addProgram() {
        try {
            int deptId = Integer.parseInt(txtDeptId.getText().trim());
            int duration = Integer.parseInt(txtDuration.getText().trim());
            
            Program newProgram = new Program(
                deptId, 
                txtProgramName.getText().trim(), 
                duration, 
                chkIsActive.isSelected()
            );

            if (controller.addProgram(newProgram)) {
                JOptionPane.showMessageDialog(this, "Program added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProgramsToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Addition failed: No rows affected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Department ID and Duration must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateProgram() {
        try {
            int programId = Integer.parseInt(txtProgramId.getText().trim());
            int deptId = Integer.parseInt(txtDeptId.getText().trim());
            int duration = Integer.parseInt(txtDuration.getText().trim());
            
            Program updatedProgram = new Program(
                programId,
                deptId, 
                txtProgramName.getText().trim(), 
                duration, 
                chkIsActive.isSelected()
            );
            
            if (controller.updateProgram(updatedProgram)) {
                JOptionPane.showMessageDialog(this, "Program updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProgramsToTable(); 
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. ID not found or data unchanged.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Please select a program and ensure numeric fields are valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteProgram() {
        try {
            int programIdToDelete = Integer.parseInt(txtProgramId.getText().trim());
            
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete program ID: " + programIdToDelete + "? This will affect related students and courses.", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                if (controller.deleteProgram(programIdToDelete)) {
                    JOptionPane.showMessageDialog(this, "Program deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProgramsToTable(); 
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a program from the table.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
             JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- UTILS ---
    private void fillFormFromTable() {
        int selectedRow = programTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtProgramId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtDeptId.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtProgramName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtDuration.setText(tableModel.getValueAt(selectedRow, 3).toString());
            chkIsActive.setSelected((Boolean) tableModel.getValueAt(selectedRow, 4));
        }
    }
    
    private void clearForm() {
        txtProgramId.setText("");
        txtDeptId.setText("");
        txtProgramName.setText("");
        txtDuration.setText("");
        chkIsActive.setSelected(true);
        programTable.clearSelection();
    }
    
    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR); 
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
    }
}
    
    
    
    
    
   /* @SuppressWarnings("unchecked")
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
            java.util.logging.Logger.getLogger(ProgramView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProgramView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProgramView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProgramView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form *
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProgramView().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
