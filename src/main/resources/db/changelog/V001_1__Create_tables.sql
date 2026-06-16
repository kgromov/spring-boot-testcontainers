-- V001_1__Create_tables.sql
drop table if exists country;
CREATE TABLE IF NOT EXISTS country (
                                       Code             VARCHAR(3) NOT NULL PRIMARY KEY,
                                       Name             VARCHAR(60) NOT NULL,
                                       Continent        ENUM ('Asia', 'Europe', 'North America', 'Africa', 'Oceania', 'Antarctica', 'South America') NOT NULL,
                                       Region           VARCHAR(32) NOT NULL,
                                       SurfaceArea      DECIMAL(10, 2) NOT NULL,
                                       IndepYear        SMALLINT,
                                       Population       INT NOT NULL,
                                       LifeExpectancy   DECIMAL(3, 1),
                                       GNP              DECIMAL(10, 2),
                                       GNPOld           DECIMAL(10, 2),
                                       LocalName        VARCHAR(45) NOT NULL,
                                       GovernmentForm   VARCHAR(45) NOT NULL,
                                       HeadOfState      VARCHAR(60),
                                       Capital          INT,
                                       Code2            VARCHAR(2) NOT NULL
) engine=InnoDB;

drop table if exists city;
CREATE TABLE IF NOT EXISTS city (
                                    ID          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    Name        VARCHAR(35) NOT NULL,
                                    CountryCode VARCHAR(3)  NOT NULL,
                                    District    VARCHAR(20) NOT NULL,
                                    Population  INT UNSIGNED DEFAULT 0 NOT NULL,
                                    FOREIGN KEY (CountryCode) REFERENCES country(Code),
                                    INDEX(CountryCode)
    ) engine=InnoDB;


drop table if exists countrylanguage;
CREATE TABLE IF NOT EXISTS countrylanguage (
                                               CountryCode VARCHAR(3)   NOT NULL,
                                               Language    VARCHAR(30)  NOT NULL,
                                               IsOfficial  ENUM ('T', 'F') default 'F' NOT NULL,
                                               Percentage  DECIMAL(4, 1)   default 0.0 NOT NULL,
                                               PRIMARY KEY (CountryCode, Language),
                                               FOREIGN KEY (CountryCode) REFERENCES country(Code),
                                               INDEX(CountryCode)
    ) engine=InnoDB;
