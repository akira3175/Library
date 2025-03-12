package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.DAO.DatabaseConnection;
import org.example.GUI.Auth.LoginForm;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Connect successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Kết nối Database thất bại!");
            System.out.println("Connect failed!");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginForm().setVisible(true);
        });
    }
}