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
