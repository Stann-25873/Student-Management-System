/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 *@author 25873
 * */



import com.controller.ProgramController;
import com.model.Program;
import com.view.StudentView;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ProgramView extends JFrame {


    private static final Color PRIMARY_COLOR = new Color(51, 136, 128);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BACKGROUND_APP_COLOR = new Color(240, 240, 240);
    private static final Color TEXT_COLOR = Color.DARK_GRAY;

    private ProgramController controller;
    private JTable programTable;
    private DefaultTableModel tableModel;


    private JTextField txtProgramId, txtDeptId, txtProgramName, txtDuration;
    private JCheckBox chkIsActive;


    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JButton btnBackToStudents;

    public ProgramView() {
        controller = new ProgramController();

        setTitle("Program Management System (Page 2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_APP_COLOR);

        initComponents();
        loadProgramsToTable();
        addEventListeners();

        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            "Program Details",
            0, 2,
            new Font("Arial", Font.BOLD, 14),
            PRIMARY_COLOR.darker()
        ));
        formPanel.setBackground(CARD_COLOR);
        formPanel.setPreferredSize(new Dimension(350, 0));

        JPanel inputGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        inputGrid.setBackground(CARD_COLOR);
        inputGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtProgramId = createStyledTextField("");
        txtProgramId.setEditable(false);
        txtDeptId = createStyledTextField("e.g., 101");
        txtProgramName = createStyledTextField("e.g., Computer Science");
        txtDuration = createStyledTextField("e.g., 4");

        chkIsActive = new JCheckBox("Is Active", true);
        chkIsActive.setBackground(CARD_COLOR);

        inputGrid.add(new JLabel("Program ID:")); inputGrid.add(txtProgramId);
        inputGrid.add(new JLabel("Department ID (FK):")); inputGrid.add(txtDeptId);
        inputGrid.add(new JLabel("Program Name:")); inputGrid.add(txtProgramName);
        inputGrid.add(new JLabel("Duration (Years):")); inputGrid.add(txtDuration);
        inputGrid.add(new JLabel("Status:")); inputGrid.add(chkIsActive);

        // Ajout des champs de saisie en haut (NORTH) du panneau de formulaire
        formPanel.add(inputGrid, BorderLayout.NORTH);

        // CHANGEMENT ICI: Espacement réduit (5) pour aligner les 4 boutons.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.setBackground(CARD_COLOR);

        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnClear = new JButton("CLEAR FORM");

        // Utilisation de la nouvelle méthode styleCRUDButton
        styleCRUDButton(btnAdd);
        styleCRUDButton(btnUpdate);
        styleCRUDButton(btnDelete);
        styleCRUDButton(btnClear);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Ajout des boutons en bas (SOUTH) du panneau de formulaire pour garantir l'affichage
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.WEST);

        String[] columnNames = {"ID", "Dept ID", "Name", "Duration (Yrs)", "Active"};
        tableModel = new DefaultTableModel(columnNames, 0);
        programTable = new JTable(tableModel);
        styleTable(programTable);

        JScrollPane scrollPane = new JScrollPane(programTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            "Registered Programs",
            0, 2,
            new Font("Arial", Font.BOLD, 14),
            PRIMARY_COLOR.darker()
        ));

        add(scrollPane, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PRIMARY_COLOR);

        btnBackToStudents = new JButton("<- Back to Student Management (Page 1)");
        // CHANGEMENT: Utilisation d'une méthode de style pour les boutons de navigation (taille normale)
        styleNavButton(btnBackToStudents);

        navPanel.add(btnBackToStudents);
        add(navPanel, BorderLayout.NORTH);
    }

    private void addEventListeners() {
        btnAdd.addActionListener(e -> addProgram());
        btnUpdate.addActionListener(e -> updateProgram());
        btnDelete.addActionListener(e -> deleteProgram());
        btnClear.addActionListener(e -> clearForm());

        btnBackToStudents.addActionListener(e -> {
            new StudentView().setVisible(true);
            this.dispose();
        });

        programTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && programTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

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
        txtDeptId.setText("e.g., 101");
        txtProgramName.setText("e.g., Computer Science");
        txtDuration.setText("e.g., 4");
        chkIsActive.setSelected(true);
        programTable.clearSelection();
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

    private void styleCRUDButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // CHANGEMENT CLÉ: Largeur ajustée à 80 pour aligner les 4 boutons.
        button.setPreferredSize(new Dimension(80, 30));
    }
    
    private void styleNavButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY_COLOR.darker());
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Taille normale pour le bouton de navigation
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
