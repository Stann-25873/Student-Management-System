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

import com.dao.StudentDAO;
import com.controller.StudentController;
import com.model.Student;
import com.view.StudentView; 
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchView extends JFrame {
    
   
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); 
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private StudentController controller;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearchTerm;
    private JButton btnSearch, btnClearSearch, btnBackToStudents;

    public SearchView() {
        controller = new StudentController();

        setTitle("Student Search and Filter (Page 4)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        addEventListeners();
        loadStudentsToTable(null); // Charge tout par défaut
        
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
     
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(PRIMARY_COLOR);
        
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchPanel.setBackground(PRIMARY_COLOR);
        
        txtSearchTerm = new JTextField(30);
        txtSearchTerm.setFont(txtSearchTerm.getFont().deriveFont(14f));
        
        btnSearch = new JButton("Search");
        btnClearSearch = new JButton("Clear Filter");
        
        styleButton(btnSearch, PRIMARY_COLOR, BACKGROUND_COLOR); 
        styleButton(btnClearSearch, Color.DARK_GRAY, BACKGROUND_COLOR); 
        
        JLabel searchLabel = new JLabel("Search by ID, First or Last Name:");
        searchLabel.setForeground(TEXT_COLOR);
        
        searchPanel.add(searchLabel); 
        searchPanel.add(txtSearchTerm);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClearSearch);
        
 
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setBackground(PRIMARY_COLOR);
        
        btnBackToStudents = new JButton("<- Back to Student Management (Page 1)");
        styleButton(btnBackToStudents, BACKGROUND_COLOR, PRIMARY_COLOR); // Fond Blanc, Texte Violet
        navPanel.add(btnBackToStudents);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(navPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // PANNEAU CENTER (JTable)
        String[] columnNames = {"ID", "First Name", "Last Name", "Phone", "DOB", "Reg Date", "Program ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        
      
        btnSearch.addActionListener(e -> performSearch());
        
 
        btnClearSearch.addActionListener(e -> {
            txtSearchTerm.setText("");
            loadStudentsToTable(null); 
        });

       
        btnBackToStudents.addActionListener(e -> {
           
            new StudentView().setVisible(true);
            this.dispose();
        });
        
       
        txtSearchTerm.addActionListener(e -> performSearch());
    }

  
    
    private void performSearch() {
        String term = txtSearchTerm.getText().trim();
        loadStudentsToTable(term);
    }
    
    private void loadStudentsToTable(String searchTerm) {
        try {
            List<Student> students;
            
            if (searchTerm == null || searchTerm.isEmpty()) {
                students = controller.getAllStudents();
            } else {
                students = controller.searchStudents(searchTerm);
            }
            
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
                "Database Error loading or searching students: " + ex.getMessage(), 
                "DB Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
    }
}

    
    
    
 /*   @SuppressWarnings("unchecked")
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

    /**
     * @param args the command line arguments
     *
    public static void main(String args[]) {
        * Set the Nimbus look and feel *
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
            java.util.logging.Logger.getLogger(SearchView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form *
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchView().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

