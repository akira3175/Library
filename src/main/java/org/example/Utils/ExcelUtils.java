package org.example.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.KhuyenMai;

public class ExcelUtils {

    public void xuatDanhSachSanPhamRaExcel(List<SanPhamDTO> danhSachSanPham, JTable table) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách sản phẩm");

        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Ảnh sản phẩm URL",
            "Số lượng", "Giá vốn", "Giá lời", "Trạng thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

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

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu tệp Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));

        try {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập thư mục mặc định: " + e.getMessage());
            fileChooser.setCurrentDirectory(null);
        }

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

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

    public List<SanPhamDTO> nhapDanhSachSanPhamTuExcel(File file) throws Exception {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new IllegalArgumentException("File Excel không có sheet nào.");
            }
            boolean skipHeader = true;

            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                if (row == null || row.getCell(0) == null) {
                    System.err.println("Dòng " + (row.getRowNum() + 1) + " trống, bỏ qua.");
                    continue;
                }

                SanPhamDTO sanPham = new SanPhamDTO();
                try {
                    Cell maSanPhamCell = row.getCell(0);
                    if (maSanPhamCell == null || maSanPhamCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Mã sản phẩm không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    sanPham.setMaSanPham((int) maSanPhamCell.getNumericCellValue());

                    Cell tenLoaiCell = row.getCell(1);
                    sanPham.setTenLoaiSanPham(tenLoaiCell != null ? tenLoaiCell.getStringCellValue() : "");

                    Cell tenSanPhamCell = row.getCell(2);
                    sanPham.setTenSanPham(tenSanPhamCell != null ? tenSanPhamCell.getStringCellValue() : "");

                    Cell anhSanPhamCell = row.getCell(3);
                    sanPham.setAnhSanPhamURL(anhSanPhamCell != null ? anhSanPhamCell.getStringCellValue() : "");

                    Cell soLuongCell = row.getCell(4);
                    if (soLuongCell == null || soLuongCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Số lượng không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    sanPham.setSoLuong((int) soLuongCell.getNumericCellValue());

                    Cell giaVonCell = row.getCell(5);
                    if (giaVonCell == null || giaVonCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Giá vốn không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    sanPham.setGiaVon((int) giaVonCell.getNumericCellValue());

                    Cell giaLoiCell = row.getCell(6);
                    if (giaLoiCell == null || giaLoiCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Giá lời không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    sanPham.setGiaLoi((int) giaLoiCell.getNumericCellValue());

                    Cell trangThaiCell = row.getCell(7);
                    String trangThaiStr = trangThaiCell != null ? trangThaiCell.getStringCellValue() : "";
                    sanPham.setTrangThai(trangThaiStr.equals("Hoạt động"));

                    danhSachSanPham.add(sanPham);
                } catch (Exception e) {
                    System.err.println("Lỗi khi đọc dòng " + (row.getRowNum() + 1) + ": " + e.getMessage());
                    continue;
                }
            }
        }
        return danhSachSanPham;
    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void xuatDanhSachKhuyenMaiRaExcel(List<KhuyenMai> danhSachKhuyenMai, JTable table) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách khuyến mãi");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã khuyến mãi", "Tên khuyến mãi", "Số tiền khuyến mãi",
            "Điều kiện hóa đơn", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Populate data rows
        int rowNum = 1;
        for (KhuyenMai khuyenMai : danhSachKhuyenMai) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(khuyenMai.getMaKhuyenMai());
            row.createCell(1).setCellValue(khuyenMai.getTenKhuyenMai());
            row.createCell(2).setCellValue(khuyenMai.getSoTienKhuyenMai());
            row.createCell(3).setCellValue(khuyenMai.getDieuKienHoaDon());
            row.createCell(4).setCellValue(dateFormat.format(khuyenMai.getNgayBatDau()));
            row.createCell(5).setCellValue(dateFormat.format(khuyenMai.getNgayKetThuc()));
            row.createCell(6).setCellValue(khuyenMai.getTrangThai() ? "Hoạt động" : "Không hoạt động");
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Show file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu tệp Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("DanhSachKhuyenMai.xlsx"));

        try {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập thư mục mặc định: " + e.getMessage());
            fileChooser.setCurrentDirectory(null);
        }

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

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

    public List<KhuyenMai> nhapDanhSachKhuyenMaiTuExcel(File file) throws Exception {
        List<KhuyenMai> danhSachKhuyenMai = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new IllegalArgumentException("File Excel không có sheet nào.");
            }
            boolean skipHeader = true;

            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                if (row == null || row.getCell(0) == null) {
                    System.err.println("Dòng " + (row.getRowNum() + 1) + " trống, bỏ qua.");
                    continue;
                }

                KhuyenMai khuyenMai = new KhuyenMai();
                try {
                    // Mã khuyến mãi
                    Cell maKhuyenMaiCell = row.getCell(0);
                    if (maKhuyenMaiCell != null && maKhuyenMaiCell.getCellType() == CellType.NUMERIC) {
                        khuyenMai.setMaKhuyenMai((int) maKhuyenMaiCell.getNumericCellValue());
                    }

                    // Tên khuyến mãi
                    Cell tenKhuyenMaiCell = row.getCell(1);
                    khuyenMai.setTenKhuyenMai(tenKhuyenMaiCell != null ? tenKhuyenMaiCell.getStringCellValue() : "");

                    // Số tiền khuyến mãi
                    Cell soTienKhuyenMaiCell = row.getCell(2);
                    if (soTienKhuyenMaiCell == null || soTienKhuyenMaiCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Số tiền khuyến mãi không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    khuyenMai.setSoTienKhuyenMai((int) soTienKhuyenMaiCell.getNumericCellValue());

                    // Điều kiện hóa đơn
                    Cell dieuKienHoaDonCell = row.getCell(3);
                    if (dieuKienHoaDonCell == null || dieuKienHoaDonCell.getCellType() != CellType.NUMERIC) {
                        throw new IllegalArgumentException("Điều kiện hóa đơn không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    khuyenMai.setDieuKienHoaDon((int) dieuKienHoaDonCell.getNumericCellValue());

                    // Ngày bắt đầu
                    Cell ngayBatDauCell = row.getCell(4);
                    if (ngayBatDauCell == null) {
                        throw new IllegalArgumentException("Ngày bắt đầu không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    khuyenMai.setNgayBatDau(dateFormat.parse(ngayBatDauCell.getStringCellValue()));

                    // Ngày kết thúc
                    Cell ngayKetThucCell = row.getCell(5);
                    if (ngayKetThucCell == null) {
                        throw new IllegalArgumentException("Ngày kết thúc không hợp lệ tại dòng " + (row.getRowNum() + 1));
                    }
                    khuyenMai.setNgayKetThuc(dateFormat.parse(ngayKetThucCell.getStringCellValue()));

                    // Trạng thái
                    Cell trangThaiCell = row.getCell(6);
                    String trangThaiStr = trangThaiCell != null ? trangThaiCell.getStringCellValue() : "";
                    khuyenMai.setTrangThai(trangThaiStr.equals("Hoạt động"));

                    danhSachKhuyenMai.add(khuyenMai);
                } catch (Exception e) {
                    System.err.println("Lỗi khi đọc dòng " + (row.getRowNum() + 1) + ": " + e.getMessage());
                    continue;
                }
            }
        }
        return danhSachKhuyenMai;
    }
}
