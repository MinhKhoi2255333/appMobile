-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 20, 2022 lúc 02:42 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `appcoach`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `username`, `phone`, `pass`, `role`) VALUES
(1, 'khoi', '123', 'e10adc3949ba59abbe56e057f20f883e', 1),
(2, 'admin', '123456', 'e10adc3949ba59abbe56e057f20f883e', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account_bookmotorbike`
--

CREATE TABLE `account_bookmotorbike` (
  `userbookID` int(11) NOT NULL,
  `activityBook` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `bookID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account_ticket`
--

CREATE TABLE `account_ticket` (
  `ticketOrderID` int(11) NOT NULL,
  `activityTicket` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `TicketID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bookmotorbike`
--

CREATE TABLE `bookmotorbike` (
  `bookID` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `PlaceReceipt` varchar(255) NOT NULL,
  `PlaceDelivery` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coach`
--

CREATE TABLE `coach` (
  `coachID` int(11) NOT NULL,
  `seat` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `messageID` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `receipt`
--

CREATE TABLE `receipt` (
  `receiptID` int(11) NOT NULL,
  `dateReceipt` varchar(255) NOT NULL,
  `Sumcost` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket`
--

CREATE TABLE `ticket` (
  `TicketID` int(11) NOT NULL,
  `describeTicket` varchar(255) NOT NULL,
  `departure` varchar(255) NOT NULL,
  `destination` varchar(255) NOT NULL,
  `departureDate` varchar(255) NOT NULL,
  `cost` int(11) NOT NULL,
  `slot` int(11) NOT NULL,
  `departureTime` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ticket`
--

INSERT INTO `ticket` (`TicketID`, `describeTicket`, `departure`, `destination`, `departureDate`, `cost`, `slot`, `departureTime`) VALUES
(1, 'angiang', 'Hồ Chí Minh', 'An Giang', '2022-12-19', 150000, 30, '09:30:00'),
(2, 'dongthap', 'Hồ Chí Minh', 'Đồng tháp', '2022-12-19', 150000, 30, '09:00:00'),
(3, 'vinhlong', 'Hồ Chí Minh', 'Vĩnh Long', '2022-12-19', 150000, 30, '08:30:00'),
(8, 'travinh', 'Hồ Chí Minh', 'Trà Vinh', '2022-12-19', 150000, 30, '12:30:00'),
(9, 'cantho', 'Hồ Chí Minh', 'Cần Thơ', '2022-12-19', 150000, 30, '10:30:00'),
(10, 'kiengiang', 'Hồ Chí Minh', 'Kiên Giang', '2022-12-19', 150000, 30, '14:30:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket_coach`
--

CREATE TABLE `ticket_coach` (
  `active` int(11) NOT NULL,
  `TicketID` int(11) NOT NULL,
  `coachID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Chỉ mục cho bảng `account_bookmotorbike`
--
ALTER TABLE `account_bookmotorbike`
  ADD PRIMARY KEY (`userbookID`),
  ADD UNIQUE KEY `id` (`id`,`bookID`),
  ADD KEY `bookID` (`bookID`);

--
-- Chỉ mục cho bảng `account_ticket`
--
ALTER TABLE `account_ticket`
  ADD PRIMARY KEY (`ticketOrderID`),
  ADD UNIQUE KEY `id` (`id`,`TicketID`),
  ADD KEY `TicketID` (`TicketID`);

--
-- Chỉ mục cho bảng `bookmotorbike`
--
ALTER TABLE `bookmotorbike`
  ADD PRIMARY KEY (`bookID`);

--
-- Chỉ mục cho bảng `coach`
--
ALTER TABLE `coach`
  ADD PRIMARY KEY (`coachID`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`messageID`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`receiptID`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`TicketID`);

--
-- Chỉ mục cho bảng `ticket_coach`
--
ALTER TABLE `ticket_coach`
  ADD PRIMARY KEY (`TicketID`,`coachID`),
  ADD KEY `coachID` (`coachID`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account_bookmotorbike`
--
ALTER TABLE `account_bookmotorbike`
  ADD CONSTRAINT `account_bookmotorbike_ibfk_1` FOREIGN KEY (`id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `account_bookmotorbike_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `bookmotorbike` (`bookID`);

--
-- Các ràng buộc cho bảng `account_ticket`
--
ALTER TABLE `account_ticket`
  ADD CONSTRAINT `account_ticket_ibfk_1` FOREIGN KEY (`id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `account_ticket_ibfk_2` FOREIGN KEY (`TicketID`) REFERENCES `ticket` (`TicketID`);

--
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`id`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `receipt_ibfk_1` FOREIGN KEY (`id`) REFERENCES `account` (`id`);

--
-- Các ràng buộc cho bảng `ticket_coach`
--
ALTER TABLE `ticket_coach`
  ADD CONSTRAINT `ticket_coach_ibfk_1` FOREIGN KEY (`TicketID`) REFERENCES `ticket` (`TicketID`),
  ADD CONSTRAINT `ticket_coach_ibfk_2` FOREIGN KEY (`coachID`) REFERENCES `coach` (`coachID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
