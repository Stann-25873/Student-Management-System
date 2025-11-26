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



import com.controller.CourseController;
import com.model.Course;
import com.view.StudentView; 
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CourseView extends JFrame {
    
    // Constantes de couleur pour le thème Violet-Blanc
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); // Violet Profond
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    private CourseController controller;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    
    // Champs de formulaire
    private JTextField txtCourseId, txtProgramId, txtCourseName, txtCredits, txtSemester;
    
    // Boutons d'action
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JButton btnBackToStudents;

    public CourseView() {
        controller = new CourseController();

        setTitle("Course Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        loadCoursesToTable();
        addEventListeners();
        
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        // PANNEAU WEST (Formulaire et Actions)
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Course Details"));
        formPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel inputGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        inputGrid.setBackground(BACKGROUND_COLOR);
        
        txtCourseId = new JTextField(20);
        txtCourseId.setEditable(false);
        txtProgramId = new JTextField(20);
        txtCourseName = new JTextField(20);
        txtCredits = new JTextField(20);
        txtSemester = new JTextField(20);

        inputGrid.add(new JLabel("Course ID:")); inputGrid.add(txtCourseId);
        inputGrid.add(new JLabel("Program ID (FK):")); inputGrid.add(txtProgramId);
        inputGrid.add(new JLabel("Course Name:")); inputGrid.add(txtCourseName);
        inputGrid.add(new JLabel("Credits (1-10):")); inputGrid.add(txtCredits);
        inputGrid.add(new JLabel("Semester Offered:")); inputGrid.add(txtSemester);
        
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
        String[] columnNames = {"ID", "Program ID", "Name", "Credits", "Semester"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Courses"));
        
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
        btnAdd.addActionListener(e -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e -> clearForm());

        // --- Listener de Navigation ---
        btnBackToStudents.addActionListener(e -> {
            new StudentView().setVisible(true);
            this.dispose();
        });

        // --- Listener de Sélection de Table ---
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && courseTable.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    // --- LOGIQUE CRUD ---
    private void loadCoursesToTable() {
        try {
            List<Course> courses = controller.getAllCourses();
            tableModel.setRowCount(0); 
            
            for (Course course : courses) {
                Object[] rowData = {
                    course.getCourseId(),
                    course.getProgramId(),
                    course.getCourseName(),
                    course.getCredits(),
                    course.getSemesterOffered()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error loading courses: " + ex.getMessage(), 
                "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addCourse() {
        try {
            int programId = Integer.parseInt(txtProgramId.getText().trim());
            int credits = Integer.parseInt(txtCredits.getText().trim());
            
            Course newCourse = new Course(
                programId, 
                txtCourseName.getText().trim(), 
                credits, 
                txtSemester.getText().trim()
            );

            if (controller.addCourse(newCourse)) {
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCoursesToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Addition failed: No rows affected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Program ID and Credits must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCourse() {
        try {
            int courseId = Integer.parseInt(txtCourseId.getText().trim());
            int programId = Integer.parseInt(txtProgramId.getText().trim());
            int credits = Integer.parseInt(txtCredits.getText().trim());
            
            Course updatedCourse = new Course(
                courseId,
                programId, 
                txtCourseName.getText().trim(), 
                credits, 
                txtSemester.getText().trim()
            );
            
            if (controller.updateCourse(updatedCourse)) {
                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCoursesToTable(); 
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. ID not found or data unchanged.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Please select a course and ensure numeric fields are valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCourse() {
        try {
            int courseIdToDelete = Integer.parseInt(txtCourseId.getText().trim());
            
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete course ID: " + courseIdToDelete + "?", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                if (controller.deleteCourse(courseIdToDelete)) {
                    JOptionPane.showMessageDialog(this, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadCoursesToTable(); 
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a course from the table.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
             JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- UTILS ---
    private void fillFormFromTable() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtCourseId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtProgramId.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtCourseName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtCredits.setText(tableModel.getValueAt(selectedRow, 3).toString());
            // Le semestre peut être NULL
            txtSemester.setText(tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "");
        }
    }
    
    private void clearForm() {
        txtCourseId.setText("");
        txtProgramId.setText("");
        txtCourseName.setText("");
        txtCredits.setText("");
        txtSemester.setText("");
        courseTable.clearSelection();
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

    **
     * @param args the command line arguments
     */
    
   /* public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(CourseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CourseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CourseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
      /*  java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourseView().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

