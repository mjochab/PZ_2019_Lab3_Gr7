-- MySQL Script generated by MySQL Workbench
-- Fri Apr  5 22:02:37 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Thoth
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Thoth` ;

-- -----------------------------------------------------
-- Schema Thoth
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Thoth` DEFAULT CHARACTER SET utf8 ;
USE `Thoth` ;

-- -----------------------------------------------------
-- Table `Thoth`.`object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`object` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`object` (
  `ObjectId` INT(11) NOT NULL,
  `ZipCode` VARCHAR(6) NOT NULL,
  `City` VARCHAR(11) NOT NULL,
  `Street` VARCHAR(11) NOT NULL,
  `IsShop` TINYINT(1) NOT NULL,
  PRIMARY KEY (`ObjectId`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`role` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`role` (
  `RoleId` INT(11) NOT NULL,
  `Position` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`RoleId`));


-- -----------------------------------------------------
-- Table `Thoth`.`state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`state` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`state` (
  `StateId` INT(11) NOT NULL,
  `Name` VARCHAR(25) NOT NULL);


-- -----------------------------------------------------
-- Table `Thoth`.`state-of-order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`state-of-order` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`state-of-order` (
  `UserId` INT(11) NOT NULL,
  `OrderId` INT(11) NOT NULL,
  `StateId` INT(11) NOT NULL,
  CONSTRAINT `StateId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`state` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Thoth`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`user` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`user` (
  `UserId` INT(11) NOT NULL,
  `FirstName` VARCHAR(25) NOT NULL,
  `LastName` VARCHAR(50) NOT NULL,
  `Login` VARCHAR(25) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Password` TEXT NOT NULL,
  `RoleId` INT(11) NOT NULL,
  `State` TINYINT(1) NOT NULL,
  `ObjectId` INT(11) NOT NULL,
  PRIMARY KEY (`UserId`),
  CONSTRAINT `ObjectId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`object` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `RoleId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`role` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `StateId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`state-of-order` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Thoth`.`receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`receipt` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`receipt` (
  `ReceiptId` INT(11) NOT NULL,
  `ObjectId` INT(11) NOT NULL,
  `TotalValue` DECIMAL(8,2) NOT NULL,
  `UserId` INT(11) NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`ReceiptId`),
  CONSTRAINT `UserId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`user` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `ObjectId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`object` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`product` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`product` (
  `ProductId` INT(11) NOT NULL,
  `Name` VARCHAR(25) NOT NULL,
  `Price` DECIMAL(8,2) NOT NULL,
  `Discount` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ProductId`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`product-receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`product-receipt` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`product-receipt` (
  `ProductId` INT(11) NOT NULL,
  `ReceiptId` INT(11) NOT NULL,
  `Amount` INT(11) NOT NULL,
  `Price` DECIMAL(8,2) NOT NULL,
  CONSTRAINT `ReceiptId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`receipt` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `ProductId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`product` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`state-on-object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`state-on-object` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`state-on-object` (
  `ProductId` INT(11) NOT NULL,
  `ObjectId` INT(11) NOT NULL,
  `Amount` DECIMAL(8,2) NOT NULL,
  CONSTRAINT `ProductId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`product` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `ObjectId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`object` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`user-object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`user-object` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`user-object` (
  `ObjectId` INT(11) NOT NULL,
  `UserId` INT(11) NOT NULL,
  CONSTRAINT `ObjectId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`object` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `UserId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`user` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Thoth`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`customer` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`customer` (
  `CustomerId` INT(11) NOT NULL,
  `FirstName` VARCHAR(25) NOT NULL,
  `LastName` VARCHAR(25) NOT NULL,
  `PhoneNumber` INT(11) NOT NULL,
  PRIMARY KEY (`CustomerId`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`order` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`order` (
  `OrderId` INT(11) NOT NULL,
  `ObjectId-need` INT(11) NOT NULL,
  `UserId` INT(11) NOT NULL,
  `CustomerId` INT(11) NOT NULL,
  `ObjectId-delivery` INT(11) NOT NULL,
  `DateOfOrder` DATE NOT NULL,
  `ParentId` INT(11) NOT NULL,
  PRIMARY KEY (`OrderId`),
  CONSTRAINT `OrderId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`state-of-order` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `UserId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`user` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `ObjectId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`object` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `CustomerId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`customer` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Thoth`.`order-product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Thoth`.`order-product` ;

CREATE TABLE IF NOT EXISTS `Thoth`.`order-product` (
  `Id` INT(11) NOT NULL,
  `OrderId` INT(11) NOT NULL,
  `ProductId` INT(11) NOT NULL,
  `Amount` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `ProductId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`product` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `OrderId`
  FOREIGN KEY ()
  REFERENCES `Thoth`.`order` ()
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
