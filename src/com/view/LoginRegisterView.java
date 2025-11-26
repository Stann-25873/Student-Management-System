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

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.controller.AuthController;
import com.model.User;
import com.view.StudentView; 
import java.awt.*;
import java.util.Optional;
import java.sql.SQLException;
import javax.swing.*;

public class LoginRegisterView extends JFrame {

   
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); 
    private static final Color ACCENT_COLOR = new Color(153, 50, 204); 
    private static final Color TEXT_COLOR = Color.DARK_GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final String CARD_LOGIN = "LoginCard";
    private static final String CARD_REGISTER = "RegisterCard";
    private AuthController controller;
    private JPanel cardsPanel; 
    private CardLayout cardLayout;
    private JTextField txtLoginUsername;
    private JPasswordField txtLoginPassword;
    private JButton btnLogin;
    private JTextField txtRegisterUsername, txtRegisterEmail;
    private JPasswordField txtRegisterPassword, txtConfirmPassword;
    private JButton btnRegister;

    public LoginRegisterView() {
        controller = new AuthController();
        setTitle("Authentication System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        
 
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(BACKGROUND_COLOR);

        initComponents();
        addEventListeners();
        
        add(cardsPanel);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
       
        JPanel loginPanel = createLoginPanel();
        cardsPanel.add(loginPanel, CARD_LOGIN);


        JPanel registerPanel = createRegisterPanel();
        cardsPanel.add(registerPanel, CARD_REGISTER);
        cardLayout.show(cardsPanel, CARD_LOGIN);
    }
    
    // =================================================================
    // PANNEAU DE CONNEXION
    // =================================================================

  
private GridBagConstraints createGbc(int gridx, int gridy, int ipadx, int ipady, int anchor) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.ipadx = ipadx;
    gbc.ipady = ipady;
    gbc.anchor = anchor; 
    gbc.insets = new Insets(10, 0, 10, 0); 
    return gbc;
}
    
    

    private JPanel createLoginPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    JLabel title = new JLabel("Login", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 32));
    title.setForeground(PRIMARY_COLOR);
    JPanel form = new JPanel(new GridBagLayout()); 
    form.setBackground(BACKGROUND_COLOR);
    

    Dimension fieldSize = new Dimension(300, 45); 

    txtLoginUsername = createStyledTextField("User Name");
    txtLoginUsername.setPreferredSize(fieldSize);
    txtLoginPassword = createStyledPasswordField("Password");
    txtLoginPassword.setPreferredSize(fieldSize);
    
    btnLogin = createStyledButton("Login", ACCENT_COLOR);
    
    JButton btnSwitchToRegister = new JButton("Register Now");
    styleLinkButton(btnSwitchToRegister);
    btnSwitchToRegister.addActionListener(e -> cardLayout.show(cardsPanel, CARD_REGISTER));
    GridBagConstraints gbc;

   
    gbc = createGbc(0, 0, 0, 0, GridBagConstraints.CENTER);
    form.add(txtLoginUsername, gbc);

    
    gbc = createGbc(0, 1, 0, 0, GridBagConstraints.CENTER);
    form.add(txtLoginPassword, gbc);

    
    gbc = createGbc(0, 2, 0, 0, GridBagConstraints.CENTER);
    gbc.insets = new Insets(20, 0, 20, 0);
    form.add(Box.createVerticalStrut(1), gbc); 

   
    gbc = createGbc(0, 3, 0, 0, GridBagConstraints.CENTER);
    form.add(btnLogin, gbc);

   
    gbc = createGbc(0, 4, 0, 0, GridBagConstraints.CENTER);
    form.add(btnSwitchToRegister, gbc);
    
    panel.add(title, BorderLayout.NORTH);
    panel.add(form, BorderLayout.CENTER); // Centrer le panneau de formulaire

    return panel;
}

    // =================================================================
    // PANNEAU D'ENREGISTREMENT
    // =================================================================
  
private JPanel createRegisterPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    JLabel title = new JLabel("Register", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 32));
    title.setForeground(PRIMARY_COLOR);
    JPanel form = new JPanel(new GridBagLayout());
    form.setBackground(BACKGROUND_COLOR);
    Dimension fieldSize = new Dimension(300, 45);

    txtRegisterUsername = createStyledTextField("User Name");
    txtRegisterUsername.setPreferredSize(fieldSize);
    txtRegisterPassword = createStyledPasswordField("Password");
    txtRegisterPassword.setPreferredSize(fieldSize);
    txtConfirmPassword = createStyledPasswordField("Confirm Password");
    txtConfirmPassword.setPreferredSize(fieldSize);
    txtRegisterEmail = createStyledTextField("Email");
    txtRegisterEmail.setPreferredSize(fieldSize);
    
    btnRegister = createStyledButton("Register", ACCENT_COLOR);

    JButton btnBackToLogin = new JButton("Back to Login");
    styleLinkButton(btnBackToLogin);
    btnBackToLogin.addActionListener(e -> cardLayout.show(cardsPanel, CARD_LOGIN));
    
 
    GridBagConstraints gbc;

 
    gbc = createGbc(0, 0, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterUsername, gbc);


    gbc = createGbc(0, 1, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterPassword, gbc);

  
    gbc = createGbc(0, 2, 0, 0, GridBagConstraints.CENTER);
    form.add(txtConfirmPassword, gbc);

  
    gbc = createGbc(0, 3, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterEmail, gbc);


    gbc = createGbc(0, 4, 0, 0, GridBagConstraints.CENTER);
    gbc.insets = new Insets(20, 0, 20, 0);
    form.add(Box.createVerticalStrut(1), gbc);


    gbc = createGbc(0, 5, 0, 0, GridBagConstraints.CENTER);
    form.add(btnRegister, gbc);

   
    gbc = createGbc(0, 6, 0, 0, GridBagConstraints.CENTER);
    form.add(btnBackToLogin, gbc);

    panel.add(title, BorderLayout.NORTH);
    panel.add(form, BorderLayout.CENTER); 
    
    return panel;
}

    // =================================================================
    // GESTION DES ÉVÉNEMENTS
    // =================================================================

    private void addEventListeners() {
        btnLogin.addActionListener(e -> attemptLogin());
        btnRegister.addActionListener(e -> attemptRegister());
    }

    private void attemptLogin() {
        String username = txtLoginUsername.getText();
        String password = new String(txtLoginPassword.getPassword());

        try {
            Optional<User> user = controller.authenticate(username, password);
            if (user.isPresent()) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.get().getUsername(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
               
                new StudentView().setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error during login: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void attemptRegister() {
        String username = txtRegisterUsername.getText();
        String email = txtRegisterEmail.getText();
        String password = new String(txtRegisterPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        try {
            if (controller.registerUser(username, password, confirmPassword, email)) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                cardLayout.show(cardsPanel, CARD_LOGIN);
                clearRegisterFields();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
           
            JOptionPane.showMessageDialog(this, "Database Error during registration: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearRegisterFields() {
        txtRegisterUsername.setText("");
        txtRegisterEmail.setText("");
        txtRegisterPassword.setText("");
        txtConfirmPassword.setText("");
    }

    // =================================================================
    // UTILS DE STYLE
    // =================================================================
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setForeground(TEXT_COLOR);
        field.setText(placeholder); 
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) field.setText("");
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) field.setText(placeholder);
            }
        });
        return field;
    }
    
    private JPasswordField createStyledPasswordField(String placeholder) {
         JPasswordField field = new JPasswordField();
         field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setForeground(TEXT_COLOR);
        field.setEchoChar((char) 0); 
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('*'); 
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder); 
                }
            }
        });
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(BACKGROUND_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private void styleLinkButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(PRIMARY_COLOR);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    **
     * @param args the command line arguments
     */
    /*
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
            java.util.logging.Logger.getLogger(LoginRegisterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginRegisterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginRegisterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginRegisterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        * Create and display the form */
    /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginRegisterView().setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

