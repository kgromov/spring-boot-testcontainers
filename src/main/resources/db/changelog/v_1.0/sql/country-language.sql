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
