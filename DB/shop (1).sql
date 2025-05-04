-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 28, 2025 lúc 03:07 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MaChiTietHoaDon` int(11) NOT NULL,
  `MaHoaDon` int(11) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `DonGia` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`MaChiTietHoaDon`, `MaHoaDon`, `MaSanPham`, `DonGia`, `SoLuong`) VALUES
(1, 1, 1, 120000, 2),
(2, 1, 2, 220000, 1),
(3, 2, 3, 170000, 1),
(4, 2, 4, 140000, 2),
(5, 3, 5, 160000, 1),
(6, 3, 6, 150000, 1),
(7, 4, 7, 190000, 2),
(8, 4, 8, 180000, 1),
(9, 5, 9, 210000, 1),
(10, 5, 10, 200000, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MaChiTietPhieuNhap` int(11) NOT NULL,
  `MaPhieuNhap` int(11) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `DonGia` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MaChiTietPhieuNhap`, `MaPhieuNhap`, `MaSanPham`, `DonGia`, `SoLuong`) VALUES
(1, 1, 1, 95000, 10),
(2, 1, 2, 185000, 5),
(3, 2, 3, 135000, 8),
(4, 2, 4, 110000, 12),
(5, 3, 5, 125000, 15),
(6, 4, 6, 130000, 7),
(7, 4, 7, 155000, 9),
(8, 5, 8, 160000, 6),
(9, 5, 9, 170000, 4),
(10, 5, 10, 175000, 5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`MaHoaDon`, `MaNguoiDung`, `MaKhachHang`, `MaKhuyenMai`, `NgayLap`, `TienGiam`, `ThanhTien`, `TrangThai`) VALUES
(1, 1, 1, 1, '2025-04-25 10:00:00', 50000, 450000, 1),
(2, 2, 2, 1, '2025-04-25 11:00:00', 30000, 270000, 1),
(3, 3, 3, 1, '2025-04-25 12:00:00', 20000, 230000, 1),
(4, 1, 4, 1, '2025-04-25 13:00:00', 10000, 190000, 1),
(5, 2, 5, 1, '2025-04-25 14:00:00', 15000, 210000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKhachHang` int(11) NOT NULL,
  `HoTen` varchar(255) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `DiaChi` varchar(255) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MaKhachHang`, `HoTen`, `SoDienThoai`, `DiaChi`, `TrangThai`) VALUES
(1, 'Khách Mặc định', '0123456789', 'bẻaruibea', 1),
(2, 'Nguyễn Văn A', '0912345678', '123 Lê Lợi, Quận 1', 1),
(3, 'Trần Thị B', '0987654321', '456 Nguyễn Huệ, Quận 1', 1),
(4, 'Lê Văn C', '0909090909', '789 Trần Hưng Đạo, Quận 5', 1),
(5, 'Phạm Thị D', '0933333333', '321 Cách Mạng, Quận 3', 1),
(6, 'Hoàng Văn E', '0922222222', '654 Nguyễn Trãi, Quận 5', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `MaKhuyenMai` int(11) NOT NULL,
  `TenKhuyenMai` varchar(45) NOT NULL,
  `DieuKienHoaDon` int(11) NOT NULL,
  `NgayBatDau` datetime NOT NULL,
  `NgayKetThuc` datetime NOT NULL,
  `SoTienKhuyenMai` int(11) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `MaLoaiSanPham` int(11) NOT NULL,
  `TenLoaiSanPham` varchar(100) NOT NULL,
  `Mota` varchar(255) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`MaNguoiDung`, `TenDangNhap`, `MatKhau`, `HoTen`, `NgaySinh`, `GioiTinh`, `Email`, `NgayVaoLam`, `SoDienThoai`, `ConHoatDong`, `MaVaiTro`, `AvatarURL`, `DiaChi`) VALUES
(1, 'admin', '1234', 'Nguyễn Văn A', '2004-04-17', 'Nam', 'vana@gmail.com', '2025-04-09 00:00:00', '0231456987', 1, 1, NULL, '1 Lê Duẩn, Quận 1, TPHCM'),
(2, 'blamo', '0000', 'Phạm Hồng B', '2004-03-06', 'Nam', 'b@gmail.com', '2025-04-09 00:00:00', NULL, 1, 1, NULL, 'èavfaesf'),
(3, 'clmao', '0000', 'Phạm Văn Ca', '2004-08-19', 'Nam', 'c@gmail.com', '2025-04-09 00:00:00', '023658741', 1, 1, NULL, 'edfujiw'),
(5, 'Toan123', '0000', 'Toàn nẻwibn', '2004-02-02', 'Nam', 'aki', '2025-04-16 00:00:00', '0236547820', 1, 1, NULL, 'eaujibuib'),
(6, 'truong', '0000', 'Nguyễn Nhật Trường', '2004-04-17', 'Nam', 'truong3175@gmail.com', '2025-04-26 00:00:00', '0868480046', 1, 2, NULL, '312 QT			'),
(7, 'admin1', 'pass123', 'Admin 1', '1990-01-01', 'Nam', 'admin1@gmail.com', '2025-01-01 00:00:00', '0901234567', 1, 1, NULL, '123 Lê Lợi'),
(8, 'user2', 'pass234', 'User 2', '1995-05-05', 'Nữ', 'user2@gmail.com', '2025-02-01 00:00:00', '0912345678', 1, 2, NULL, '456 Nguyễn Huệ'),
(9, 'user3', 'pass345', 'User 3', '1998-08-08', 'Nam', 'user3@gmail.com', '2025-03-01 00:00:00', '0923456789', 1, 3, NULL, '789 Trần Hưng Đạo');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNhaCungCap` int(11) NOT NULL,
  `TenNhaCungCap` varchar(100) NOT NULL,
  `DiaChi` varchar(255) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `Fax` varchar(11) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNhaCungCap`, `TenNhaCungCap`, `DiaChi`, `SoDienThoai`, `Fax`, `TrangThai`) VALUES
(1, 'Công ty ABC', '12 Nguyễn Trãi, Quận 5, TP.HCM', '0911111111', '0811111111', 1),
(2, 'Công ty XYZ', '34 Lê Lợi, Quận 1, TP.HCM', '0922222222', '0822222222', 1),
(3, 'Công ty Thương mại KLM', '56 Hai Bà Trưng, Quận 3, TP.HCM', '0933333333', '0833333333', 1),
(4, 'Nhà phân phối QWE', '78 Trần Hưng Đạo, Quận 5, TP.HCM', '0944444444', '0844444444', 1),
(5, 'Công ty CP GHI', '90 Nguyễn Huệ, Quận 1, TP.HCM', '0955555555', '0855555555', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPhieuNhap` int(11) NOT NULL,
  `MaNguoiDung` int(11) NOT NULL,
  `MaNhaCungCap` int(11) NOT NULL,
  `ThoiGianLap` datetime NOT NULL,
  `TrangThai` tinyint(4) GENERATED ALWAYS AS (1) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`MaPhieuNhap`, `MaNguoiDung`, `MaNhaCungCap`, `ThoiGianLap`) VALUES
(1, 1, 1, '2025-04-20 08:30:00'),
(2, 2, 1, '2025-04-21 09:45:00'),
(3, 3, 1, '2025-04-22 10:15:00'),
(4, 1, 1, '2025-04-23 14:00:00'),
(5, 2, 1, '2025-04-24 15:30:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quyen`
--

CREATE TABLE `quyen` (
  `MaQuyen` int(11) NOT NULL,
  `TenQuyen` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `quyen`
--

INSERT INTO `quyen` (`MaQuyen`, `TenQuyen`) VALUES
(3, 'Bán hàng'),
(5, 'Quản lý khách hàng'),
(8, 'Quản lý kho'),
(7, 'Quản lý khuyến mãi'),
(2, 'Quản lý nhân sự'),
(1, 'Quản lý sản phẩm'),
(9, 'Thống kê');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
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
  `GiaVon` int(11) NOT NULL DEFAULT 0,
  `GiaLoi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSanPham`, `MaLoaiSanPham`, `AnhSanPhamURL`, `TenSanPham`, `NhaSanXuat`, `TrangThai`, `sanphamcol`, `SoLuong`, `GiaVon`, `GiaLoi`) VALUES
(1, 1, NULL, 'Sản phẩm 1', 'Nhà SX A', 1, NULL, 100, 100000, 20000),
(2, 1, NULL, 'Sản phẩm 2', 'Nhà SX A', 1, NULL, 200, 200000, 30000),
(3, 1, NULL, 'Sản phẩm 3', 'Nhà SX B', 1, NULL, 150, 150000, 25000),
(4, 1, NULL, 'Sản phẩm 4', 'Nhà SX B', 1, NULL, 120, 120000, 22000),
(5, 1, NULL, 'Sản phẩm 5', 'Nhà SX C', 1, NULL, 130, 130000, 23000),
(6, 1, NULL, 'Sản phẩm 6', 'Nhà SX C', 1, NULL, 140, 140000, 24000),
(7, 1, NULL, 'Sản phẩm 7', 'Nhà SX D', 1, NULL, 160, 160000, 26000),
(8, 1, NULL, 'Sản phẩm 8', 'Nhà SX D', 1, NULL, 170, 170000, 27000),
(9, 1, NULL, 'Sản phẩm 9', 'Nhà SX E', 1, NULL, 180, 180000, 28000),
(10, 1, NULL, 'Sản phẩm 10', 'Nhà SX E', 1, NULL, 190, 190000, 29000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vaitro`
--

CREATE TABLE `vaitro` (
  `MaVaiTro` int(11) NOT NULL,
  `TenVaiTro` varchar(45) NOT NULL,
  `MoTa` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `vaitro`
--

INSERT INTO `vaitro` (`MaVaiTro`, `TenVaiTro`, `MoTa`) VALUES
(1, 'Quản trị viên', 'Toàn quyền quản lý hệ thống'),
(2, 'Quản lý', 'Có quyền quản lý báo cáo và nhân sự'),
(3, 'Nhân viên nhập kho', 'Nhân viên quản lý nhập hàng vào kho		'),
(4, 'Nhân viên bán hàng', 'ẻveav');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vaitro_quyen`
--

CREATE TABLE `vaitro_quyen` (
  `MaVaiTro` int(11) NOT NULL,
  `MaQuyen` int(11) NOT NULL,
  `ChucNang` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `vaitro_quyen`
--

INSERT INTO `vaitro_quyen` (`MaVaiTro`, `MaQuyen`, `ChucNang`) VALUES
(1, 1, NULL),
(1, 2, NULL),
(1, 3, NULL),
(1, 5, NULL),
(1, 7, NULL),
(1, 8, NULL),
(1, 9, NULL),
(4, 1, NULL),
(4, 4, NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaChiTietHoaDon`);

--
-- Chỉ mục cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MaChiTietPhieuNhap`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHoaDon`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKhachHang`);

--
-- Chỉ mục cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`MaKhuyenMai`),
  ADD UNIQUE KEY `TenKhuyenMai_UNIQUE` (`TenKhuyenMai`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`MaLoaiSanPham`);

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`MaNguoiDung`),
  ADD UNIQUE KEY `TenDangNhap_UNIQUE` (`TenDangNhap`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNhaCungCap`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPhieuNhap`);

--
-- Chỉ mục cho bảng `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MaQuyen`),
  ADD UNIQUE KEY `TenQuyen_UNIQUE` (`TenQuyen`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSanPham`);

--
-- Chỉ mục cho bảng `vaitro`
--
ALTER TABLE `vaitro`
  ADD PRIMARY KEY (`MaVaiTro`),
  ADD UNIQUE KEY `TenVaiTro_UNIQUE` (`TenVaiTro`);

--
-- Chỉ mục cho bảng `vaitro_quyen`
--
ALTER TABLE `vaitro_quyen`
  ADD PRIMARY KEY (`MaVaiTro`,`MaQuyen`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `MaChiTietHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  MODIFY `MaChiTietPhieuNhap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKhachHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `MaKhuyenMai` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `MaLoaiSanPham` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `MaNguoiDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNhaCungCap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MaPhieuNhap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `quyen`
--
ALTER TABLE `quyen`
  MODIFY `MaQuyen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `vaitro`
--
ALTER TABLE `vaitro`
  MODIFY `MaVaiTro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
