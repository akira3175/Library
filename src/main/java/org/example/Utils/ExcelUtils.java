package org.example.Utils;

import java.io.File;
import org.example.DTO.SanPhamDTO;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    public void xuatDanhSachSanPhamRaExcel(List<SanPhamDTO> danhSachSanPham, JTable table) {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách sản phẩm");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Ảnh sản phẩm URL", 
                           "Số lượng", "Giá vốn", "Giá lời", "Trạng thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Populate data rows
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

        // Initialize JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu tệp Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));

        // Set a safe initial directory (user's home directory)
        try {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập thư mục mặc định: " + e.getMessage());
            fileChooser.setCurrentDirectory(null); // Fallback to default
        }

        // Show save dialog and handle response
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            // Save the workbook to the selected file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất tệp Excel thành công!", 
                                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu tệp Excel: " + e.getMessage(), 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (Exception e) {
                    System.err.println("Lỗi khi đóng workbook: " + e.getMessage());
                }
            }
        }
    }
}