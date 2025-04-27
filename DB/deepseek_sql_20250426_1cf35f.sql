-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2025 at 10:47 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MaChiTietHoaDon` int(11) NOT NULL,
  `MaHoaDon` int(11) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `DonGia` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`MaChiTietHoaDon`, `MaHoaDon`, `MaSanPham`, `DonGia`, `SoLuong`) VALUES
(1, 1, 578, 20000, 2),
(2, 1, 628, 20000, 3),
(3, 2, 34245, 10000, 5),
(4, 2, 34246, 13000, 4),
(5, 2, 34247, 14000, 3),
(6, 3, 789, 7000, 4),
(7, 3, 790, 7500, 5),
(8, 4, 578, 20000, 3),
(9, 4, 628, 20000, 3),
(10, 5, 34248, 15000, 6),
(11, 5, 34249, 21000, 3);

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MaChiTietPhieuNhap` int(11) NOT NULL,
  `MaPhieuNhap` int(11) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `DonGia` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MaChiTietPhieuNhap`, `MaPhieuNhap`, `MaSanPham`, `DonGia`, `SoLuong`) VALUES
(1, 1, 578, 12000, 50),
(2, 1, 628, 11000, 40),
(3, 2, 34245, 7000, 30),
(4, 2, 34246, 9000, 40),
(5, 3, 34247, 10000, 20),
(6, 3, 34248, 11000, 30),
(7, 4, 789, 5000, 60),
(8, 4, 790, 6000, 50),
(9, 5, 34249, 15000, 20),
(10, 5, 578, 12000, 30);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `MaHoaDon` int(11) NOT NULL,
  `MaNguoiDung` int(11) NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaKhuyenMai` int(11) NOT NULL,
  `NgayLap` datetime NOT NULL,
  `TienGiam` int(11) NOT NULL,
  `ThanhTien` int(11) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hoadon`
--

INSERT INTO `hoadon` (`MaHoaDon`, `MaNguoiDung`, `MaKhachHang`, `MaKhuyenMai`, `NgayLap`, `TienGiam`, `ThanhTien`, `TrangThai`) VALUES
(1, 1, 1, 1, '2025-04-10 09:15:22', 10000, 90000, 1),
(2, 1, 2, 2, '2025-04-11 14:30:45', 20000, 180000, 1),
(3, 1, 3, 1, '2025-04-12 16:45:12', 10000, 95000, 1),
(4, 1, 4, NULL, '2025-04-13 10:20:33', 0, 120000, 1),
(5, 1, 5, 3, '2025-04-14 17:55:18', 15000, 135000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKhachHang` int(11) NOT NULL,
  `HoTen` varchar(255) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `DiaChi` varchar(255) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MaKhachHang`, `HoTen`, `SoDienThoai`, `DiaChi`, `TrangThai`) VALUES
(1, 'Trần Văn Bình', '0987654321', '123 Đường Lê Lợi, Quận 1, TP.HCM', 1),
(2, 'Nguyễn Thị Anh', '0912345678', '456 Đường Nguyễn Huệ, Quận 1, TP.HCM', 1),
(3, 'Lê Hoàng Minh', '0978123456', '789 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM', 1),
(4, 'Phạm Thị Hương', '0905123789', '321 Đường Trần Hưng Đạo, Quận 5, TP.HCM', 1),
(5, 'Võ Đức Duy', '0936987452', '654 Đường Lý Thường Kiệt, Quận 10, TP.HCM', 1);

-- --------------------------------------------------------

--
-- Table structure for table `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `MaKhuyenMai` int(11) NOT NULL,
  `TenKhuyenMai` varchar(45) NOT NULL,
  `DieuKienHoaDon` int(11) NOT NULL,
  `NgayBatDau` datetime NOT NULL,
  `NgayKetThuc` datetime NOT NULL,
  `SoTienKhuyenMai` int(11) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khuyenmai`
--

INSERT INTO `khuyenmai` (`MaKhuyenMai`, `TenKhuyenMai`, `DieuKienHoaDon`, `NgayBatDau`, `NgayKetThuc`, `SoTienKhuyenMai`, `TrangThai`) VALUES
(1, 'Giảm 10K cho hóa đơn 100K', 100000, '2025-04-01 00:00:00', '2025-04-30 23:59:59', 10000, 1),
(2, 'Giảm 20K cho hóa đơn 200K', 200000, '2025-04-01 00:00:00', '2025-04-30 23:59:59', 20000, 1),
(3, 'Khuyến mãi tháng 4', 150000, '2025-04-15 00:00:00', '2025-04-25 23:59:59', 15000, 1),
(4, 'Giảm giá cuối tuần', 50000, '2025-04-19 00:00:00', '2025-04-21 23:59:59', 5000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `MaLoaiSanPham` int(11) NOT NULL,
  `TenLoaiSanPham` varchar(100) NOT NULL,
  `Mota` varchar(255) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `loaisanpham`
--

INSERT INTO `loaisanpham` (`MaLoaiSanPham`, `TenLoaiSanPham`, `Mota`, `TrangThai`) VALUES
(123, 'Nước có ga', 'thức uống có ga', 1),
(234, 'Nước lọc', 'nước lọc', 1),
(567, 'Sữa', 'Sữa', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nguoidung`
--

CREATE TABLE `nguoidung` (
  `MaNguoiDung` int(11) NOT NULL,
  `TenDangNhap` varchar(45) NOT NULL,
  `MatKhau` varchar(45) NOT NULL,
  `HoTen` varchar(45) DEFAULT NULL,
  `NgaySinh` varchar(45) DEFAULT NULL,
  `GioiTinh` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `NgayVaoLam` datetime DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `ConHoatDong` tinyint(4) DEFAULT 1,
  `MaVaiTro` int(11) NOT NULL DEFAULT 1,
  `AvatarURL` varchar(45) DEFAULT NULL,
  `DiaChi` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguoidung`
--

INSERT INTO `nguoidung` (`MaNguoiDung`, `TenDangNhap`, `MatKhau`, `HoTen`, `NgaySinh`, `GioiTinh`, `Email`, `NgayVaoLam`, `SoDienThoai`, `ConHoatDong`, `MaVaiTro`, `AvatarURL`, `DiaChi`) VALUES
(1, 'admin', '1234', 'Nguyễn Văn A', '2004-04-17', 'Nam', 'vana@gmail.com', '2025-03-03 00:00:00', '0909123456', 1, 1, 'avatar_admin.jpg', '123 Đường ABC, TP.HCM'),
(2, 'manager', '1234', 'Trần Thị B', '1995-08-20', 'Nữ', 'manager@shop.com', '2025-03-10 00:00:00', '0918123456', 1, 2, 'avatar_manager.jpg', '456 Đường XYZ, TP.HCM'),
(3, 'staff1', '1234', 'Lê Văn C', '1998-11-15', 'Nam', 'staff1@shop.com', '2025-03-15 00:00:00', '0987123456', 1, 3, 'avatar_staff1.jpg', '789 Đường DEF, TP.HCM'),
(4, 'staff2', '1234', 'Phạm Thị D', '1997-05-25', 'Nữ', 'staff2@shop.com', '2025-03-20 00:00:00', '0976123456', 1, 3, 'avatar_staff2.jpg', '321 Đường GHI, TP.HCM');

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNhaCungCap` int(11) NOT NULL,
  `TenNhaCungCap` varchar(100) NOT NULL,
  `DiaChi` varchar(255) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `Fax` varchar(11) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNhaCungCap`, `TenNhaCungCap`, `DiaChi`, `SoDienThoai`, `Fax`, `TrangThai`) VALUES
(567, 'Hiệp Thạnh', 'Tây Ninh', '232321556', 'dfsdfsdf', 1),
(764, 'Bỉnh Tân', 'HCM', '0399473743', '234234234', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPhieuNhap` int(11) NOT NULL,
  `MaNguoiDung` int(11) NOT NULL,
  `MaNhaCungCap` int(11) NOT NULL,
  `ThoiGianLap` datetime NOT NULL,
  `TrangThai` tinyint(4) GENERATED ALWAYS AS (1) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`MaPhieuNhap`, `MaNguoiDung`, `MaNhaCungCap`, `ThoiGianLap`) VALUES
(1, 1, 567, '2025-04-01 08:00:00'),
(2, 1, 764, '2025-04-05 10:30:00'),
(3, 1, 567, '2025-04-10 09:15:00'),
(4, 1, 764, '2025-04-15 14:20:00'),
(5, 1, 567, '2025-04-20 11:45:00');

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--

CREATE TABLE `quyen` (
  `MaQuyen` int(11) NOT NULL,
  `TenQuyen` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quyen`
--

INSERT INTO `quyen` (`MaQuyen`, `TenQuyen`) VALUES
(1, 'Quản lý người dùng'),
(2, 'Quản lý sản phẩm'),
(3, 'Quản lý hóa đơn'),
(4, 'Quản lý nhập hàng'),
(5, 'Quản lý khách hàng'),
(6, 'Quản lý khuyến mãi');

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSanPham` int(11) NOT NULL,
  `MaLoaiSanPham` int(11) NOT NULL,
  `AnhSanPhamURL` varchar(100) DEFAULT NULL,
  `TenSanPham` varchar(255) NOT NULL,
  `NhaSanXuat` varchar(255) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1,
  `sanphamcol` varchar(45) DEFAULT NULL,
  `SoLuong` int(11) NOT NULL,
  `GiaVon` double NOT NULL DEFAULT 0,
  `GiaLoi` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`MaSanPham`, `MaLoaiSanPham`, `AnhSanPhamURL`, `TenSanPham`, `NhaSanXuat`, `TrangThai`, `sanphamcol`, `SoLuong`, `GiaVon`, `GiaLoi`) VALUES
(578, 123, 'images/coca_cola.jpg', 'Coca Cola', 'Coca Cola', 1, NULL, 100, 12000, 8000),
(628, 123, 'images/pepsi.jpg', 'Pepsi', 'PepsiCo', 1, NULL, 80, 11000, 7000),
(789, 234, 'images/aquafina.jpg', 'Nước khoáng Aquafina', 'PepsiCo', 1, NULL, 120, 5000, 2000),
(790, 234, 'images/lavie.jpg', 'Nước tinh khiết Lavie', 'Nestlé', 1, NULL, 90, 6000, 2500),
(34245, 567, 'images/sua_fami.jpg', 'Sữa fami', 'Vinamilk', 1, NULL, 50, 7000, 3000),
(34246, 567, 'images/sua_milo.jpg', 'Sữa milo', 'Nestlé', 1, NULL, 70, 9000, 4000),
(34247, 567, 'images/sua_dalatmilk.jpg', 'Sữa Đà Lạt Milk', 'Dalatmilk', 1, NULL, 40, 10000, 4000),
(34248, 567, 'images/sua_vinamilk.jpg', 'Sữa tươi Vinamilk', 'Vinamilk', 1, NULL, 60, 11000, 3500),
(34249, 567, 'images/sua_ensure.jpg', 'Sữa Ensure Gold', 'Abbott', 1, NULL, 30, 15000, 6000);

-- --------------------------------------------------------

--
-- Table structure for table `vaitro`
--

CREATE TABLE `vaitro` (
  `MaVaiTro` int(11) NOT NULL,
  `TenVaiTro` varchar(45) NOT NULL,
  `MoTa` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vaitro`
--

INSERT INTO `vaitro` (`MaVaiTro`, `TenVaiTro`, `MoTa`) VALUES
(1, 'Quản trị viên', 'Quản trị viên toàn quyền trên hệ thống'),
(2, 'Quản lý', 'Quản lý nhân sự, hệ thống'),
(3, 'Nhân viên Bán hàng', 'Nhân viên bán hàng ');

-- --------------------------------------------------------

--
-- Table structure for table `vaitro_quyen`
--

CREATE TABLE `vaitro_quyen` (
  `MaVaiTro` int(11) NOT NULL,
  `MaQuyen` int(11) NOT NULL,
  `ChucNang` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vaitro_quyen`
--

INSERT INTO `vaitro_quyen` (`MaVaiTro`, `MaQuyen`, `ChucNang`) VALUES
(1, 1, 'Toàn quyền'),
(1, 2, 'Toàn quyền'),
(1, 3, 'Toàn quyền'),
(1, 4, 'Toàn quyền'),
(1, 5, 'Toàn quyền'),
(1, 6, 'Toàn quyền'),
(2, 2, 'Xem, Thêm, Sửa'),
(2, 3, 'Xem, Thêm, Sửa'),
(2, 4, 'Xem, Thêm'),
(2, 5, 'Xem, Thêm, Sửa'),
(3, 3, 'Xem, Thêm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaChiTietHoaDon`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MaChiTietPhieuNhap`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHoaDon`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKhachHang`);

--
-- Indexes for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`MaKhuyenMai`),
  ADD UNIQUE KEY `TenKhuyenMai_UNIQUE` (`TenKhuyenMai`);

--
-- Indexes for table `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`MaLoaiSanPham`);

--
-- Indexes for table `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`MaNguoiDung`),
  ADD UNIQUE KEY `TenDangNhap_UNIQUE` (`TenDangNhap`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNhaCungCap`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPhieuNhap`);

--
-- Indexes for table `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MaQuyen`),
  ADD UNIQUE KEY `TenQuyen_UNIQUE` (`TenQuyen`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSanPham`);

--
-- Indexes for table `vaitro`
--
ALTER TABLE `vaitro`
  ADD PRIMARY KEY (`MaVaiTro`),
  ADD UNIQUE KEY `TenVaiTro_UNIQUE` (`TenVaiTro`);

--
-- Indexes for table `vaitro_quyen`
--
ALTER TABLE `vaitro_quyen`
  ADD PRIMARY KEY (`MaVaiTro`,`MaQuyen`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `MaChiTietHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  MODIFY `MaChiTietPhieuNhap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKhachHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `MaKhuyenMai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `MaLoaiSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=568;

--
-- AUTO_INCREMENT for table `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `MaNguoiDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNhaCungCap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=765;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MaPhieuNhap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `quyen`
--
ALTER TABLE `quyen`
  MODIFY `MaQuyen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34250;

--
-- AUTO_INCREMENT for table `vaitro`
--
ALTER TABLE `vaitro`
  MODIFY `MaVaiTro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;