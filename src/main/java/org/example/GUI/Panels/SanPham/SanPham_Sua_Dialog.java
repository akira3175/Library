/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package org.example.GUI.Panels.SanPham;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.example.BUS.SanPhamBUS;
import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;

/**
 *
 * @author dtoan
 */
public class SanPham_Sua_Dialog extends javax.swing.JDialog {

    private JTable tbSanPham;
    private SanPhamDAO loaiSanPhamDAO;
    private SanPhamDAO nhaSanXuatDAO;
    private SanPhamBUS sanPhamBUS;
    private SanPhamDTO sanPham;

    public SanPham_Sua_Dialog(java.awt.Frame parent, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham, SanPhamDTO sanPham) {
        super(parent, modal);
        this.loaiSanPhamDAO = new SanPhamDAO();
        this.nhaSanXuatDAO = new SanPhamDAO();
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
        this.sanPham = sanPham;
        initComponents();
        taiDuLieuLoaiSanPham();
        taiDuLieuNhaSanXuat();
        hienThiDuLieuSanPham();
        setLocationRelativeTo(null);
    }

    private void hienThiDuLieuSanPham() {
        SanPham_Sua_txt_MaSanPham.setText(String.valueOf(sanPham.getMaSanPham()));
        SanPham_Sua_txt_MaSanPham.setEditable(false);
        SanPham_Sua_cbo_LoaiSanPham.setSelectedItem(sanPham.getTenLoaiSanPham());
        SanPham_Sua_txt_AnhSanPham.setText(sanPham.getAnhSanPhamURL());
        SanPham_Sua_txt_TenSanPham.setText(sanPham.getTenSanPham());
        SanPham_Sua_cbo_NhaSanXuat.setSelectedItem(sanPham.getNhaSanXuat());
        SanPham_Sua_txt_SoLuong.setText(String.valueOf(sanPham.getSoLuong()));
        SanPham_Sua_txt_GiaVon.setText(String.valueOf(sanPham.getGiaVon()));
        SanPham_Sua_txt_GiaLoi.setText(String.valueOf(sanPham.getGiaLoi()));
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
        jLabel1 = new javax.swing.JLabel();
        SanPham_Sua_txt_MaSanPham = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SanPham_Sua_cbo_LoaiSanPham = new javax.swing.JComboBox<>();
        SanPham_Sua_txt_AnhSanPham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        SanPham_Sua_txt_TenSanPham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SanPham_Sua_cbo_NhaSanXuat = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        SanPham_Sua_txt_SoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        SanPham_Sua_txt_GiaVon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        SanPham_Sua_txt_GiaLoi = new javax.swing.JTextField();
        SanPham_btn_Sua = new javax.swing.JButton();
        SanPham_btn_Huy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setMinimumSize(new java.awt.Dimension(32767, 32767));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm");

        SanPham_Sua_txt_MaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_MaSanPham.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Loại sản phẩm");

        SanPham_Sua_cbo_LoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        SanPham_Sua_txt_AnhSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_AnhSanPham.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ảnh sản phẩm URL");

        SanPham_Sua_txt_TenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_TenSanPham.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên sản phẩm");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nhà sản xuất");

        SanPham_Sua_cbo_NhaSanXuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Số lượng");

        SanPham_Sua_txt_SoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_SoLuong.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Giá vốn");

        SanPham_Sua_txt_GiaVon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_GiaVon.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Giá lời");

        SanPham_Sua_txt_GiaLoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_Sua_txt_GiaLoi.setToolTipText("");

        SanPham_btn_Sua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_btn_Sua.setText("Xác nhận");
        SanPham_btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SanPham_btn_SuaActionPerformed(evt);
            }
        });

        SanPham_btn_Huy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SanPham_btn_Huy.setText("Hủy");
        SanPham_btn_Huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SanPham_btn_HuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SanPham_Sua_txt_GiaVon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SanPham_Sua_txt_MaSanPham, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SanPham_Sua_cbo_LoaiSanPham, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SanPham_Sua_txt_AnhSanPham, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SanPham_Sua_txt_TenSanPham, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SanPham_Sua_cbo_NhaSanXuat, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SanPham_Sua_txt_SoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SanPham_Sua_txt_GiaLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SanPham_btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SanPham_btn_Huy, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(94, 94, 94))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(SanPham_Sua_txt_MaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(SanPham_Sua_cbo_LoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_Sua_txt_AnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_Sua_txt_TenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(SanPham_Sua_cbo_NhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_Sua_txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_Sua_txt_GiaVon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_Sua_txt_GiaLoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SanPham_btn_Huy)
                    .addComponent(SanPham_btn_Sua))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SanPham_btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SanPham_btn_SuaActionPerformed
        try {
            if (SanPham_Sua_txt_SoLuong.getText().trim().isEmpty()
                    || SanPham_Sua_txt_GiaVon.getText().trim().isEmpty()
                    || SanPham_Sua_txt_GiaLoi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường số!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sanPham.setMaLoaiSanPham(layMaLoaiSanPhamTuTen(SanPham_Sua_cbo_LoaiSanPham.getSelectedItem().toString()));
            if (sanPham.getMaLoaiSanPham() == -1) {
                JOptionPane.showMessageDialog(this, "Loại sản phẩm không hợp lệ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sanPham.setAnhSanPhamURL(SanPham_Sua_txt_AnhSanPham.getText().trim());
            sanPham.setTenSanPham(SanPham_Sua_txt_TenSanPham.getText().trim());
            sanPham.setNhaSanXuat(SanPham_Sua_cbo_NhaSanXuat.getSelectedItem().toString());
            sanPham.setSoLuong(Integer.parseInt(SanPham_Sua_txt_SoLuong.getText().trim()));
            sanPham.setGiaVon(Double.parseDouble(SanPham_Sua_txt_GiaVon.getText().trim()));
            sanPham.setGiaLoi(Double.parseDouble(SanPham_Sua_txt_GiaLoi.getText().trim()));

            if (sanPhamBUS.suaSanPham(sanPham)) {
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                sanPhamBUS.hienThiSanPhamLenTable(tbSanPham);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho các trường số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SanPham_btn_SuaActionPerformed

    private void SanPham_btn_HuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SanPham_btn_HuyActionPerformed
        dispose();
    }//GEN-LAST:event_SanPham_btn_HuyActionPerformed
    private int layMaLoaiSanPhamTuTen(String tenLoaiSanPham) {
        List<SanPhamDTO> danhSachLoai = loaiSanPhamDAO.layDanhSachLoaiSanPham();
        for (SanPhamDTO loai : danhSachLoai) {
            if (loai.getTenLoaiSanPham().equals(tenLoaiSanPham)) {
                return loai.getMaLoaiSanPham();
            }
        }
        return -1;
    }

    private void taiDuLieuNhaSanXuat() {
        List<SanPhamDTO> danhSachNhaSanXuat = nhaSanXuatDAO.layDanhSachNhaSanXuat();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (SanPhamDTO nhaSX : danhSachNhaSanXuat) {
            model.addElement(nhaSX.getTenNhaCungCap());
        }
        SanPham_Sua_cbo_NhaSanXuat.setModel(model);
    }

    private void taiDuLieuLoaiSanPham() {
        List<SanPhamDTO> danhSachLoaiSanPham = loaiSanPhamDAO.layDanhSachLoaiSanPham();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (SanPhamDTO loai : danhSachLoaiSanPham) {
            model.addElement(loai.getTenLoaiSanPham());
        }
        SanPham_Sua_cbo_LoaiSanPham.setModel(model);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SanPham_Sua_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPham_Sua_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPham_Sua_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPham_Sua_Dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            SanPhamBUS sanPhamBUS = new SanPhamBUS();
            JTable tbSanPham = new JTable();
            SanPhamDTO sanPham = new SanPhamDTO();
            SanPham_Sua_Dialog dialog = new SanPham_Sua_Dialog(new javax.swing.JFrame(), true, sanPhamBUS, tbSanPham, sanPham);
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SanPham_Sua_cbo_LoaiSanPham;
    private javax.swing.JComboBox<String> SanPham_Sua_cbo_NhaSanXuat;
    private javax.swing.JTextField SanPham_Sua_txt_AnhSanPham;
    private javax.swing.JTextField SanPham_Sua_txt_GiaLoi;
    private javax.swing.JTextField SanPham_Sua_txt_GiaVon;
    private javax.swing.JTextField SanPham_Sua_txt_MaSanPham;
    private javax.swing.JTextField SanPham_Sua_txt_SoLuong;
    private javax.swing.JTextField SanPham_Sua_txt_TenSanPham;
    private javax.swing.JButton SanPham_btn_Huy;
    private javax.swing.JButton SanPham_btn_Sua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
