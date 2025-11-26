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
import com.view.StudentView; // Pour ouvrir la fenêtre principale après la connexion
import java.awt.*;
import java.util.Optional;
import java.sql.SQLException;
import javax.swing.*;

public class LoginRegisterView extends JFrame {

    // --- Constantes de Style ---
    private static final Color PRIMARY_COLOR = new Color(102, 0, 153); // Violet Profond
    private static final Color ACCENT_COLOR = new Color(153, 50, 204); // Violet clair pour les boutons
    private static final Color TEXT_COLOR = Color.DARK_GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final String CARD_LOGIN = "LoginCard";
    private static final String CARD_REGISTER = "RegisterCard";

    private AuthController controller;
    private JPanel cardsPanel; // Conteneur utilisant CardLayout
    private CardLayout cardLayout;

    // Champs de Connexion
    private JTextField txtLoginUsername;
    private JPasswordField txtLoginPassword;
    private JButton btnLogin;

    // Champs d'Enregistrement
    private JTextField txtRegisterUsername, txtRegisterEmail;
    private JPasswordField txtRegisterPassword, txtConfirmPassword;
    private JButton btnRegister;

    public LoginRegisterView() {
        controller = new AuthController();
        setTitle("Authentication System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        
        // Conteneur principal
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(BACKGROUND_COLOR);

        initComponents();
        addEventListeners();
        
        add(cardsPanel);
        setLocationRelativeTo(null); // Centrer la fenêtre
    }

    private void initComponents() {
        // --- 1. Création du Panneau de Connexion ---
        JPanel loginPanel = createLoginPanel();
        cardsPanel.add(loginPanel, CARD_LOGIN);

        // --- 2. Création du Panneau d'Enregistrement ---
        JPanel registerPanel = createRegisterPanel();
        cardsPanel.add(registerPanel, CARD_REGISTER);

        // Afficher la carte de connexion par défaut
        cardLayout.show(cardsPanel, CARD_LOGIN);
    }
    
    // =================================================================
    // PANNEAU DE CONNEXION
    // =================================================================

    // Dans LoginRegisterView.java, ajoutez cette méthode utilitaire
private GridBagConstraints createGbc(int gridx, int gridy, int ipadx, int ipady, int anchor) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.ipadx = ipadx; // Espacement interne horizontal
    gbc.ipady = ipady; // Espacement interne vertical
    gbc.anchor = anchor; // Position dans la cellule (CENTER, WEST, etc.)
    gbc.insets = new Insets(10, 0, 10, 0); // Marge externe
    return gbc;
}
    
    
// Dans LoginRegisterView.java
    private JPanel createLoginPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    JLabel title = new JLabel("Login", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 32));
    title.setForeground(PRIMARY_COLOR);
    
    // NOUVEAU: Utiliser GridBagLayout pour un meilleur contrôle
    JPanel form = new JPanel(new GridBagLayout()); 
    form.setBackground(BACKGROUND_COLOR);
    
    // Définir une taille préférée pour les champs de texte
    Dimension fieldSize = new Dimension(300, 45); // Largeur fixe, hauteur un peu plus grande

    txtLoginUsername = createStyledTextField("User Name");
    txtLoginUsername.setPreferredSize(fieldSize); // Appliquer la taille
    txtLoginPassword = createStyledPasswordField("Password");
    txtLoginPassword.setPreferredSize(fieldSize); // Appliquer la taille
    
    btnLogin = createStyledButton("Login", ACCENT_COLOR);
    
    JButton btnSwitchToRegister = new JButton("Register Now");
    styleLinkButton(btnSwitchToRegister);
    btnSwitchToRegister.addActionListener(e -> cardLayout.show(cardsPanel, CARD_REGISTER));

    // AJOUT DES COMPOSANTS AU FORM AVEC GridBagLayout
    GridBagConstraints gbc;

    // Username field
    gbc = createGbc(0, 0, 0, 0, GridBagConstraints.CENTER);
    form.add(txtLoginUsername, gbc);

    // Password field
    gbc = createGbc(0, 1, 0, 0, GridBagConstraints.CENTER);
    form.add(txtLoginPassword, gbc);

    // Espace
    gbc = createGbc(0, 2, 0, 0, GridBagConstraints.CENTER);
    gbc.insets = new Insets(20, 0, 20, 0); // Plus d'espace au-dessus du bouton
    form.add(Box.createVerticalStrut(1), gbc); // Un petit espaceur

    // Login button
    gbc = createGbc(0, 3, 0, 0, GridBagConstraints.CENTER);
    form.add(btnLogin, gbc);

    // Register Now link
    gbc = createGbc(0, 4, 0, 0, GridBagConstraints.CENTER);
    form.add(btnSwitchToRegister, gbc);
    
    panel.add(title, BorderLayout.NORTH);
    panel.add(form, BorderLayout.CENTER); // Centrer le panneau de formulaire

    return panel;
}

    // =================================================================
    // PANNEAU D'ENREGISTREMENT
    // =================================================================
   // Dans LoginRegisterView.java
private JPanel createRegisterPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    JLabel title = new JLabel("Register", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 32));
    title.setForeground(PRIMARY_COLOR);
    
    // NOUVEAU: Utiliser GridBagLayout
    JPanel form = new JPanel(new GridBagLayout());
    form.setBackground(BACKGROUND_COLOR);
    
    // Définir une taille préférée pour les champs de texte
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
    
    // AJOUT DES COMPOSANTS AU FORM AVEC GridBagLayout
    GridBagConstraints gbc;

    // Username field
    gbc = createGbc(0, 0, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterUsername, gbc);

    // Password field
    gbc = createGbc(0, 1, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterPassword, gbc);

    // Confirm Password field
    gbc = createGbc(0, 2, 0, 0, GridBagConstraints.CENTER);
    form.add(txtConfirmPassword, gbc);

    // Email field
    gbc = createGbc(0, 3, 0, 0, GridBagConstraints.CENTER);
    form.add(txtRegisterEmail, gbc);

    // Espace
    gbc = createGbc(0, 4, 0, 0, GridBagConstraints.CENTER);
    gbc.insets = new Insets(20, 0, 20, 0);
    form.add(Box.createVerticalStrut(1), gbc);

    // Register button
    gbc = createGbc(0, 5, 0, 0, GridBagConstraints.CENTER);
    form.add(btnRegister, gbc);

    // Back to Login link
    gbc = createGbc(0, 6, 0, 0, GridBagConstraints.CENTER);
    form.add(btnBackToLogin, gbc);

    panel.add(title, BorderLayout.NORTH);
    panel.add(form, BorderLayout.CENTER); // Centrer le panneau de formulaire
    
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
                
                // OUVRIR LA FENÊTRE PRINCIPALE
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
                // Basculer vers le panneau de connexion après l'enregistrement
                cardLayout.show(cardsPanel, CARD_LOGIN);
                clearRegisterFields();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            // Gérer spécifiquement les erreurs de duplication si elles n'ont pas été attrapées par le contrôleur
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
        field.setText(placeholder); // Placeholder simple
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
        
        // Gestion simple du placeholder (visible uniquement si le champ est vide)
        field.setEchoChar((char) 0); 
        field.setText(placeholder);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('*'); // Montrer les étoiles
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder); // Montrer le placeholder
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

