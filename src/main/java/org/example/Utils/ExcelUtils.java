package org.example.Utils;

import org.example.DTO.SanPhamDTO;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    public void xuatDanhSachSanPhamRaExcel(List<SanPhamDTO> danhSachSanPham, JTable table) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachSanPham");

            // Header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Ảnh sản phẩm URL", 
                               "Số lượng", "Giá vốn", "Giá lời", "Trạng thái"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data
            int rowNum = 1;
            for (SanPhamDTO sanPham : danhSachSanPham) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sanPham.getMaSanPham());
                row.createCell(1).setCellValue(sanPham.getTenLoaiSanPham());
                row.createCell(2).setCellValue(sanPham.getTenSanPham());
                row.createCell(3).setCellValue(sanPham.getAnhSanPhamURL());
                row.createCell(4).setCellValue(sanPham.getSoLuong());
                row.createCell(5).setCellValue(sanPham.getGiaVon());
                row.createCell(6).setCellValue(sanPham.getGiaLoi());
                row.createCell(7).setCellValue(sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động");
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("DanhSachSanPham.xlsx"));
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile())) {
                    workbook.write(fileOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}