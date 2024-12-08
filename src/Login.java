/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author lap_h
 */
public class Login extends javax.swing.JFrame {

    private Map<String, CUser> userDatabase;
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setTitle("Cinema Project");

        userDatabase = loadUserDatabase("src/BazaDannych/User.txt");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Admin = new javax.swing.JButton();
        TextAdmin = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Cashier1 = new javax.swing.JButton();
        TextCashier1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setForeground(new java.awt.Color(72, 61, 139));
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jPanel2.setBackground(new java.awt.Color(72, 61, 139));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(75, 0, 130));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setMinimumSize(new java.awt.Dimension(461, 489));
        jPanel2.setPreferredSize(null);

        jPanel2.setLayout(new java.awt.BorderLayout());

        Admin.setBackground(new java.awt.Color(72, 61, 139));
        Admin.setForeground(new java.awt.Color(72, 61, 139));
        Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/administrator.jpg"))); // NOI18N
        Admin.setAlignmentX(0.5F);
        Admin.setAutoscrolls(true);
        Admin.setBorder(null);
        Admin.setBorderPainted(false);
        Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Admin.setMaximumSize(new java.awt.Dimension(457, 457));
        Admin.setMinimumSize(new java.awt.Dimension(457, 457));
        Admin.setName(""); // NOI18N
        Admin.setOpaque(true);
        Admin.setPreferredSize(new java.awt.Dimension(456, 457));

        Admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminActionPerformed(evt);
            }
        });
        jPanel2.add(Admin, java.awt.BorderLayout.CENTER);

        TextAdmin.setBackground(new java.awt.Color(72, 61, 139));
        TextAdmin.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        TextAdmin.setForeground(new java.awt.Color(0, 0, 0));
        TextAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextAdmin.setLabelFor(Admin);
        TextAdmin.setText("Administrator");
        TextAdmin.setToolTipText("");
        TextAdmin.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        TextAdmin.setFocusable(false);
        TextAdmin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(TextAdmin, java.awt.BorderLayout.PAGE_END);
        TextAdmin.getAccessibleContext().setAccessibleName("");
        TextAdmin.getAccessibleContext().setAccessibleParent(Admin);

        jPanel4.setBackground(new java.awt.Color(72, 61, 139));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setForeground(new java.awt.Color(75, 0, 130));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setPreferredSize(null);
        jPanel4.setLayout(new java.awt.BorderLayout());

        Cashier1.setBackground(new java.awt.Color(72, 61, 139));
        Cashier1.setForeground(new java.awt.Color(72, 61, 139));
        Cashier1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Kasjer.jpg"))); // NOI18N
        Cashier1.setAlignmentX(0.5F);
        Cashier1.setAutoscrolls(true);
        Cashier1.setBorder(null);
        Cashier1.setBorderPainted(false);
        Cashier1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Cashier1.setMaximumSize(new java.awt.Dimension(457, 457));
        Cashier1.setMinimumSize(new java.awt.Dimension(457, 457));
        Cashier1.setPreferredSize(new java.awt.Dimension(456, 457));

        Cashier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cashier1ActionPerformed(evt);
            }
        });
        jPanel4.add(Cashier1, java.awt.BorderLayout.CENTER);

        TextCashier1.setBackground(new java.awt.Color(72, 61, 139));
        TextCashier1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        TextCashier1.setForeground(new java.awt.Color(0, 0, 0));
        TextCashier1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextCashier1.setLabelFor(Cashier1);
        TextCashier1.setText("Kasjer");
        TextCashier1.setToolTipText("");
        TextCashier1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        TextCashier1.setFocusable(false);
        TextCashier1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TextCashier1.setMaximumSize(new java.awt.Dimension(156, 28));
        TextCashier1.setMinimumSize(new java.awt.Dimension(156, 28));
        TextCashier1.setPreferredSize(new java.awt.Dimension(156, 28));
        jPanel4.add(TextCashier1, java.awt.BorderLayout.PAGE_END);
        TextCashier1.getAccessibleContext().setAccessibleName("");
        TextCashier1.getAccessibleContext().setAccessibleParent(Cashier1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 426, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addGap(247, 247, 247))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(260, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(191, 191, 191))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void Cashier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cashier1ActionPerformed
        showPasswordDialog("Kasjer");
    }//GEN-LAST:event_Cashier1ActionPerformed


    private void AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminActionPerformed
        showPasswordDialog("Admin");
    }//GEN-LAST:event_AdminActionPerformed


    private void showPasswordDialog(String userType) {
        JPasswordField passwordField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Wpisz hasło dla: " + userType));
        panel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(null, panel, userType + " Logowanie", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            char[] password = passwordField.getPassword();
            String passwordText = new String(password);

                if (validatePassword(userType, passwordText)) {
                    if (userType.equals("Admin")) {
                        dispose();
                    } else if (userType.equals("Kasjer")) {
                        Cashier cashierWindow = new Cashier();
                        cashierWindow.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Niepoprawne hasło.");
                }
        }
    }

    private boolean validatePassword(String userType, String password) {
        CUser user = userDatabase.get(userType);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    private Map<String, CUser> loadUserDatabase(String fileName) {
        Map<String, CUser> userDatabase = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    if (username.equals("Admin")) {
                        userDatabase.put(username, new CAdministrator(username, password));
                        System.out.println(password);
                    } else if (username.equals("Kasjer")) {
                        userDatabase.put(username, new CCashier(username, password));
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd odczytu bazy użytkowników.");
        }
        return userDatabase;
    }
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Admin;
    private javax.swing.JButton Cashier1;
    private javax.swing.JLabel TextAdmin;
    private javax.swing.JLabel TextCashier1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}