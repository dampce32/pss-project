/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50140
Source Host           : localhost:3306
Source Database       : pss

Target Server Type    : MYSQL
Target Server Version : 50140
File Encoding         : 65001

Date: 2013-01-24 21:42:22
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
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_bank
-- ----------------------------
INSERT INTO `t_bank` VALUES ('402881e53c3e26cc013c3e2cfe980013', '中国农业银行', 'ABC', '121');
INSERT INTO `t_bank` VALUES ('402881e53c3e26cc013c3e2d24510014', '中国建设银行', 'CCB', '500');

-- ----------------------------
-- Table structure for `t_buy`
-- ----------------------------
DROP TABLE IF EXISTS `t_buy`;
CREATE TABLE `t_buy` (
  `buyId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyDate` date NOT NULL,
  `receiveDate` date DEFAULT NULL,
  `otherAmount` float DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `payAmount` float DEFAULT NULL,
  `statue` int(11) DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
  PRIMARY KEY (`buyId`),
  KEY `FK_Reference_35` (`supplierId`),
  KEY `FK_Reference_36` (`employeeId`),
  KEY `FK_T_BUY_REFERENCE_T_INVOIC` (`invoiceTypeId`),
  KEY `FK_Reference_38` (`bankId`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`supplierId`) REFERENCES `t_supplier` (`supplierId`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_38` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_T_BUY_REFERENCE_T_INVOIC` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_buy
-- ----------------------------
INSERT INTO `t_buy` VALUES ('402880bb3c6a55d5013c6a7992430004', '402881e53be5d01c013be5d1ec720003', '402881e53c3e26cc013c3e2c52cb000f', '402881e53c3e26cc013c3e2d4b580015', '402881e53c3e26cc013c3e2cfe980013', 'Buy201301240001', 'aa', '2013-01-17', '2013-01-24', '3', '201', '201', null, null, '1', '0');

-- ----------------------------
-- Table structure for `t_buydetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_buydetail`;
CREATE TABLE `t_buydetail` (
  `buyDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `buyId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveQty` float DEFAULT NULL,
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
INSERT INTO `t_buydetail` VALUES ('402880bb3c6a55d5013c6a7992440005', '402880bb3c6a55d5013c6a7992430004', '402881e53c3e26cc013c3e2bc53b000d', '402881e53c3e26cc013c3e2a19760002', '3', '33', null, null, null, '1');
INSERT INTO `t_buydetail` VALUES ('402880bb3c6a55d5013c6a7992440006', '402880bb3c6a55d5013c6a7992430004', '402881e53c3e26cc013c3e2c180e000e', '402881e53c3e26cc013c3e2a373f0004', '33', '3', null, null, null, '2');

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `customerId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `customerName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `contacter` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` longtext COLLATE utf8_unicode_ci,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `customerTypeID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  KEY `FKDACDF3493F0C935D` (`customerTypeID`),
  CONSTRAINT `FKDACDF3493F0C935D` FOREIGN KEY (`customerTypeID`) REFERENCES `t_customertype` (`dataDictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `t_customertype`
-- ----------------------------
DROP TABLE IF EXISTS `t_customertype`;
CREATE TABLE `t_customertype` (
  `dataDictionaryId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerTypeCode` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerTypeName` longtext COLLATE utf8_unicode_ci,
  `note` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`dataDictionaryId`),
  UNIQUE KEY `dataDictionaryId` (`dataDictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_customertype
-- ----------------------------

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
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a19760002', 'color', 'R');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a28f80003', 'color', 'G');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a373f0004', 'color', 'B');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a5cf60005', 'size', 'S');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a79660006', 'size', 'M');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2a8af70007', 'size', 'L');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2aa69c0008', 'unit', 'kg');
INSERT INTO `t_datadictionary` VALUES ('402881e53c3e26cc013c3e2abb110009', 'unit', 'm');

-- ----------------------------
-- Table structure for `t_deliver`
-- ----------------------------
DROP TABLE IF EXISTS `t_deliver`;
CREATE TABLE `t_deliver` (
  `deliverId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expressId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
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
  PRIMARY KEY (`deliverId`),
  KEY `FK_Reference_67` (`customerId`),
  KEY `FK_Reference_68` (`expressId`),
  KEY `FK_Reference_69` (`warehouseId`),
  KEY `FK_Reference_70` (`employeeId`),
  KEY `FK_Reference_71` (`bankId`),
  KEY `FK_Reference_72` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_67` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
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
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `discount` float DEFAULT NULL,
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
  `amount` float DEFAULT NULL,
  `payedAmount` float DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`deliverRejectId`),
  KEY `FK_Reference_77` (`bankId`),
  KEY `FK_Reference_78` (`employeeId`),
  KEY `FK_Reference_79` (`warehouseId`),
  KEY `FK_Reference_80` (`invoiceTypeId`),
  KEY `FK_Reference_81` (`customerId`),
  CONSTRAINT `FK_Reference_77` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_78` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`),
  CONSTRAINT `FK_Reference_79` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_80` FOREIGN KEY (`invoiceTypeId`) REFERENCES `t_invoicetype` (`invoiceTypeId`),
  CONSTRAINT `FK_Reference_81` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`)
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
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
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
INSERT INTO `t_employee` VALUES ('402881e53c3e26cc013c3e2d4b580015', '员工001');
INSERT INTO `t_employee` VALUES ('402881e53c3e26cc013c3e2d6b730016', '员工002');
INSERT INTO `t_employee` VALUES ('402881e53c3e26cc013c3e2d80090017', '员工003');

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

-- ----------------------------
-- Table structure for `t_invoicetype`
-- ----------------------------
DROP TABLE IF EXISTS `t_invoicetype`;
CREATE TABLE `t_invoicetype` (
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `invoiceTypeName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
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
  `payKind` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `payedAmount` float DEFAULT NULL,
  `discountAmount` float DEFAULT NULL,
  `payAmount` float DEFAULT NULL,
  `discountedAmount` double DEFAULT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sourceDate` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prepayId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`payDetailId`),
  KEY `FK_Reference_85` (`payId`),
  KEY `FK_Reference_86` (`receiveId`),
  KEY `FKAEA158CEB3D29075` (`buyId`),
  KEY `FKAEA158CE2A2ABB97` (`prepayId`),
  KEY `FKAEA158CE54DEBE0B` (`rejectId`),
  CONSTRAINT `FKAEA158CE54DEBE0B` FOREIGN KEY (`rejectId`) REFERENCES `t_reject` (`rejectId`),
  CONSTRAINT `FKAEA158CE2A2ABB97` FOREIGN KEY (`prepayId`) REFERENCES `t_prepay` (`prepayId`),
  CONSTRAINT `FKAEA158CEB3D29075` FOREIGN KEY (`buyId`) REFERENCES `t_buy` (`buyId`),
  CONSTRAINT `FK_Reference_85` FOREIGN KEY (`payId`) REFERENCES `t_pay` (`payId`),
  CONSTRAINT `FK_Reference_86` FOREIGN KEY (`receiveId`) REFERENCES `t_receive` (`receiveId`)
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
  PRIMARY KEY (`prefixId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_prefix
-- ----------------------------
INSERT INTO `t_prefix` VALUES ('402880bb3c6a096d013c6a12abf60001', 'prepay', 'Prepay', '预付单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d53b8250001', 'buy', 'Buy', '采购单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d5464870002', 'receive', 'Rec', '入库单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d54f7bf0003', 'receiveOther', 'ORec', '其他入库单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d5344013c5d557d800004', 'pay', 'Pay', '付款单');
INSERT INTO `t_prefix` VALUES ('402881e53c5d8e25013c5dcc2eeb000f', 'reject', 'Rej', '采购退货单');

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
  `amount` float DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
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
INSERT INTO `t_prepay` VALUES ('402880bb3c6a55d5013c6b3544ea0018', '402881e53c3e26cc013c3e2cfe980013', '402881e53c3e26cc013c3e2c52cb000f', '402881e53c3e26cc013c3e2d6b730016', 'Prepay201301240001', '2013-01-24', '100', 'aa', '1', null, '0');

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
  `qtyStore` float DEFAULT NULL,
  `amountStore` float DEFAULT NULL,
  `buyingPrice` float DEFAULT NULL,
  `salePrice` float DEFAULT NULL,
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
INSERT INTO `t_product` VALUES ('402880bb3c6a55d5013c6a6ac5650003', '402881e53c3e26cc013c3e2b5bce000c', '402881e53c3e26cc013c3e2aa69c0008', '402881e53c3e26cc013c3e2a19760002', '402881e53c3e26cc013c3e2a5cf60005', 'a', 'a', '0', '0', '3', '3', null);
INSERT INTO `t_product` VALUES ('402880bb3c6a55d5013c6a8d6d2d000e', '402881e53c3e26cc013c3e2b0968000a', null, null, null, 'aaaa', 'aaaa', '0', '0', '33', '33', null);
INSERT INTO `t_product` VALUES ('402881e53c3e26cc013c3e2bc53b000d', '402881e53c3e26cc013c3e2b5bce000c', '402881e53c3e26cc013c3e2aa69c0008', '402881e53c3e26cc013c3e2a19760002', '402881e53c3e26cc013c3e2a5cf60005', '商品00001', '商品00001', '2', '211', '33', '33', null);
INSERT INTO `t_product` VALUES ('402881e53c3e26cc013c3e2c180e000e', '402881e53c3e26cc013c3e2b2c91000b', '402881e53c3e26cc013c3e2aa69c0008', '402881e53c3e26cc013c3e2a373f0004', '402881e53c3e26cc013c3e2a79660006', '商品00002', '商品00002', '6', '18', '3', '3', null);

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
INSERT INTO `t_producttype` VALUES ('402881e53c3e26cc013c3e2b0968000a', 'fda922bf5f2847a89f9fb58727e99600', '商品类别0001', '商品类别0001', '0');
INSERT INTO `t_producttype` VALUES ('402881e53c3e26cc013c3e2b2c91000b', 'fda922bf5f2847a89f9fb58727e99600', '商品类别0002', '商品类别0002', '1');
INSERT INTO `t_producttype` VALUES ('402881e53c3e26cc013c3e2b5bce000c', '402881e53c3e26cc013c3e2b0968000a', '商品类别00010001', '商品类别00010001', '1');
INSERT INTO `t_producttype` VALUES ('fda922bf5f2847a89f9fb58727e99600', null, '商品类别', '0', '0');

-- ----------------------------
-- Table structure for `t_receive`
-- ----------------------------
DROP TABLE IF EXISTS `t_receive`;
CREATE TABLE `t_receive` (
  `receiveId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveDate` date DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `discountAmount` float DEFAULT NULL,
  `payAmount` float DEFAULT NULL,
  `shzt` int(11) DEFAULT NULL,
  `isPay` int(11) DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `otherAmount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
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
INSERT INTO `t_receive` VALUES ('402880bb3c6a55d5013c6a7cc8ec0007', '402881e53c3e26cc013c3e2c52cb000f', '402881e53c3e26cc013c3e2caa8f0011', '402881e53be5d01c013be5d1b6b70002', '402881e53c3e26cc013c3e2d4b580015', '402881e53c3e26cc013c3e2cfe980013', 'Rec201301240001', 'adfad', '2013-01-24', '179', '1', '78', null, '0', null, '2', '1', '0');
INSERT INTO `t_receive` VALUES ('402880bb3c6a55d5013c6a8dfd69000f', null, '402881e53c3e26cc013c3e2cc53d0012', null, '402881e53c3e26cc013c3e2d4b580015', null, 'ORec201301240001', 'adfad', '2013-01-24', null, null, null, null, '0', 'aa', null, '1', '0');

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
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
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
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a7cc8ed0008', '402880bb3c6a55d5013c6a7cc8ec0007', '402881e53c3e26cc013c3e2bc53b000d', '402880bb3c6a55d5013c6a7992440005', '402881e53c3e26cc013c3e2a19760002', '1', '33', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a7cc8ed0009', '402880bb3c6a55d5013c6a7cc8ec0007', '402881e53c3e26cc013c3e2c180e000e', '402880bb3c6a55d5013c6a7992440006', '402881e53c3e26cc013c3e2a373f0004', '2', '3', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a7cc8ed000a', '402880bb3c6a55d5013c6a7cc8ec0007', '402881e53c3e26cc013c3e2bc53b000d', null, '402881e53c3e26cc013c3e2a19760002', '4', '33', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a7cc8ee000b', '402880bb3c6a55d5013c6a7cc8ec0007', '402881e53c3e26cc013c3e2c180e000e', null, '402881e53c3e26cc013c3e2a373f0004', '2', '3', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a8dfd6a0010', '402880bb3c6a55d5013c6a8dfd69000f', '402881e53c3e26cc013c3e2bc53b000d', null, '402881e53c3e26cc013c3e2a19760002', '2', '33', null, null, null);
INSERT INTO `t_receivedetail` VALUES ('402880bb3c6a55d5013c6a8dfd6b0011', '402880bb3c6a55d5013c6a8dfd69000f', '402881e53c3e26cc013c3e2c180e000e', null, '402881e53c3e26cc013c3e2a373f0004', '2', '3', null, null, null);

-- ----------------------------
-- Table structure for `t_reject`
-- ----------------------------
DROP TABLE IF EXISTS `t_reject`;
CREATE TABLE `t_reject` (
  `rejectId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoiceTypeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rejectDate` date DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `payAmount` float DEFAULT NULL,
  `shzt` int(11) DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `checkAmount` double DEFAULT NULL,
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
INSERT INTO `t_reject` VALUES ('402880bb3c6a55d5013c6abb13050016', '402881e53c3e26cc013c3e2caa8f0011', '402881e53c3e26cc013c3e2d4b580015', '402881e53c3e26cc013c3e2c52cb000f', null, '402881e53c3e26cc013c3e2cfe980013', 'Rej201301240001', null, '2013-01-24', '20', '0', null, null, '1', '0');

-- ----------------------------
-- Table structure for `t_rejectdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_rejectdetail`;
CREATE TABLE `t_rejectdetail` (
  `rejectDetailId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `rejectId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `colorId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
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
INSERT INTO `t_rejectdetail` VALUES ('402880bb3c6a55d5013c6abb13060017', '402880bb3c6a55d5013c6abb13050016', '402881e53c3e26cc013c3e2bc53b000d', '402881e53c3e26cc013c3e2a19760002', '5', '4', '3', '3', '3', '20');

-- ----------------------------
-- Table structure for `t_reportconfig`
-- ----------------------------
DROP TABLE IF EXISTS `t_reportconfig`;
CREATE TABLE `t_reportconfig` (
  `reportConfigId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `reportConfigCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportSql` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportParamsSql` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportDetailSql` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reportName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`reportConfigId`),
  KEY `AK_AK` (`reportConfigCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_reportconfig
-- ----------------------------
INSERT INTO `t_reportconfig` VALUES ('402880bb3c662f21013c6637a44e0001', null, null, 'call p_buyParams(@buyId)', 'buy', 'CALL p_buyDetail(@buyId)', '采购单');

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
INSERT INTO `t_right` VALUES ('402880bb3c27a904013c27e25e860001', '采购退货', 'inWarehouse/reject.do', '402881e53be5d01c013be6347b6c0007', '1', '4');
INSERT INTO `t_right` VALUES ('402880bb3c5c43ae013c5d48150f0010', '编号前缀管理', 'system/prefix.do', '402881e53b046217013b048ffca1000c', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd08ef90001', '库存管理', null, '402881e53aa31698013aa31fa1980003', '0', '5');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '财务管理', null, '402881e53aa31698013aa31fa1980003', '0', '6');
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd128260003', '当前库存', 'store/store.do', '402880bb3c5fc306013c5fd08ef90001', '1', null);
INSERT INTO `t_right` VALUES ('402880bb3c5fc306013c5fd16c240004', '采购付款', 'finance/pay.do', '402880bb3c5fc306013c5fd0c5a00002', '1', null);
INSERT INTO `t_right` VALUES ('402880bb3c6038f3013c60c98c2b0012', '个人信息', 'self/self.do', '402880c33b020bcc013b02476a450001', '1', '1');
INSERT INTO `t_right` VALUES ('402880bb3c6038f3013c60c9e8fe0013', '修改密码', 'self/modifyPwd.do', '402880c33b020bcc013b02476a450001', '1', '2');
INSERT INTO `t_right` VALUES ('402880c33b020bcc013b02476a450001', '个人信息管理', '', '402881e53aa31698013aa31fa1980003', '0', '1');
INSERT INTO `t_right` VALUES ('402881e53aa31698013aa31fa1980003', '系统权限', '', null, '0', null);
INSERT INTO `t_right` VALUES ('402881e53b046217013b048ffca1000c', '系统管理', '', '402881e53aa31698013aa31fa1980003', '0', '2');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04caff8b0001', '权限管理', 'system/right.do', '402881e53b046217013b048ffca1000c', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04cb53a40002', '角色管理', 'system/role.do', '402881e53b046217013b048ffca1000c', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53b04bb98013b04cbc01c0003', '用户管理', 'system/user.do', '402881e53b046217013b048ffca1000c', '1', '4');
INSERT INTO `t_right` VALUES ('402881e53baeee40013baef1c5e50001', '基础数据', '', '402881e53aa31698013aa31fa1980003', '0', '3');
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
INSERT INTO `t_right` VALUES ('402881e53be5d01c013be6347b6c0007', '入库管理', '', '402881e53aa31698013aa31fa1980003', '0', '4');
INSERT INTO `t_right` VALUES ('402881e53be5d01c013be637f3380008', '采购入库', 'inWarehouse/receive.do', '402881e53be5d01c013be6347b6c0007', '1', '2');
INSERT INTO `t_right` VALUES ('402881e53c200d7f013c20164cb60001', '当前库存', 'store/store.do', '402881e53be5d01c013be6347b6c0007', '1', '6');
INSERT INTO `t_right` VALUES ('402881e53c240327013c24ccc6730001', '其他入库', 'inWarehouse/receiveOther.do', '402881e53be5d01c013be6347b6c0007', '1', '3');
INSERT INTO `t_right` VALUES ('402881e53c3e26cc013c3e28710f0001', '采购单', 'inWarehouse/buy.do', '402881e53be5d01c013be6347b6c0007', '1', '1');
INSERT INTO `t_right` VALUES ('402881e53c580cf1013c58104b5d0001', '采购付款', 'finance/pay.do', '402881e53be5d01c013be6347b6c0007', '1', '5');

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
INSERT INTO `t_roleright` VALUES ('402880bb3c27a904013c27e25e860001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5c43ae013c5d48150f0010', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd08ef90001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd0c5a00002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd128260003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c5fc306013c5fd16c240004', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c6038f3013c60c98c2b0012', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880bb3c6038f3013c60c9e8fe0013', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402880c33b020bcc013b02476a450001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53aa31698013aa31fa1980003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b046217013b048ffca1000c', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04caff8b0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cb53a40002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53b04bb98013b04cbc01c0003', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef1c5e50001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53baeee40013baef4096f0002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc32896e40001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc2fd9b013bc329500c0002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc6b3d4013bc6b4a5c40001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53bc82c91013bc832ecca0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be485f3013be4c875990002', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be55e13013be563b45f0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be58722013be58905b70001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5af4f013be5b04d3a0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be5d1602e0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be6347b6c0007', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53be5d01c013be637f3380008', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c200d7f013c20164cb60001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c240327013c24ccc6730001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c3e26cc013c3e28710f0001', '402881e53aa21d17013aa224b5ed0003', '1');
INSERT INTO `t_roleright` VALUES ('402881e53c580cf1013c58104b5d0001', '402881e53aa21d17013aa224b5ed0003', '1');

-- ----------------------------
-- Table structure for `t_sale`
-- ----------------------------
DROP TABLE IF EXISTS `t_sale`;
CREATE TABLE `t_sale` (
  `saleId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `customerId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bankId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `employeeId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sourceCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `saleDate` date DEFAULT NULL,
  `deliverDate` date DEFAULT NULL,
  `otherAmount` float DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `payedAmount` float DEFAULT NULL,
  `note` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `receiptedAmount` double DEFAULT NULL,
  PRIMARY KEY (`saleId`),
  KEY `FK_Reference_61` (`customerId`),
  KEY `FK_Reference_62` (`bankId`),
  KEY `FK_Reference_63` (`employeeId`),
  CONSTRAINT `FK_Reference_61` FOREIGN KEY (`customerId`) REFERENCES `t_customer` (`customerId`),
  CONSTRAINT `FK_Reference_62` FOREIGN KEY (`bankId`) REFERENCES `t_bank` (`bankId`),
  CONSTRAINT `FK_Reference_63` FOREIGN KEY (`employeeId`) REFERENCES `t_employee` (`employeeId`)
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
  `qty` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `note1` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hadSaleQty` float DEFAULT NULL,
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
-- Table structure for `t_store`
-- ----------------------------
DROP TABLE IF EXISTS `t_store`;
CREATE TABLE `t_store` (
  `storeId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `warehouseId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` float DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`storeId`),
  KEY `FK_Reference_56` (`warehouseId`),
  KEY `FK_Reference_57` (`productId`),
  CONSTRAINT `FK_Reference_56` FOREIGN KEY (`warehouseId`) REFERENCES `t_warehouse` (`warehouseId`),
  CONSTRAINT `FK_Reference_57` FOREIGN KEY (`productId`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_store
-- ----------------------------
INSERT INTO `t_store` VALUES ('402880bb3c6a55d5013c6a7e0090000c', '402881e53c3e26cc013c3e2caa8f0011', '402881e53c3e26cc013c3e2bc53b000d', '0', '-20');
INSERT INTO `t_store` VALUES ('402880bb3c6a55d5013c6a7e009b000d', '402881e53c3e26cc013c3e2caa8f0011', '402881e53c3e26cc013c3e2c180e000e', '4', '0');
INSERT INTO `t_store` VALUES ('402880bb3c6a55d5013c6a8ec23a0012', '402881e53c3e26cc013c3e2cc53d0012', '402881e53c3e26cc013c3e2bc53b000d', '2', '0');
INSERT INTO `t_store` VALUES ('402880bb3c6a55d5013c6a8ec23f0013', '402881e53c3e26cc013c3e2cc53d0012', '402881e53c3e26cc013c3e2c180e000e', '2', '0');

-- ----------------------------
-- Table structure for `t_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `supplierId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `supplierName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplierCode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('402881e53c3e26cc013c3e2c52cb000f', '供应商001', '供应商001');
INSERT INTO `t_supplier` VALUES ('402881e53c3e26cc013c3e2c71020010', '供应商002', '供应商002');

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
INSERT INTO `t_warehouse` VALUES ('402881e53c3e26cc013c3e2caa8f0011', '仓库001', '仓库001', null, null, null, null);
INSERT INTO `t_warehouse` VALUES ('402881e53c3e26cc013c3e2cc53d0012', '仓库002', '仓库002', null, null, null, null);

-- ----------------------------
-- Procedure structure for `p_buyDetail`
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_buyDetail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_buyDetail`(in buyId_in varchar(32))
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
-- Procedure structure for `p_buyParams`
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_buyParams`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_buyParams`(IN buyId_in varchar(32))
BEGIN
	select 
			a.buyCode,
			a.sourceCode,
			a.buyDate,
			a.receiveDate,
			b.supplierName,
			c.employeeName,
			a.note
	from t_buy a
  left join t_supplier b on a.supplierId = b.supplierId
  left join t_employee c on a.employeeId = c.employeeId
	where a.buyId = buyId_in;
END
;;
DELIMITER ;
