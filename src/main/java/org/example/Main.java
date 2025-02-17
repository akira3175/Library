package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.GUI.Auth.LoginForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
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