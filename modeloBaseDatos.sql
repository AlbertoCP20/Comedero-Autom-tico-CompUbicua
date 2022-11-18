-- MySQL Script generated by MySQL Workbench
-- Fri Nov 18 13:56:08 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`USER` (
  `idUser` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `secondName` VARCHAR(45) NOT NULL,
  `birthday` DATE NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `photo` LONGBLOB NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FEEDER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`FEEDER` (
  `idFeeder` INT NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idFeeder`, `idUser`),
  INDEX `IdUser_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `IdUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `mydb`.`USER` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PET` (
  `idPet` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `birthday` DATE NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `breed` VARCHAR(45) NOT NULL,
  `weight` FLOAT NOT NULL,
  `photo` LONGBLOB NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idPet`),
  INDEX `idUser_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `mydb`.`USER` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SENSOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`SENSOR` (
  `idFeeder` INT NOT NULL,
  `idSensor` INT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFeeder`, `idSensor`),
  CONSTRAINT `idFeeder`
    FOREIGN KEY (`idFeeder`)
    REFERENCES `mydb`.`FEEDER` (`idFeeder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`MEASUREMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MEASUREMENT` (
  `date` DATETIME NOT NULL,
  `idSensor` INT NOT NULL,
  `valor` FLOAT NOT NULL,
  `idFeeder` INT NOT NULL,
  PRIMARY KEY (`date`, `idSensor`),
  INDEX `idSensorPressure_idx` (`idSensor` ASC) VISIBLE,
  CONSTRAINT `idFeeder`
    FOREIGN KEY (`idFeeder`)
    REFERENCES `mydb`.`FEEDER` (`idFeeder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idSensor`
    FOREIGN KEY (`idSensor`)
    REFERENCES `mydb`.`SENSOR` (`idSensor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;