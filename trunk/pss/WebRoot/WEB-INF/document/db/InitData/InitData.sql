/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50140
Source Host           : localhost:3306
Source Database       : pss

Target Server Type    : MYSQL
Target Server Version : 50140
File Encoding         : 65001

Date: 2013-02-04 16:13:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_bank`
-- ----------------------------
DROP TABLE IF EXISTS `t_bank`;
CREATE TABLE `t_bank` (
  `bankId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankShortName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_bank
-- ----------------------------
INSERT INTO `t_bank` VALUES ('402880bb3c85143a013c8523a50e0018', '中国农业银行', 'ABC', '231');
INSERT INTO `t_bank` VALUES ('402880bb3c85143a013c8523c6af0019', '中国建设银行', 'CCB', '469');

-- ----------------------------
-- Table structure for `t_bankstatements`
-- ----------------------------
DROP TABLE IF EXISTS `t_bankstatements`;
CREATE TABLE `t_bankstatements` (
  `bankStatementsId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double NOT NULL,
  `bankStatementsDate` date NOT NULL,
  `bankStatementsKind` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`bankStatementsId`),
  KEY `FK_Reference_97` (`bankId`),
  KEY `FK_Reference_98` (`employeeId`),
  CONSTRAINT `FK_Reference_97` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_98` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_bankstatements
-- ----------------------------

-- ----------------------------
-- Table structure for `t_buy`
-- ----------------------------
DROP TABLE IF EXISTS `t_buy`;
CREATE TABLE `t_buy` (
  `buyId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyDate` date NOT NULL,
  `receiveDate` date DEFAULT NULL,
  `otherAmount` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  `statue` int(11) DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`buyId`),
  KEY `FK_Reference_100` (`invoiceTypeId`),
  KEY `FK_Reference_35` (`supplierId`),
  KEY `FK_Reference_36` (`employeeId`),
  KEY `FK_Reference_38` (`bankId`),
  CONSTRAINT `FK_Reference_100` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_38` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_buy
-- ----------------------------
INSERT INTO `t_buy` VALUES ('402880bb3c8542f0013c854975af0001', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523c6af0019', '402881e53be5d01c013be5d1b6b70002', 'Buy201301290001', '3', '2013-01-22', '2013-01-29', '3', '15', '15', '0', null, null, '1');
INSERT INTO `t_buy` VALUES ('402880bb3c8542f0013c8561aa5b0003', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Buy201301290002', 'aa', '2013-01-29', '2013-01-30', '3', '3', '3', '0', null, '33', '0');
INSERT INTO `t_buy` VALUES ('402880bb3c8542f0013c8571a87a0005', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523c6af0019', '402881e53be5d01c013be5d1ec720003', 'Buy201301290003', 'aa', '2013-01-29', '2013-01-30', '4', '16', '16', '0', null, 'aaa', '1');
INSERT INTO `t_buy` VALUES ('402880bb3c892fc2013c8958d2860001', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523c6af0019', '402881e53be5d01c013be5d1b6b70002', 'Buy201301300001', null, '2013-01-30', '2013-01-30', '2', '8', '8', '0', null, '3', '0');
INSERT INTO `t_buy` VALUES ('402880bb3c8a3478013c8a36e85e0001', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Buy201301300002', null, '2013-01-30', '2013-01-30', '2', '134', '134', '0', null, null, '1');
INSERT INTO `t_buy` VALUES ('402880bb3c8a3478013c8a376f2b0003', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Buy201301300003', null, '2013-01-30', '2013-01-30', '4', '20', '20', '0', null, null, '1');

-- ----------------------------
-- Table structure for `t_buydetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_buydetail`;
CREATE TABLE `t_buydetail` (
  `buyDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `buyId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveQty` double DEFAULT NULL,
  PRIMARY KEY (`buyDetailId`),
  KEY `FK_Reference_39` (`productId`),
  KEY `FK_Reference_40` (`colorId`),
  KEY `FK_T_BUYDET_REFERENCE_T_BUY` (`buyId`),
  CONSTRAINT `FK_Reference_39` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_T_BUYDET_REFERENCE_T_BUY` FOREIGN KEY (`buyId`) REFERENCES `t_buy` (`buyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_buydetail
-- ----------------------------
INSERT INTO `t_buydetail` VALUES ('402880bb3c8542f0013c854975d30002', '402880bb3c8542f0013c854975af0001', '402880bb3c85143a013c852539d30022', '402880bb3c85143a013c851ff70e0006', '3', '4', null, null, null, '3');
INSERT INTO `t_buydetail` VALUES ('402880bb3c8542f0013c8561aa5b0004', '402880bb3c8542f0013c8561aa5b0003', '402880bb3c85143a013c8524f5140021', null, '4', '0', '3', '3', '3', '0');
INSERT INTO `t_buydetail` VALUES ('402880bb3c8542f0013c8571a87a0006', '402880bb3c8542f0013c8571a87a0005', '402880bb3c85143a013c852539d30022', null, '3', '4', '33', '3', '3', '0');
INSERT INTO `t_buydetail` VALUES ('402880bb3c892fc2013c8958d2ad0002', '402880bb3c892fc2013c8958d2860001', '402880bb3c85143a013c8524f5140021', '402880bb3c85143a013c851fea8a0005', '2', '3', null, null, null, '0');
INSERT INTO `t_buydetail` VALUES ('402880bb3c8a3478013c8a36e8730002', '402880bb3c8a3478013c8a36e85e0001', '402880bb3c85143a013c8524f5140021', '402880bb3c85143a013c851fea8a0005', '44', '3', '3', '3', '3', '44');
INSERT INTO `t_buydetail` VALUES ('402880bb3c8a3478013c8a376f2c0004', '402880bb3c8a3478013c8a376f2b0003', '402880bb3c85143a013c852539d30022', '402880bb3c85143a013c851ff70e0006', '4', '4', null, null, null, '0');

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `customerId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerTypeID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `customerName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contacter` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) NOT NULL,
  `note` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  KEY `FK_Reference_81` (`customerTypeID`),
  CONSTRAINT `FK_Reference_81` FOREIGN KEY (`customerTypeID`) REFERENCES `t_customertype` (`customerTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('402880bb3c85143a013c852448ea001e', '402880bb3c85143a013c8524262d001d', '客户001', '客户001', 'aa', 'aaa', '1', null, 'a');
INSERT INTO `t_customer` VALUES ('402880bb3ca2f9ce013ca2fb41180001', '402880bb3c85143a013c8524262d001d', 'a', 'a', 'a', 'aa', '1', 'a', 'a');

-- ----------------------------
-- Table structure for `t_customertype`
-- ----------------------------
DROP TABLE IF EXISTS `t_customertype`;
CREATE TABLE `t_customertype` (
  `customerTypeID` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerTypeCode` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerTypeName` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`customerTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_customertype
-- ----------------------------
INSERT INTO `t_customertype` VALUES ('402880bb3c85143a013c8524262d001d', '客户类型001', '客户类型001', null);

-- ----------------------------
-- Table structure for `t_datadictionary`
-- ----------------------------
DROP TABLE IF EXISTS `t_datadictionary`;
CREATE TABLE `t_datadictionary` (
  `dataDictionaryId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dataDictionaryKind` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dataDictionaryName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`dataDictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统中用到的一些基础的数据：用kind 区分开\r\n1.商品颜色 color\r\n2.商品规格 size';

-- ----------------------------
-- Records of t_datadictionary
-- ----------------------------
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c851fea8a0005', 'color', 'R');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c851ff70e0006', 'color', 'G');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c852005260007', 'color', 'B');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c852034450008', 'size', 'S');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c8520463e0009', 'size', 'M');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c85205801000a', 'size', 'L');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c85207ace000b', 'unit', 'kg');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c85208e42000c', 'unit', 't');
INSERT INTO `t_datadictionary` VALUES ('402880bb3c85143a013c8520a26a000d', 'unit', 'm');

-- ----------------------------
-- Table structure for `t_defaultpackaging`
-- ----------------------------
DROP TABLE IF EXISTS `t_defaultpackaging`;
CREATE TABLE `t_defaultpackaging` (
  `defaultPackagingId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `parentProductId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`defaultPackagingId`),
  UNIQUE KEY `defaultPackagingId` (`defaultPackagingId`),
  KEY `FK1409F2ED1948A007` (`productId`),
  KEY `FK1409F2EDE94BB69D` (`parentProductId`),
  CONSTRAINT `FK1409F2ED1948A007` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK1409F2EDE94BB69D` FOREIGN KEY (`parentProductId`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_defaultpackaging
-- ----------------------------
INSERT INTO `t_defaultpackaging` VALUES ('402822813c9edc28013c9edcb9690001', '1', '402880bb3c85143a013c8524f5140021', '402880bb3c85143a013c852539d30022');
INSERT INTO `t_defaultpackaging` VALUES ('402822813c9edc28013c9edcb9b40002', '1', '402880bb3c85143a013c8524f5140021', '402880bb3c85143a013c8525b81f0023');

-- ----------------------------
-- Table structure for `t_deliver`
-- ----------------------------
DROP TABLE IF EXISTS `t_deliver`;
CREATE TABLE `t_deliver` (
  `deliverId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `expressId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverDate` date DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `discountAmount` float DEFAULT NULL,
  `receiptedAmount` float DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `expressCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isReceipt` int(11) DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  PRIMARY KEY (`deliverId`),
  KEY `FK_Reference_101` (`customerId`),
  KEY `FK_Reference_68` (`expressId`),
  KEY `FK_Reference_69` (`warehouseId`),
  KEY `FK_Reference_70` (`employeeId`),
  KEY `FK_Reference_71` (`bankId`),
  KEY `FK_Reference_72` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_101` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
  CONSTRAINT `FK_Reference_68` FOREIGN KEY (`expressId`) REFERENCES `t_express` (`expressId`),
  CONSTRAINT `FK_Reference_69` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_70` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_71` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_72` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_deliver
-- ----------------------------

-- ----------------------------
-- Table structure for `t_deliverdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_deliverdetail`;
CREATE TABLE `t_deliverdetail` (
  `deliverDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `deliverId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleDetailId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`deliverDetailId`),
  KEY `FK_Reference_73` (`deliverId`),
  KEY `FK_Reference_74` (`productId`),
  KEY `FK_Reference_75` (`colorId`),
  KEY `FK_Reference_76` (`saleDetailId`),
  CONSTRAINT `FK_Reference_73` FOREIGN KEY (`deliverId`) REFERENCES `t_deliver` (`deliverId`),
  CONSTRAINT `FK_Reference_74` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_75` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_Reference_76` FOREIGN KEY (`saleDetailId`) REFERENCES `t_saledetail` (`saleDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_deliverdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_deliverreject`
-- ----------------------------
DROP TABLE IF EXISTS `t_deliverreject`;
CREATE TABLE `t_deliverreject` (
  `deliverRejectId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverRejectCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverRejectDate` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payedAmount` double DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  PRIMARY KEY (`deliverRejectId`),
  KEY `FK_Reference_102` (`customerId`),
  KEY `FK_Reference_77` (`bankId`),
  KEY `FK_Reference_78` (`employeeId`),
  KEY `FK_Reference_79` (`warehouseId`),
  KEY `FK_Reference_80` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_102` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
  CONSTRAINT `FK_Reference_77` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_78` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_79` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_80` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_deliverreject
-- ----------------------------

-- ----------------------------
-- Table structure for `t_deliverrejectdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_deliverrejectdetail`;
CREATE TABLE `t_deliverrejectdetail` (
  `deliverRejectDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `deliverRejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`deliverRejectDetailId`),
  KEY `FK_Reference_82` (`deliverRejectId`),
  KEY `FK_Reference_83` (`productId`),
  KEY `FK_Reference_84` (`colorId`),
  CONSTRAINT `FK_Reference_82` FOREIGN KEY (`deliverRejectId`) REFERENCES `t_deliverreject` (`deliverRejectId`),
  CONSTRAINT `FK_Reference_83` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_84` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_deliverrejectdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `employeeName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('402880bb3c85143a013c8523e5a8001a', '员工001');
INSERT INTO `t_employee` VALUES ('402880bb3c85143a013c8523f63a001b', '员工002');
INSERT INTO `t_employee` VALUES ('402880bb3c85143a013c852405d6001c', '员工003');

-- ----------------------------
-- Table structure for `t_expense`
-- ----------------------------
DROP TABLE IF EXISTS `t_expense`;
CREATE TABLE `t_expense` (
  `expenseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `expenseName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double NOT NULL,
  `expenseDate` date NOT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`expenseId`),
  KEY `FK_Reference_95` (`bankId`),
  KEY `FK_Reference_96` (`employeeId`),
  CONSTRAINT `FK_Reference_95` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_96` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_expense
-- ----------------------------

-- ----------------------------
-- Table structure for `t_express`
-- ----------------------------
DROP TABLE IF EXISTS `t_express`;
CREATE TABLE `t_express` (
  `expressId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `expressName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`expressId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_express
-- ----------------------------
INSERT INTO `t_express` VALUES ('402880bb3c85143a013c852487ff001f', '快递001');
INSERT INTO `t_express` VALUES ('402880bb3c85143a013c852499bf0020', '快递002');

-- ----------------------------
-- Table structure for `t_income`
-- ----------------------------
DROP TABLE IF EXISTS `t_income`;
CREATE TABLE `t_income` (
  `incomeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `incomeName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `incomeDate` date NOT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`incomeId`),
  KEY `FK_Reference_93` (`bankId`),
  KEY `FK_Reference_94` (`employeeId`),
  CONSTRAINT `FK_Reference_93` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_94` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_income
-- ----------------------------

-- ----------------------------
-- Table structure for `t_invoicetype`
-- ----------------------------
DROP TABLE IF EXISTS `t_invoicetype`;
CREATE TABLE `t_invoicetype` (
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `invoiceTypeName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`invoiceTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_invoicetype
-- ----------------------------
INSERT INTO `t_invoicetype` VALUES ('402881e53be5d01c013be5d1b6b70002', '不开票');
INSERT INTO `t_invoicetype` VALUES ('402881e53be5d01c013be5d1ec720003', '已开收据');
INSERT INTO `t_invoicetype` VALUES ('402881e53be5d01c013be5d21c630004', '普票3%');
INSERT INTO `t_invoicetype` VALUES ('402881e53be5d01c013be5d2465f0005', '增票17%');

-- ----------------------------
-- Table structure for `t_packaging`
-- ----------------------------
DROP TABLE IF EXISTS `t_packaging`;
CREATE TABLE `t_packaging` (
  `packagingId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `packagingCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `packagingDate` date NOT NULL,
  `price` double NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`packagingId`),
  UNIQUE KEY `packagingId` (`packagingId`),
  KEY `FK65271938142C5B2F` (`warehouseId`),
  KEY `FK65271938344987E9` (`employeeId`),
  KEY `FK652719381948A007` (`productId`),
  CONSTRAINT `FK652719381948A007` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK65271938142C5B2F` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK65271938344987E9` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_packaging
-- ----------------------------

-- ----------------------------
-- Table structure for `t_packagingdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_packagingdetail`;
CREATE TABLE `t_packagingdetail` (
  `packagingDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `packagingId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`packagingDetailId`),
  UNIQUE KEY `packagingDetailId` (`packagingDetailId`),
  KEY `FK33DC53A9142C5B2F` (`warehouseId`),
  KEY `FK33DC53A91948A007` (`productId`),
  KEY `FK33DC53A9230C64AF` (`packagingId`),
  CONSTRAINT `FK33DC53A9230C64AF` FOREIGN KEY (`packagingId`) REFERENCES `t_packaging` (`packagingId`),
  CONSTRAINT `FK33DC53A9142C5B2F` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK33DC53A91948A007` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_packagingdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_pay`
-- ----------------------------
DROP TABLE IF EXISTS `t_pay`;
CREATE TABLE `t_pay` (
  `payId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payway` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payDate` date DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `discountAmount` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  PRIMARY KEY (`payId`),
  KEY `FK_Reference_34` (`supplierId`),
  KEY `FK_Reference_58` (`bankId`),
  KEY `FK_Reference_59` (`employeeId`),
  CONSTRAINT `FK_Reference_34` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_58` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_59` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_pay
-- ----------------------------

-- ----------------------------
-- Table structure for `t_paydetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_paydetail`;
CREATE TABLE `t_paydetail` (
  `payDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `payId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prepayId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payKind` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceDate` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payedAmount` double DEFAULT NULL,
  `discountAmount` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  PRIMARY KEY (`payDetailId`),
  KEY `FK_Reference_85` (`payId`),
  KEY `FK_Reference_86` (`receiveId`),
  KEY `FK_Reference_90` (`prepayId`),
  KEY `FK_Reference_91` (`buyId`),
  KEY `FK_Reference_92` (`rejectId`),
  CONSTRAINT `FK_Reference_85` FOREIGN KEY (`payId`) REFERENCES `t_pay` (`payId`),
  CONSTRAINT `FK_Reference_86` FOREIGN KEY (`receiveId`) REFERENCES `t_receive` (`receiveId`),
  CONSTRAINT `FK_Reference_90` FOREIGN KEY (`prepayId`) REFERENCES `t_prepay` (`prepayId`),
  CONSTRAINT `FK_Reference_91` FOREIGN KEY (`buyId`) REFERENCES `t_buy` (`buyId`),
  CONSTRAINT `FK_Reference_92` FOREIGN KEY (`rejectId`) REFERENCES `t_reject` (`rejectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_paydetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_prefix`
-- ----------------------------
DROP TABLE IF EXISTS `t_prefix`;
CREATE TABLE `t_prefix` (
  `prefixId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `prefixCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prefix` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prefixName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`prefixId`),
  KEY `AK_AK` (`prefixCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_prefix
-- ----------------------------
INSERT INTO `t_prefix` VALUES ('402880bb3c6a096d013c6a12abf60001', 'prepay', 'Prepay', '预付单');
INSERT INTO `t_prefix` VALUES ('402880bb3ca43ec0013ca43ff4270001', 'packaging', 'packaging', '组装');
INSERT INTO `t_prefix` VALUES ('402880bb3ca43ec0013ca4406dbc0002', 'split', 'split', '拆分');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d53b8250001', 'buy', 'Buy', '采购单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d5464870002', 'receive', 'Receive', '入库单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d54f7bf0003', 'receiveOther', 'ReceiveOther', '其他入库单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d557d800004', 'pay', 'Pay', '付款单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d8e25013c5dcc2eeb000f', 'reject', 'Reject', '采购退货单');
INSERT INTO `t_prefix` VALUES ('402881e53c7b327f013c7b3951870001', 'sale', 'Sale', '订单');
INSERT INTO `t_prefix` VALUES ('402881e53c7b327f013c7b39a2d10002', 'deliver', 'Deliver', '出库');
INSERT INTO `t_prefix` VALUES ('402881e53c7b327f013c7b39f13e0003', 'deliverOther', 'DeliverOther', '其他出库');
INSERT INTO `t_prefix` VALUES ('402881e53c7b327f013c7b3a3c960004', 'deliverReject', 'DeliverReject', '销售退货');
INSERT INTO `t_prefix` VALUES ('402881e53c7b327f013c7b3aa2d00005', 'preReceipt', 'PreReceipt', '预收');

-- ----------------------------
-- Table structure for `t_prepay`
-- ----------------------------
DROP TABLE IF EXISTS `t_prepay`;
CREATE TABLE `t_prepay` (
  `prepayId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prepayCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prepayDate` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`prepayId`),
  KEY `FK_Reference_87` (`bankId`),
  KEY `FK_Reference_88` (`supplierId`),
  KEY `FK_Reference_89` (`employeeId`),
  CONSTRAINT `FK_Reference_87` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_88` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_89` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_prepay
-- ----------------------------

-- ----------------------------
-- Table structure for `t_prereceipt`
-- ----------------------------
DROP TABLE IF EXISTS `t_prereceipt`;
CREATE TABLE `t_prereceipt` (
  `prepayId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `amount` double DEFAULT NULL,
  `balanceAmount` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  `note` longtext COLLATE utf8_unicode_ci,
  `preReceiptCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `preReceiptDate` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `preReceiptId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`prepayId`),
  UNIQUE KEY `prepayId` (`prepayId`),
  UNIQUE KEY `preReceiptId` (`preReceiptId`),
  KEY `FKC4271F00344987E9` (`employeeId`),
  KEY `FKC4271F00C5606485` (`bankId`),
  KEY `FKC4271F00A9052E49` (`customerId`),
  CONSTRAINT `FKC4271F00344987E9` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FKC4271F00A9052E49` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
  CONSTRAINT `FKC4271F00C5606485` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_prereceipt
-- ----------------------------

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `productId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `productTypeID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unitId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sizeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qtyStore` double DEFAULT NULL,
  `amountStore` double DEFAULT NULL,
  `buyingPrice` double DEFAULT NULL,
  `salePrice` double DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `FK_T_PRODUC_REFERENCE_T_Size` (`sizeId`),
  KEY `FK_T_PRODUC_REFERENCE_PRODUCTT` (`productTypeID`),
  KEY `FK_T_PRODUC_REFERENCE_T_Unit` (`unitId`),
  KEY `FK_T_PRODUC_REFERENCE_T_Color` (`colorId`),
  CONSTRAINT `FK_T_PRODUC_REFERENCE_PRODUCTT` FOREIGN KEY (`productTypeID`) REFERENCES `t_producttype` (`productTypeId`),
  CONSTRAINT `FK_T_PRODUC_REFERENCE_T_Color` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_T_PRODUC_REFERENCE_T_Size` FOREIGN KEY (`sizeId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_T_PRODUC_REFERENCE_T_Unit` FOREIGN KEY (`unitId`) REFERENCES `t_datadictionary` (`dataDictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('402822813c9edc28013c9ee0818c0003', '402880bb3c85143a013c852283f60010', null, null, null, 'aa', 'aa', '0', '0', '0', '0', null);
INSERT INTO `t_product` VALUES ('402880bb3c85143a013c8524f5140021', '402880bb3c85143a013c852283f60010', '402880bb3c85143a013c85207ace000b', '402880bb3c85143a013c851fea8a0005', '402880bb3c85143a013c852034450008', '商品00001', '商品00001', '47', '141', '3', '3', null);
INSERT INTO `t_product` VALUES ('402880bb3c85143a013c852539d30022', '402880bb3c85143a013c8522bcba0011', '402880bb3c85143a013c85208e42000c', '402880bb3c85143a013c851ff70e0006', '402880bb3c85143a013c8520463e0009', '商品00002', '商品00002', '-2', '2', '4', '4', null);
INSERT INTO `t_product` VALUES ('402880bb3c85143a013c8525b81f0023', '402880bb3c85143a013c85225a6a000f', '402880bb3c85143a013c85208e42000c', '402880bb3c85143a013c851fea8a0005', '402880bb3c85143a013c8520463e0009', '商品00003', '商品00003', '0', '0', '44.44', '44.44', null);

-- ----------------------------
-- Table structure for `t_producttype`
-- ----------------------------
DROP TABLE IF EXISTS `t_producttype`;
CREATE TABLE `t_producttype` (
  `productTypeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `parentProductTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productTypeName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productTypeCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isLeaf` int(11) DEFAULT NULL,
  PRIMARY KEY (`productTypeId`),
  KEY `FK286BE93E6D6ED1B1` (`parentProductTypeId`),
  CONSTRAINT `FK286BE93E6D6ED1B1` FOREIGN KEY (`parentProductTypeId`) REFERENCES `t_producttype` (`productTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_producttype
-- ----------------------------
INSERT INTO `t_producttype` VALUES ('402880bb3c85143a013c85223c08000e', 'fda922bf5f2847a89f9fb58727e99600', '商品类别0001', '商品类别0001', '0');
INSERT INTO `t_producttype` VALUES ('402880bb3c85143a013c85225a6a000f', 'fda922bf5f2847a89f9fb58727e99600', '商品类别0002', '商品类别0002', '0');
INSERT INTO `t_producttype` VALUES ('402880bb3c85143a013c852283f60010', '402880bb3c85143a013c85223c08000e', '商品类别00010001', '商品类别00010001', '1');
INSERT INTO `t_producttype` VALUES ('402880bb3c85143a013c8522bcba0011', '402880bb3c85143a013c85225a6a000f', '商品类别00020001', '商品类别00020001', '1');
INSERT INTO `t_producttype` VALUES ('fda922bf5f2847a89f9fb58727e99600', null, '商品类别', '0', '0');

-- ----------------------------
-- Table structure for `t_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `t_receipt`;
CREATE TABLE `t_receipt` (
  `receiptId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `amount` double DEFAULT NULL,
  `discountAmount` double DEFAULT NULL,
  `note` longtext COLLATE utf8_unicode_ci,
  `receiptAmount` double DEFAULT NULL,
  `receiptCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiptDate` date DEFAULT NULL,
  `receiptway` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`receiptId`),
  UNIQUE KEY `receiptId` (`receiptId`),
  KEY `FKD6BF1D2D344987E9` (`employeeId`),
  KEY `FKD6BF1D2DC5606485` (`bankId`),
  KEY `FKD6BF1D2DA9052E49` (`customerId`),
  CONSTRAINT `FKD6BF1D2D344987E9` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FKD6BF1D2DA9052E49` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
  CONSTRAINT `FKD6BF1D2DC5606485` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `t_receiptdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_receiptdetail`;
CREATE TABLE `t_receiptdetail` (
  `receiptDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `amount` double DEFAULT NULL,
  `discountAmount` double DEFAULT NULL,
  `receiptAmount` double DEFAULT NULL,
  `receiptKind` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiptedAmount` double DEFAULT NULL,
  `sourceCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverRejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `preReceiptId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiptId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`receiptDetailId`),
  UNIQUE KEY `receiptDetailId` (`receiptDetailId`),
  KEY `FK667CCBDEF2DE8891` (`deliverRejectId`),
  KEY `FK667CCBDE61633C97` (`preReceiptId`),
  KEY `FK667CCBDED49DBC59` (`receiptId`),
  KEY `FK667CCBDEE26981DB` (`saleId`),
  KEY `FK667CCBDEB866E2F3` (`deliverId`),
  CONSTRAINT `FK667CCBDE61633C97` FOREIGN KEY (`preReceiptId`) REFERENCES `t_prereceipt` (`preReceiptId`),
  CONSTRAINT `FK667CCBDEB866E2F3` FOREIGN KEY (`deliverId`) REFERENCES `t_deliver` (`deliverId`),
  CONSTRAINT `FK667CCBDED49DBC59` FOREIGN KEY (`receiptId`) REFERENCES `t_receipt` (`receiptId`),
  CONSTRAINT `FK667CCBDEE26981DB` FOREIGN KEY (`saleId`) REFERENCES `t_sale` (`saleId`),
  CONSTRAINT `FK667CCBDEF2DE8891` FOREIGN KEY (`deliverRejectId`) REFERENCES `t_deliverreject` (`deliverRejectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_receiptdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_receive`
-- ----------------------------
DROP TABLE IF EXISTS `t_receive`;
CREATE TABLE `t_receive` (
  `receiveId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveDate` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `discountAmount` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `isPay` int(11) DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `otherAmount` double DEFAULT NULL,
  PRIMARY KEY (`receiveId`),
  KEY `FK_Reference_43` (`supplierId`),
  KEY `FK_Reference_44` (`warehouseId`),
  KEY `FK_Reference_45` (`invoiceTypeId`),
  KEY `FK_Reference_46` (`employeeId`),
  KEY `FK_Reference_47` (`bankId`),
  CONSTRAINT `FK_Reference_43` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_44` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_45` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_46` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_47` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_receive
-- ----------------------------
INSERT INTO `t_receive` VALUES ('402880bb3c8542f0013c857b66580007', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Receive201301290001', 'adfad', '2013-01-29', '15', '2', '11', '0', '1', '0', '33', '3');
INSERT INTO `t_receive` VALUES ('402880bb3c8969b1013c897ee4c80001', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c85235c740016', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Receive201301300001', 'adfad', '2013-01-30', '8', '2', '6', '0', '1', '1', '3b', '2');
INSERT INTO `t_receive` VALUES ('402880bb3c8a3478013c8a383e3c0005', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1ec720003', 'Receive201301300002', null, '2013-01-30', '132', '3', '100', '0', '1', '1', null, '0');
INSERT INTO `t_receive` VALUES ('402880bb3c8a3478013c8a38e15b0007', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8523a50e0018', '402881e53be5d01c013be5d1b6b70002', 'Receive201301300003', null, '2013-01-22', '15', '0', '15', '0', '0', '0', null, '3');

-- ----------------------------
-- Table structure for `t_receivedetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_receivedetail`;
CREATE TABLE `t_receivedetail` (
  `receiveDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `receiveId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyDetailId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`receiveDetailId`),
  KEY `FK_T_RECEIV_REFERENCE_T_RECEIV` (`receiveId`),
  KEY `FK_Reference_42` (`buyDetailId`),
  KEY `FK_Reference_48` (`productId`),
  KEY `FK_Reference_49` (`colorId`),
  CONSTRAINT `FK_Reference_42` FOREIGN KEY (`buyDetailId`) REFERENCES `t_buydetail` (`buyDetailId`),
  CONSTRAINT `FK_Reference_48` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_49` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_T_RECEIV_REFERENCE_T_RECEIV` FOREIGN KEY (`receiveId`) REFERENCES `t_receive` (`receiveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_receivedetail
-- ----------------------------
INSERT INTO `t_receivedetail` VALUES ('402880bb3c8542f0013c857b66590008', '402880bb3c8542f0013c857b66580007', '402880bb3c85143a013c852539d30022', '402880bb3c8542f0013c854975d30002', '402880bb3c85143a013c851ff70e0006', '3', '4', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c8969b1013c897ee4cf0002', '402880bb3c8969b1013c897ee4c80001', '402880bb3c85143a013c8524f5140021', null, '402880bb3c85143a013c851fea8a0005', '2', '3', '33', '3', '3');
INSERT INTO `t_receivedetail` VALUES ('402880bb3c8a3478013c8a383e3d0006', '402880bb3c8a3478013c8a383e3c0005', '402880bb3c85143a013c8524f5140021', '402880bb3c8a3478013c8a36e8730002', '402880bb3c85143a013c851fea8a0005', '44', '3', '3', '3', '3');
INSERT INTO `t_receivedetail` VALUES ('402880bb3c8a3478013c8a38e15c0008', '402880bb3c8a3478013c8a38e15b0007', '402880bb3c85143a013c852539d30022', null, '402880bb3c85143a013c851ff70e0006', '3', '4', null, null, null);

-- ----------------------------
-- Table structure for `t_reject`
-- ----------------------------
DROP TABLE IF EXISTS `t_reject`;
CREATE TABLE `t_reject` (
  `rejectId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectDate` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  `shzt` int(11) DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`rejectId`),
  KEY `FK_Reference_50` (`warehouseId`),
  KEY `FK_Reference_51` (`employeeId`),
  KEY `FK_Reference_52` (`invoiceTypeId`),
  KEY `FK_Reference_53` (`bankId`),
  KEY `FK4901160A55C35EA5` (`supplierId`),
  CONSTRAINT `FK4901160A55C35EA5` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_50` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_51` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_52` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_53` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_reject
-- ----------------------------
INSERT INTO `t_reject` VALUES ('402880bb3c8a3478013c8a39fa9e000b', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c8523e5a8001a', '402880bb3c85143a013c8522deb90012', '402880bb3c85143a013c8523a50e0018', null, 'Reject201301300002', null, '2013-01-30', '90', '2', '0', null, null, '1');

-- ----------------------------
-- Table structure for `t_rejectdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_rejectdetail`;
CREATE TABLE `t_rejectdetail` (
  `rejectDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `rejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`rejectDetailId`),
  KEY `FK_T_REJECT_REFERENCE_T_REJECT` (`rejectId`),
  KEY `FK_Reference_54` (`productId`),
  KEY `FK_Reference_55` (`colorId`),
  CONSTRAINT `FK_Reference_54` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_55` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_T_REJECT_REFERENCE_T_REJECT` FOREIGN KEY (`rejectId`) REFERENCES `t_reject` (`rejectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_rejectdetail
-- ----------------------------
INSERT INTO `t_rejectdetail` VALUES ('402880bb3c8a3478013c8a39fa9f000c', '402880bb3c8a3478013c8a39fa9e000b', '402880bb3c85143a013c852539d30022', null, '5', '2', null, null, null, '10');

-- ----------------------------
-- Table structure for `t_reportconfig`
-- ----------------------------
DROP TABLE IF EXISTS `t_reportconfig`;
CREATE TABLE `t_reportconfig` (
  `reportConfigId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `reportCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportDetailSql` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportParamsSql` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportKind` int(11) DEFAULT NULL,
  `templateData` longblob,
  PRIMARY KEY (`reportConfigId`),
  KEY `AK_AK` (`reportCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_reportconfig
-- ----------------------------
INSERT INTO `t_reportconfig` VALUES ('402822813c9edc28013c9f68fedb000c', 'receiptCheck', '客户对账单', 'CALL P_ReceiptCheckDetail(@txtBeginDate,@txtEndDate,@customerId,@warehouseId)', 'CALL P_ReceiptCheckParams(@customerId,@warehouseId)', '1', null);
INSERT INTO `t_reportconfig` VALUES ('402880bb3c85143a013c851993db0001', 'buy', '采购单', 'call p_buyDetail(@buyId)', 'call p_buyParams(@buyId)', '0', null);
INSERT INTO `t_reportconfig` VALUES ('402880bb3c8fd9df013c8fe0af2f0002', 'payCheck', '供应商对账单', 'CALL P_PayCheckDetail(@txtBeginDate,@txtEndDate,@supplierId,@warehouseId)', 'CALL P_PayCheckParams(@supplierId,@warehouseId)', '1', null);
INSERT INTO `t_reportconfig` VALUES ('402880bb3ca3f9a5013ca4190bbf0001', 'receive', '采购单入库', 'call P_ReceiveDetail (@receiveId)', 'call P_ReceiveParams (@receiveId)', '0', null);

-- ----------------------------
-- Table structure for `t_reportparam`
-- ----------------------------
DROP TABLE IF EXISTS `t_reportparam`;
CREATE TABLE `t_reportparam` (
  `reportParamId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `paramName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `paramCode` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`reportParamId`),
  KEY `AK_Key_2` (`paramCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_reportparam
-- ----------------------------
INSERT INTO `t_reportparam` VALUES ('402822813c9edc28013c9f32b7730009', '客户', '@customerId');
INSERT INTO `t_reportparam` VALUES ('402880bb3c8f6b8e013c8f6c89630001', '开始日期', '@txtBeginDate');
INSERT INTO `t_reportparam` VALUES ('402880bb3c8f6b8e013c8f6d13f10002', '供应商', '@supplierId');
INSERT INTO `t_reportparam` VALUES ('402880bb3c8f6b8e013c8f6d50740003', '仓库', '@warehouseId');
INSERT INTO `t_reportparam` VALUES ('402880bb3c8fd9df013c8fdc27a40001', '结束日期', '@txtEndDate');

-- ----------------------------
-- Table structure for `t_reportparamconfig`
-- ----------------------------
DROP TABLE IF EXISTS `t_reportparamconfig`;
CREATE TABLE `t_reportparamconfig` (
  `reportParamConfigId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `reportConfigId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportParamId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isNeedChoose` int(11) NOT NULL,
  `array` int(11) NOT NULL,
  PRIMARY KEY (`reportParamConfigId`),
  KEY `FK_Reference_103` (`reportConfigId`),
  KEY `FK_Reference_104` (`reportParamId`),
  CONSTRAINT `FK_Reference_103` FOREIGN KEY (`reportConfigId`) REFERENCES `t_reportconfig` (`reportConfigId`),
  CONSTRAINT `FK_Reference_104` FOREIGN KEY (`reportParamId`) REFERENCES `t_reportparam` (`reportParamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_reportparamconfig
-- ----------------------------
INSERT INTO `t_reportparamconfig` VALUES ('402822813c9edc28013c9f68fedb000d', '402822813c9edc28013c9f68fedb000c', '402880bb3c8f6b8e013c8f6c89630001', '1', '0');
INSERT INTO `t_reportparamconfig` VALUES ('402822813c9edc28013c9f68fedb000e', '402822813c9edc28013c9f68fedb000c', '402880bb3c8fd9df013c8fdc27a40001', '1', '1');
INSERT INTO `t_reportparamconfig` VALUES ('402822813c9edc28013c9f68fedc000f', '402822813c9edc28013c9f68fedb000c', '402822813c9edc28013c9f32b7730009', '1', '2');
INSERT INTO `t_reportparamconfig` VALUES ('402822813c9edc28013c9f68fedc0010', '402822813c9edc28013c9f68fedb000c', '402880bb3c8f6b8e013c8f6d50740003', '1', '3');
INSERT INTO `t_reportparamconfig` VALUES ('402880bb3c8fd9df013c8fe0af300003', '402880bb3c8fd9df013c8fe0af2f0002', '402880bb3c8f6b8e013c8f6c89630001', '1', '0');
INSERT INTO `t_reportparamconfig` VALUES ('402880bb3c8fd9df013c8fe0af300004', '402880bb3c8fd9df013c8fe0af2f0002', '402880bb3c8fd9df013c8fdc27a40001', '1', '1');
INSERT INTO `t_reportparamconfig` VALUES ('402880bb3c8fd9df013c8fe0af300005', '402880bb3c8fd9df013c8fe0af2f0002', '402880bb3c8f6b8e013c8f6d13f10002', '1', '2');
INSERT INTO `t_reportparamconfig` VALUES ('402880bb3c9352c7013c9358ac1b0001', '402880bb3c8fd9df013c8fe0af2f0002', '402880bb3c8f6b8e013c8f6d50740003', '1', '3');

-- ----------------------------
-- Table structure for `t_right`
-- ----------------------------
DROP TABLE IF EXISTS `t_right`;
CREATE TABLE `t_right` (
  `rightId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `rightName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rightUrl` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentRightId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isLeaf` int(11) DEFAULT NULL,
  `array` int(11) DEFAULT NULL,
  PRIMARY KEY (`rightId`),
  KEY `FK25CA651FD1B92B7` (`parentRightId`),
  CONSTRAINT `FK25CA651FD1B92B7` FOREIGN KEY (`parentRightId`) REFERENCES `t_right` (`rightId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_right
-- ----------------------------
INSERT INTO `t_right` VALUES ('402822813c985685013c9858edad0001', '统计报表', 'iframe/report.html', '402881e53aa31698013aa31fa1980003', '1', '10');
INSERT INTO `t_right` VALUES ('402880bb3c27a904013c27e25e860001', '采购退货', 'inWarehouse/reject.do', '402881e53be5d01c013be6347b6c0007', '1', '4');
INSERT INTO `t_right` VALUES ('402880bb3c5c43ae013c5d48150f0010', '编号前缀管理', 'system/prefix.do', '402881e53b046217013b048ffca1000c', '1', '2');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd08ef90001', '库存管理', null, '402881e53aa31698013aa31fa1980003', '0', '7');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '财务管理', null, '402881e53aa31698013aa31fa1980003', '0', '8');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd128260003', '当前库存', 'store/store.do', '402880bb3c5fc306013c5fd08ef90001', '1', null);
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd16c240004', '采购付款', 'finance/pay.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3c83c0f5013c83c3e1020001', '个人信息管理', null, '402881e53aa31698013aa31fa1980003', '0', '2');
INSERT INTO `t_right` VALUES ('402880bb3c83c0f5013c83c42c1b0002', '个人信息', 'self/self.do', '402880bb3c83c0f5013c83c3e1020001', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3c83c0f5013c83c479c10003', '修改密码', 'self/modifyPwd.do', '402880bb3c83c0f5013c83c3e1020001', '1', '2');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c8406feff0001', '预付单', 'finance/prepay.do', '402880bb3c5fc306013c5fd0c5a00002', '1', null);
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c84075b720002', '预收单', 'finance/prereceipt.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '3');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c84079e5c0003', '销售收款', 'finance/receipt.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '4');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c8407ef7e0004', '收入', 'finance/income.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '5');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c84082e920005', '银行', 'dict/bank.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '6');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c840868970006', '费用支出', 'finance/expense.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '7');
INSERT INTO `t_right` VALUES ('402880bb3c83fca6013c8408a4030007', '银行存取款', 'finance/bankStatements.do', '402880bb3c5fc306013c5fd0c5a00002', '1', '8');
INSERT INTO `t_right` VALUES ('402880bb3c8e21b0013c8f53d5c60001', '报表参数管理', 'system/reportParam.do', '402881e53b046217013b048ffca1000c', '1', '7');
INSERT INTO `t_right` VALUES ('402880bb3c94afa8013c94df96550004', '报表模板配置', 'iframe/reportTemplateConfig.html', '402881e53b046217013b048ffca1000c', '1', '8');
INSERT INTO `t_right` VALUES ('402880bb3ca3022e013ca3e30f710001', '系统配置', 'system/systemConfig.do', '402881e53b046217013b048ffca1000c', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3ca43ec0013ca440f1410003', '货品组装', 'store/packaging.do', '402880bb3c5fc306013c5fd08ef90001', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3ca43ec0013ca4412dc70004', '货品拆分', 'store/split.do', '402880bb3c5fc306013c5fd08ef90001', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53aa31698013aa31fa1980003', '系统权限', '', null, '0', null);
INSERT INTO `t_right` VALUES ('402881e53b046217013b048ffca1000c', '系统管理', '', '402881e53aa31698013aa31fa1980003', '0', '3');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04caff8b0001', '权限管理', 'system/right.do', '402881e53b046217013b048ffca1000c', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04cb53a40002', '角色管理', 'system/role.do', '402881e53b046217013b048ffca1000c', '1', '4');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04cbc01c0003', '用户管理', 'system/user.do', '402881e53b046217013b048ffca1000c', '1', '5');
INSERT INTO `t_right` VALUES ('402881e53baeee40013baef1c5e50001', '基础数据', '', '402881e53aa31698013aa31fa1980003', '0', '4');
INSERT INTO `t_right` VALUES ('402881e53baeee40013baef4096f0002', '商品颜色', 'dict/color.do', '402881e53baeee40013baef1c5e50001', '1', '1');
INSERT INTO `t_right` VALUES ('402881e53bc2fd9b013bc32896e40001', '商品规格', 'dict/size.do', '402881e53baeee40013baef1c5e50001', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53bc2fd9b013bc329500c0002', '商品单位', 'dict/unit.do', '402881e53baeee40013baef1c5e50001', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53bc6b3d4013bc6b4a5c40001', '商品类别', 'dict/productType.do', '402881e53baeee40013baef1c5e50001', '1', '4');
INSERT INTO `t_right` VALUES ('402881e53bc82c91013bc832ecca0001', '商品', 'dict/product.do', '402881e53baeee40013baef1c5e50001', '1', '5');
INSERT INTO `t_right` VALUES ('402881e53be485f3013be4c875990002', '供应商', 'dict/supplier.do', '402881e53baeee40013baef1c5e50001', '1', '6');
INSERT INTO `t_right` VALUES ('402881e53be55e13013be563b45f0001', '仓库', 'dict/warehouse.do', '402881e53baeee40013baef1c5e50001', '1', '7');
INSERT INTO `t_right` VALUES ('402881e53be58722013be58905b70001', '银行', 'dict/bank.do', '402881e53baeee40013baef1c5e50001', '1', '8');
INSERT INTO `t_right` VALUES ('402881e53be5af4f013be5b04d3a0001', '员工', 'dict/employee.do', '402881e53baeee40013baef1c5e50001', '1', '9');
INSERT INTO `t_right` VALUES ('402881e53be5d01c013be5d1602e0001', '发票类型', 'dict/invoiceType.do', '402881e53baeee40013baef1c5e50001', '1', '10');
INSERT INTO `t_right` VALUES ('402881e53be5d01c013be6347b6c0007', '入库管理', '', '402881e53aa31698013aa31fa1980003', '0', '5');
INSERT INTO `t_right` VALUES ('402881e53be5d01c013be637f3380008', '采购入库', 'inWarehouse/receive.do', '402881e53be5d01c013be6347b6c0007', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53c200d7f013c20164cb60001', '当前库存', 'store/store.do', '402881e53be5d01c013be6347b6c0007', '1', '6');
INSERT INTO `t_right` VALUES ('402881e53c240327013c24ccc6730001', '其他入库', 'inWarehouse/receiveOther.do', '402881e53be5d01c013be6347b6c0007', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53c3e26cc013c3e28710f0001', '采购单', 'inWarehouse/buy.do', '402881e53be5d01c013be6347b6c0007', '1', '1');
INSERT INTO `t_right` VALUES ('402881e53c580cf1013c58104b5d0001', '采购付款', 'finance/pay.do', '402881e53be5d01c013be6347b6c0007', '1', '5');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7be857580006', '报表管理', 'system/reportConfig.do', '402881e53b046217013b048ffca1000c', '1', '6');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7be94d9d0007', '客户类型', 'dict/customerType.do', '402881e53baeee40013baef1c5e50001', '1', '11');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7be9966f0008', '客户', 'dict/customer.do', '402881e53baeee40013baef1c5e50001', '1', '12');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7be9e44f0009', '快递', 'dict/express.do', '402881e53baeee40013baef1c5e50001', '1', '13');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7beac5e9000a', '出库管理', null, '402881e53aa31698013aa31fa1980003', '0', '6');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7beb0600000b', '客户订单', 'outWarehouse/sale.do', '402881e53c7b327f013c7beac5e9000a', '1', '1');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7beb439f000c', '销售出库', 'outWarehouse/deliver.do', '402881e53c7b327f013c7beac5e9000a', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7beb84a9000d', '其他出库', 'outWarehouse/deliverOther.do', '402881e53c7b327f013c7beac5e9000a', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7bebe37b000e', '销售收款', 'finance/receipt.do', '402881e53c7b327f013c7beac5e9000a', '1', '4');
INSERT INTO `t_right` VALUES ('402881e53c7b327f013c7bec565f000f', '当前库存', 'store/store.do', '402881e53c7b327f013c7beac5e9000a', '1', '5');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `roleId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `roleName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('402880bb3c8a3478013c8a69a92c0026', '测试');
INSERT INTO `t_role` VALUES ('402880bb3c8a3478013c8a6ab2b00028', 'aa');
INSERT INTO `t_role` VALUES ('402881e53aa21d17013aa224b5ed0003', '超级管理员');

-- ----------------------------
-- Table structure for `t_roleright`
-- ----------------------------
DROP TABLE IF EXISTS `t_roleright`;
CREATE TABLE `t_roleright` (
  `rightId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `roleId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`rightId`,`roleId`),
  KEY `FK_T_ROLERI_REFERENCE_T_ROLE` (`roleId`),
  CONSTRAINT `FK_T_ROLERI_REFERENCE_T_RIGHT` FOREIGN KEY (`rightId`) REFERENCES `t_right` (`rightId`),
  CONSTRAINT `FK_T_ROLERI_REFERENCE_T_ROLE` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_roleright
-- ----------------------------
INSERT INTO `t_roleright` VALUES ('402822813c985685013c9858edad0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402822813c985685013c9858edad0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402822813c985685013c9858edad0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c27a904013c27e25e860001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c27a904013c27e25e860001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c27a904013c27e25e860001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5c43ae013c5d48150f0010', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5c43ae013c5d48150f0010', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5c43ae013c5d48150f0010', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd08ef90001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd08ef90001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd08ef90001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd128260003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd128260003', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd128260003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd16c240004', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd16c240004', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd16c240004', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c3e1020001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c3e1020001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c3e1020001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c42c1b0002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c42c1b0002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c42c1b0002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c479c10003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c479c10003', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83c0f5013c83c479c10003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8406feff0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8406feff0001', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8406feff0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84075b720002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84075b720002', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84075b720002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84079e5c0003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84079e5c0003', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84079e5c0003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8407ef7e0004', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8407ef7e0004', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8407ef7e0004', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84082e920005', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84082e920005', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c84082e920005', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c840868970006', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c840868970006', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c840868970006', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8408a4030007', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8408a4030007', '402880bb3c8a3478013c8a6ab2b00028', '0');
INSERT INTO `t_roleright` VALUES ('402880bb3c83fca6013c8408a4030007', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c8e21b0013c8f53d5c60001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c8e21b0013c8f53d5c60001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c8e21b0013c8f53d5c60001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c94afa8013c94df96550004', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c94afa8013c94df96550004', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c94afa8013c94df96550004', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca3022e013ca3e30f710001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca3022e013ca3e30f710001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca3022e013ca3e30f710001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca440f1410003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca440f1410003', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca440f1410003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca4412dc70004', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca4412dc70004', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3ca43ec0013ca4412dc70004', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53aa31698013aa31fa1980003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53aa31698013aa31fa1980003', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53aa31698013aa31fa1980003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b046217013b048ffca1000c', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b046217013b048ffca1000c', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b046217013b048ffca1000c', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04caff8b0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04caff8b0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04caff8b0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cb53a40002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cb53a40002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cb53a40002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cbc01c0003', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cbc01c0003', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cbc01c0003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef1c5e50001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef1c5e50001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef1c5e50001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef4096f0002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef4096f0002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef4096f0002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc32896e40001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc32896e40001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc32896e40001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc329500c0002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc329500c0002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc329500c0002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc6b3d4013bc6b4a5c40001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc6b3d4013bc6b4a5c40001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc6b3d4013bc6b4a5c40001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc82c91013bc832ecca0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc82c91013bc832ecca0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc82c91013bc832ecca0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be485f3013be4c875990002', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be485f3013be4c875990002', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be485f3013be4c875990002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be55e13013be563b45f0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be55e13013be563b45f0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be55e13013be563b45f0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be58722013be58905b70001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be58722013be58905b70001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be58722013be58905b70001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5af4f013be5b04d3a0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5af4f013be5b04d3a0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5af4f013be5b04d3a0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be5d1602e0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be5d1602e0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be5d1602e0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be6347b6c0007', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be6347b6c0007', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be6347b6c0007', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be637f3380008', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be637f3380008', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be637f3380008', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c200d7f013c20164cb60001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c200d7f013c20164cb60001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c200d7f013c20164cb60001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c240327013c24ccc6730001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c240327013c24ccc6730001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c240327013c24ccc6730001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c3e26cc013c3e28710f0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c3e26cc013c3e28710f0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c3e26cc013c3e28710f0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c580cf1013c58104b5d0001', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c580cf1013c58104b5d0001', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c580cf1013c58104b5d0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be857580006', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be857580006', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be857580006', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be94d9d0007', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be94d9d0007', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be94d9d0007', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9966f0008', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9966f0008', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9966f0008', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9e44f0009', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9e44f0009', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7be9e44f0009', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beac5e9000a', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beac5e9000a', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beac5e9000a', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb0600000b', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb0600000b', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb0600000b', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb439f000c', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb439f000c', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb439f000c', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb84a9000d', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb84a9000d', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7beb84a9000d', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bebe37b000e', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bebe37b000e', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bebe37b000e', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bec565f000f', '402880bb3c8a3478013c8a69a92c0026', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bec565f000f', '402880bb3c8a3478013c8a6ab2b00028', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c7b327f013c7bec565f000f', '402881e53aa21d17013aa224b5ed0003', '1');

-- ----------------------------
-- Table structure for `t_sale`
-- ----------------------------
DROP TABLE IF EXISTS `t_sale`;
CREATE TABLE `t_sale` (
  `saleId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleDate` date DEFAULT NULL,
  `deliverDate` date DEFAULT NULL,
  `otherAmount` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `receiptedAmount` double DEFAULT NULL,
  `note` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  PRIMARY KEY (`saleId`),
  KEY `FK_Reference_62` (`bankId`),
  KEY `FK_Reference_63` (`employeeId`),
  KEY `FK_Reference_99` (`customerId`),
  CONSTRAINT `FK_Reference_62` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_63` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_99` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_sale
-- ----------------------------

-- ----------------------------
-- Table structure for `t_saledetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_saledetail`;
CREATE TABLE `t_saledetail` (
  `saleDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hadSaleQty` double DEFAULT NULL,
  PRIMARY KEY (`saleDetailId`),
  KEY `FK_Reference_64` (`productId`),
  KEY `FK_Reference_65` (`colorId`),
  KEY `FK_Reference_66` (`saleId`),
  CONSTRAINT `FK_Reference_64` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK_Reference_65` FOREIGN KEY (`colorId`) REFERENCES `t_datadictionary` (`dataDictionaryId`),
  CONSTRAINT `FK_Reference_66` FOREIGN KEY (`saleId`) REFERENCES `t_sale` (`saleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_saledetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_split`
-- ----------------------------
DROP TABLE IF EXISTS `t_split`;
CREATE TABLE `t_split` (
  `splitId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `splitCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `splitDate` date NOT NULL,
  `status` int(11) NOT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`splitId`),
  UNIQUE KEY `splitId` (`splitId`),
  KEY `FK26DFF4F142C5B2F` (`warehouseId`),
  KEY `FK26DFF4F344987E9` (`employeeId`),
  KEY `FK26DFF4F1948A007` (`productId`),
  CONSTRAINT `FK26DFF4F1948A007` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `FK26DFF4F142C5B2F` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK26DFF4F344987E9` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_split
-- ----------------------------

-- ----------------------------
-- Table structure for `t_splitdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_splitdetail`;
CREATE TABLE `t_splitdetail` (
  `splitDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `splitId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`splitDetailId`),
  UNIQUE KEY `splitDetailId` (`splitDetailId`),
  KEY `FKDE8E0C80142C5B2F` (`warehouseId`),
  KEY `FKDE8E0C801948A007` (`productId`),
  KEY `FKDE8E0C808468391D` (`splitId`),
  CONSTRAINT `FKDE8E0C808468391D` FOREIGN KEY (`splitId`) REFERENCES `t_split` (`splitId`),
  CONSTRAINT `FKDE8E0C80142C5B2F` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FKDE8E0C801948A007` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_splitdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_store`
-- ----------------------------
DROP TABLE IF EXISTS `t_store`;
CREATE TABLE `t_store` (
  `storeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`storeId`),
  KEY `FK_Reference_56` (`warehouseId`),
  KEY `FK_Reference_57` (`productId`),
  CONSTRAINT `FK_Reference_56` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_57` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_store
-- ----------------------------
INSERT INTO `t_store` VALUES ('402880bb3c85143a013c8536d61a0028', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c8524f5140021', '45', '0');
INSERT INTO `t_store` VALUES ('402880bb3c8542f0013c857be82c0009', '402880bb3c85143a013c852342d80015', '402880bb3c85143a013c852539d30022', '-2', '-10');
INSERT INTO `t_store` VALUES ('402880bb3c8969b1013c897ef3790003', '402880bb3c85143a013c85235c740016', '402880bb3c85143a013c8524f5140021', '2', '0');

-- ----------------------------
-- Table structure for `t_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `supplierId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `addr` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note1` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('402880bb3c85143a013c8522deb90012', '供应商001', '供应商001', 'aa', 'a', 'a', 'a', 'aaa', 'a', 'a', 'a');
INSERT INTO `t_supplier` VALUES ('402880bb3c85143a013c8522fc440013', '供应商002', '供应商002', null, null, null, null, null, null, null, null);
INSERT INTO `t_supplier` VALUES ('402880bb3c85143a013c852323890014', '供应商003', '供应商003', null, null, null, null, null, null, null, null);
INSERT INTO `t_supplier` VALUES ('402880bb3ca2e084013ca2e9e6cd0002', 'a', 'a', 'aa', 'a', 'a', 'a', 'aa', 'aa', 'a', 'a');

-- ----------------------------
-- Table structure for `t_systemconfig`
-- ----------------------------
DROP TABLE IF EXISTS `t_systemconfig`;
CREATE TABLE `t_systemconfig` (
  `systemConfigId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `companyName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `companyPhone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `companyFax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `companyAddr` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`systemConfigId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_systemconfig
-- ----------------------------
INSERT INTO `t_systemconfig` VALUES ('f1482541b2ce40f79e01e1a7c2785473', 'aaaaaa', 'aa', 'aa', 'aa');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `userCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userPwd` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `AK_UQ_USERCODE_T_USER` (`userCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('402880bb3c8a3478013c8a63e4f90023', '中国', '中国', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `t_user` VALUES ('402880bb3c8a3478013c8a6b028f0029', 'a', 'a', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `t_user` VALUES ('402881e53b046217013b0490d440000d', 'admin', '超级管理员', '21232f297a57a5a743894a0e4a801fc3');

-- ----------------------------
-- Table structure for `t_userrole`
-- ----------------------------
DROP TABLE IF EXISTS `t_userrole`;
CREATE TABLE `t_userrole` (
  `userId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `roleId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK_T_USERRO_REFERENCE_T_ROLE` (`roleId`),
  CONSTRAINT `FK_T_USERRO_REFERENCE_T_ROLE` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`roleId`),
  CONSTRAINT `FK_T_USERRO_REFERENCE_T_USER` FOREIGN KEY (`userId`) REFERENCES `t_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_userrole
-- ----------------------------
INSERT INTO `t_userrole` VALUES ('402880bb3c8a3478013c8a6b028f0029', '402880bb3c8a3478013c8a6ab2b00028');
INSERT INTO `t_userrole` VALUES ('402880bb3c8a3478013c8a63e4f90023', '402881e53aa21d17013aa224b5ed0003');
INSERT INTO `t_userrole` VALUES ('402881e53b046217013b0490d440000d', '402881e53aa21d17013aa224b5ed0003');

-- ----------------------------
-- Table structure for `t_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `t_warehouse`;
CREATE TABLE `t_warehouse` (
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseContactor` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseTel` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseAddr` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseNode` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`warehouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_warehouse
-- ----------------------------
INSERT INTO `t_warehouse` VALUES ('402880bb3c85143a013c852342d80015', '仓库001', '仓库001', null, null, null, null);
INSERT INTO `t_warehouse` VALUES ('402880bb3c85143a013c85235c740016', '仓库002', '仓库002', null, null, null, null);
INSERT INTO `t_warehouse` VALUES ('402880bb3c85143a013c852377090017', '仓库003', '仓库003', null, null, null, null);

-- ----------------------------
-- Procedure structure for `P_BuyDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_BuyDetail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_BuyDetail`(in buyId_in varchar(32))
BEGIN
	select 
		b.productCode,
		b.productName,
		e.dataDictionaryName unitName,
		c.dataDictionaryName colorName,
		d.dataDictionaryName sizeName,
    a.qty,
		a.price,
		a.qty*a.price amount,
		a.note1 
	from t_buydetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where buyId = buyId_in;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_BuyParams`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_BuyParams`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_BuyParams`(in buyId varchar(32))
BEGIN
	select 
			a.buyCode,
			a.buyDate,
			a.sourceCode,
			a.receiveDate,
			a.note,
			b.supplierName,
			c.companyName
	from t_buy a
	left join t_supplier b on a.supplierId = b.supplierId
	left join (select a.companyName
			from t_systemConfig a) c on 1 = 1
	where a.buyId = buyId;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_PayCheckDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_PayCheckDetail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_PayCheckDetail`(
	IN txtBeginDate varchar(20),
	IN txtEndDate varchar(20),
	IN supplierId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select 
		b.receiveDate,
		b.receiveCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName unitName,
		e.dataDictionaryName sizeName,
		sum(a.qty) qty,
    sum(a.qty*a.price) amount
	from t_receivedetail a
  left join t_receive b on a.receiveId = b.receiveId
  left join t_product c on a.productId = c.productId
  left join t_datadictionary d on c.unitId = d.dataDictionaryId
  left join t_datadictionary e on c.sizeId = e.dataDictionaryId
  where b.receiveDate between txtBeginDate and txtEndDate
		and b.supplierId = supplierId and b.warehouseId = warehouseId
  group by
		b.receiveDate,
		b.receiveCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName,
		e.dataDictionaryName;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_PayCheckParams`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_PayCheckParams`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_PayCheckParams`(IN supplierId varchar(32),
	IN warehouseId varchar(32))
BEGIN
	select 
		a.supplierName,
		a.phone,
		a.fax,
		b.warehouseName
  from(select *
		from t_supplier a
		where a.supplierId = supplierId)a
  left join(select *
		from t_warehouse a 
		where a.warehouseId = warehouseId)b on 1 = 1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_ReceiptCheckDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_ReceiptCheckDetail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_ReceiptCheckDetail`(
	IN txtBeginDate varchar(20),
	IN txtEndDate varchar(20),
	IN customerId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select 
		b.deliverDate,
		b.deliverCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName unitName,
		e.dataDictionaryName sizeName,
		sum(a.qty) qty,
    sum(a.qty*a.price) amount
	from t_deliverdetail a
  left join t_deliver b on a.deliverId = b.deliverId
  left join t_product c on a.productId = c.productId
  left join t_datadictionary d on c.unitId = d.dataDictionaryId
  left join t_datadictionary e on c.sizeId = e.dataDictionaryId
  where b.deliverDate between txtBeginDate and txtEndDate
		and b.customerId = customerId and b.warehouseId = warehouseId
    and b.`status` = 1
  group by
		b.deliverDate,
		b.deliverCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName,
		e.dataDictionaryName;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_ReceiptCheckParams`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_ReceiptCheckParams`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_ReceiptCheckParams`(IN customerId varchar(32),
	IN warehouseId varchar(32))
BEGIN
	select 
		a.customerName,
		a.phone,
		a.fax,
		b.warehouseName
  from(select *
		from t_customer a
		where a.customerId = customerId)a
  left join(select *
		from t_warehouse a 
		where a.warehouseId = warehouseId)b on 1 = 1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_ReceiveDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_ReceiveDetail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_ReceiveDetail`(in receiveId varchar(32))
BEGIN
	select 
		b.productCode,
		b.productName,
		e.dataDictionaryName unitName,
		c.dataDictionaryName colorName,
		d.dataDictionaryName sizeName,
    a.qty,
		a.price,
		a.qty*a.price amount,
		a.note1 
	from t_Receivedetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.receiveId = receiveId;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `P_ReceiveParams`
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_ReceiveParams`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_ReceiveParams`(in receiveId varchar(32))
BEGIN
	select 
			a.ReceiveCode,
			a.receiveDate,
			a.deliverCode,
			a.note,
			b.supplierName,
			c.warehouseName,
			d.employeeName,
			e.companyName
	from t_Receive a
	left join t_supplier b on a.supplierId = b.supplierId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_employee d on a.employeeId = d.employeeId
	left join (select a.companyName
		from t_systemConfig a) e on 1 = 1
  where a.receiveId = receiveId;

	
END
;;
DELIMITER ;
