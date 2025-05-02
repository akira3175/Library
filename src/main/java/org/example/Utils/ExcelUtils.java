package org.example.Utils;

import org.example.DTO.SanPhamDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelUtils {
        public void xuatDanhSachSanPhamRaExcel(List<SanPhamDTO> danhSachSanPham, JTable table) {
        // Tạo một workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh Sách Sản Phẩm");

        // Tạo style cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {
            "Mã Sản Phẩm", "Loại Sản Phẩm", "Tên Sản Phẩm", "Nhà Sản Xuất",
            "Số Lượng", "Giá Vốn", "Giá Lời", "Trạng Thái"
        };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Tạo style cho dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        // Đổ dữ liệu vào sheet
        int rowNum = 1;
        for (SanPhamDTO sanPham : danhSachSanPham) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sanPham.getMaSanPham());
            row.createCell(1).setCellValue(sanPham.getTenLoaiSanPham() != null ? sanPham.getTenLoaiSanPham() : "");
            row.createCell(2).setCellValue(sanPham.getTenSanPham());
            row.createCell(3).setCellValue(sanPham.getNhaSanXuat());
            row.createCell(4).setCellValue(sanPham.getSoLuong());
            row.createCell(5).setCellValue(sanPham.getGiaVon());
            row.createCell(6).setCellValue(sanPham.getGiaLoi());
            row.createCell(7).setCellValue(sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động");

            // Áp dụng style cho các ô dữ liệu
            for (int i = 0; i < columns.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Mở JFileChooser để chọn vị trí lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));
        int userSelection = fileChooser.showSaveDialog(table);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
