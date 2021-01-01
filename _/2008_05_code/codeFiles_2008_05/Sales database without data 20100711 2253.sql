-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.32-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema sales
--

CREATE DATABASE IF NOT EXISTS sales;
USE sales;

--
-- Definition of table `branch`
--
CREATE TABLE `branch` (
  `BranchId` int(10) unsigned NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Location` varchar(45) NOT NULL,
  PRIMARY KEY (`BranchId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branch`
--

/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;


--
-- Definition of table `customer`
--
CREATE TABLE `customer` (
  `CustomerNo` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `ContactNo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CustomerNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `employee`
--
CREATE TABLE `employee` (
  `EmployeeId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Designation` varchar(10) NOT NULL,
  `Gender` varchar(1) NOT NULL,
  `Mobile` varchar(15) NOT NULL,
  `BranchId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`EmployeeId`),
  KEY `FK_Employee_Branch_BranchId` (`BranchId`),
  CONSTRAINT `FK_Employee_Branch_BranchId` FOREIGN KEY (`BranchId`) REFERENCES `branch` (`BranchId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


--
-- Definition of table `product`
--
CREATE TABLE `product` (
  `ProductCode` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Image` longblob,
  PRIMARY KEY (`ProductCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


--
-- Definition of table `purchase`
--
CREATE TABLE `purchase` (
  `PurchaseNo` int(11) NOT NULL,
  `PurchaseDate` date NOT NULL,
  `SupplierNo` int(11) DEFAULT '0',
  PRIMARY KEY (`PurchaseNo`) USING BTREE,
  KEY `supplierNo` (`SupplierNo`) USING BTREE,
  KEY `FK_purchase_supplierNo` (`SupplierNo`),
  CONSTRAINT `FK_purchase_supplierNo` FOREIGN KEY (`SupplierNo`) REFERENCES `supplier` (`SupplierNo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `purchase`
--

/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;


--
-- Definition of table `purchase_details`
--
CREATE TABLE `purchase_details` (
  `PurchaseNo` int(11) NOT NULL DEFAULT '0',
  `ProductCode` int(11) NOT NULL DEFAULT '0',
  `PurchaseQuantity` int(11) NOT NULL DEFAULT '0',
  `UnitPurchasePrice` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`PurchaseNo`,`ProductCode`) USING BTREE,
  KEY `purchaseNo` (`PurchaseNo`) USING BTREE,
  KEY `productNo` (`ProductCode`) USING BTREE,
  KEY `FK_purchaseline_purchaseNo` (`PurchaseNo`),
  CONSTRAINT `FK_purchaseDetails_purchaseNo` FOREIGN KEY (`PurchaseNo`) REFERENCES `purchase` (`PurchaseNo`) ON UPDATE CASCADE,
  CONSTRAINT `FK_purchaseDetails_ProductCode` FOREIGN KEY (`ProductCode`) REFERENCES `product` (`ProductCode`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `purchase_details`
--

/*!40000 ALTER TABLE `purchase_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_details` ENABLE KEYS */;


--
-- Definition of table `sales`
--
CREATE TABLE `sales` (
  `SalesNo` int(11) NOT NULL AUTO_INCREMENT,
  `SalesDate` date NOT NULL,
  `CustomerNo` int(11) DEFAULT '0',
  `SalesDeskId` int(10) unsigned NOT NULL,
  `EmployeeId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`SalesNo`) USING BTREE,
  KEY `customerNo` (`CustomerNo`) USING BTREE,
  KEY `FK_sales_customerNo` (`CustomerNo`),
  KEY `FK_sales_salesDesk_salesDeskId` (`SalesDeskId`),
  KEY `FK_sales_employee_employeeId` (`EmployeeId`),
  CONSTRAINT `FK_sales_employee_employeeId` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`EmployeeId`) ON UPDATE CASCADE,
  CONSTRAINT `FK_sales_customerNo` FOREIGN KEY (`CustomerNo`) REFERENCES `customer` (`CustomerNo`) ON UPDATE CASCADE,
  CONSTRAINT `FK_sales_salesDesk_salesDeskId` FOREIGN KEY (`SalesDeskId`) REFERENCES `sales_desk` (`SalesDeskId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `sales`
--

/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;


--
-- Definition of table `sales_desk`
--
CREATE TABLE `sales_desk` (
  `SalesDeskId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BranchId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`SalesDeskId`),
  KEY `FK_Sales_Desk_Branch_BranchId` (`BranchId`),
  CONSTRAINT `FK_Sales_Desk_Branch_BranchId` FOREIGN KEY (`BranchId`) REFERENCES `branch` (`BranchId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales_desk`
--

/*!40000 ALTER TABLE `sales_desk` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_desk` ENABLE KEYS */;


--
-- Definition of table `sales_details`
--
CREATE TABLE `sales_details` (
  `SalesNo` int(11) NOT NULL DEFAULT '0',
  `ProductCode` int(11) NOT NULL DEFAULT '0',
  `SalesQuantity` int(11) NOT NULL DEFAULT '0',
  `UnitSalesPrice` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`SalesNo`,`ProductCode`) USING BTREE,
  KEY `salesNo` (`SalesNo`) USING BTREE,
  KEY `productNo` (`ProductCode`) USING BTREE,
  KEY `FK_salesline_productCode` (`ProductCode`),
  KEY `FK_salesline_salesNo` (`SalesNo`),
  CONSTRAINT `FK_salesDetails_productCode` FOREIGN KEY (`ProductCode`) REFERENCES `product` (`ProductCode`) ON UPDATE CASCADE,
  CONSTRAINT `FK_saleDetails_salesNo` FOREIGN KEY (`SalesNo`) REFERENCES `sales` (`SalesNo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `sales_details`
--

/*!40000 ALTER TABLE `sales_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_details` ENABLE KEYS */;


--
-- Definition of table `stock`
--
CREATE TABLE `stock` (
  `ProductCode` int(11) NOT NULL DEFAULT '0',
  `Quantity` int(11) NOT NULL DEFAULT '0',
  `ReorderLevel` int(11) NOT NULL DEFAULT '0',
  `BranchId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ProductCode`) USING BTREE,
  UNIQUE KEY `productNo` (`ProductCode`) USING BTREE,
  KEY `FK_stock_productCode` (`ProductCode`),
  KEY `FK_stock_Branch_BranchId` (`BranchId`),
  CONSTRAINT `FK_stock_Branch_BranchId` FOREIGN KEY (`BranchId`) REFERENCES `branch` (`BranchId`) ON UPDATE CASCADE,
  CONSTRAINT `FK_stock_productCode` FOREIGN KEY (`ProductCode`) REFERENCES `product` (`ProductCode`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `stock`
--

/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;


--
-- Definition of table `supplier`
--
CREATE TABLE `supplier` (
  `SupplierNo` int(11) NOT NULL,
  `SupplierName` varchar(50) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `ContactNo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`SupplierNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `supplier`
--

/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;


--
-- Definition of table `users`
--
CREATE TABLE `users` (
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `EmployeeId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`UserName`),
  KEY `FK_Users_Employee_EmployeeId` (`EmployeeId`),
  CONSTRAINT `FK_Users_Employee_EmployeeId` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`EmployeeId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
