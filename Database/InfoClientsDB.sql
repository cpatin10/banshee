-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema info_clients
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `info_clients` ;

-- -----------------------------------------------------
-- Schema info_clients
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `info_clients` DEFAULT CHARACTER SET utf8 ;
USE `info_clients` ;

-- -----------------------------------------------------
-- Table `info_clients`.`country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`country` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`country` (
  `id` SMALLINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `info_clients`.`state`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`state` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`state` (
  `id` SMALLINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `country_id` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_state_country_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_state_country`
    FOREIGN KEY (`country_id`)
    REFERENCES `info_clients`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `info_clients`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`city` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`city` (
  `id` SMALLINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `state_id` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_city_state1_idx` (`state_id` ASC) VISIBLE,
  CONSTRAINT `fk_city_state1`
    FOREIGN KEY (`state_id`)
    REFERENCES `info_clients`.`state` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `info_clients`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`customer` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`customer` (
  `id` BIGINT NOT NULL,
  `nit` VARCHAR(255) NOT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(12) NOT NULL,
  `city_id` SMALLINT NOT NULL,
  `credit_limit` DECIMAL(19,4) NOT NULL,
  `available_credit` DECIMAL(19,4) NOT NULL,
  `visit_percentage` DECIMAL(5,4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nit_UNIQUE` (`nit` ASC) VISIBLE,
  INDEX `fk_customer_city1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `info_clients`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `info_clients`.`sales_representative`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`sales_representative` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`sales_representative` (
  `id` BIGINT NOT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `identification_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identification_number_UNIQUE` (`identification_number` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `info_clients`.`visit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `info_clients`.`visit` ;

CREATE TABLE IF NOT EXISTS `info_clients`.`visit` (
  `id` BIGINT NOT NULL,
  `date` DATETIME NOT NULL,
  `net` BIGINT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `customer_id` BIGINT NOT NULL,
  `sales_representative_id` BIGINT NOT NULL,
  `visit_total` DECIMAL(19,4) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_visit_customer1_idx` (`customer_id` ASC) VISIBLE,
  INDEX `fk_visit_sales_representative1_idx` (`sales_representative_id` ASC) VISIBLE,
  CONSTRAINT `fk_visit_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `info_clients`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_visit_sales_representative1`
    FOREIGN KEY (`sales_representative_id`)
    REFERENCES `info_clients`.`sales_representative` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
