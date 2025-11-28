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
import com.view.StudentView;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class SearchView extends JFrame {


    private static final Color PRIMARY_COLOR = new Color(51, 136, 128); 
    private static final Color BACKGROUND_APP_COLOR = new Color(240, 240, 240);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color CARD_COLOR = Color.WHITE; 
    private StudentController controller;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearchTerm;
    private JButton btnSearch, btnClearSearch, btnBackToStudents;

    public SearchView() {
        controller = new StudentController();

        setTitle("Student Search and Filter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650); 
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_APP_COLOR);

        initComponents();
        addEventListeners();
        loadStudentsToTable(null);

        setLocationRelativeTo(null);
    }
    private void initComponents() {

        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

       
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchPanel.setBackground(PRIMARY_COLOR);

        txtSearchTerm = new JTextField(25); 
        txtSearchTerm.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearchTerm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        btnSearch = new JButton("SEARCH");
        btnClearSearch = new JButton("CLEAR FILTER");

        
        styleSearchButton(btnSearch);
        styleSearchButton(btnClearSearch);


        JLabel searchLabel = new JLabel("Search by ID, First or Last Name:");
        searchLabel.setForeground(TEXT_COLOR);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));

        searchPanel.add(searchLabel);
        searchPanel.add(txtSearchTerm);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClearSearch);

       
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // 0 pour centrer verticalement
        navPanel.setBackground(PRIMARY_COLOR);

        btnBackToStudents = new JButton("<- Back to Student Management");
        
      
        styleNavBackButton(btnBackToStudents);
        
        navPanel.add(btnBackToStudents);

       
        topPanel.add(searchPanel, BorderLayout.CENTER); 
        topPanel.add(navPanel, BorderLayout.EAST);      
        add(topPanel, BorderLayout.NORTH);

       
        String[] columnNames = {"ID", "First Name", "Last Name", "Phone", "DOB", "Reg Date", "Program ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        styleTable(studentTable); 

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            "Search Results",
            0, 2,
            new Font("Arial", Font.BOLD, 14),
            PRIMARY_COLOR.darker()
        ));

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
                // Nécessite que StudentController ait une méthode searchStudents()
                students = controller.searchStudents(searchTerm); 
            }

            tableModel.setRowCount(0);

            if (students.isEmpty() && searchTerm != null && !searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students found matching the search term.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }

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
    private void styleNavBackButton(JButton button) {
        button.setBackground(PRIMARY_COLOR.darker());
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 30)); // Largeur augmentée
    }
    private void styleSearchButton(JButton button) {
        button.setBackground(PRIMARY_COLOR.darker());
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 30));
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

