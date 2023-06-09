CREATE
DATABASE IF NOT EXISTS DB_OP_SCH;
USE DB_OP_SCH;
CREATE TABLE IF NOT EXISTS appointments
(
    ID           INT(11)      NOT NULL AUTO_INCREMENT,
    DOCTOR_EMAIL VARCHAR(255) NOT NULL,
    PATIENT_NAME VARCHAR(255) NOT NULL,
    DOCTOR_NAME  VARCHAR(255) NOT NULL,
    DESCRIPTION  VARCHAR(255),
    GENDER       VARCHAR(255),
    DATE         DATETIME,
    AGE          INT(11),
    PRIMARY KEY (ID),
    FOREIGN KEY (DOCTOR_EMAIL) REFERENCES professionals (EMAIL)
);
CREATE TABLE IF NOT EXISTS professionals
(
    EMAIL      VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD   VARCHAR(255) NOT NULL,
    NAME       VARCHAR(255) NOT NULL,
    SPECIALITY VARCHAR(255) NOT NULL,
    PRIMARY KEY (EMAIL)
);