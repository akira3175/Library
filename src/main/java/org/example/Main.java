package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.BUS.NguoiDungBUS;
import org.example.DAO.DatabaseConnection;
import org.example.GUI.Auth.LoginForm;

import javax.swing.*;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            logger.info("Kết nối Database thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Kết nối Database thất bại!");
            logger.error("Kết nối Database tất bại");
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